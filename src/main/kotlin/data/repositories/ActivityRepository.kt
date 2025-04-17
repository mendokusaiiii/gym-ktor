package org.example.data.repositories

import org.example.data.models.Activities
import org.example.data.models.Activity
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ActivityRepository {
    fun createActivity(activity: Activity): Activity? = transaction {
        val insertedId = Activities.insert {
            it[name] = activity.name
            it[description] = activity.description
            it[userId] = activity.userId
        } get Activities.id

        activity.copy(id = insertedId)
    }

    fun getActivitiesByUserId(userId: Int): List<Activity> = transaction {
        Activities.selectAll().where { Activities.userId eq userId }
            .mapNotNull {
                Activity(
                    id = it[Activities.id],
                    name = it[Activities.name],
                    description = it[Activities.description],
                    userId = it[Activities.userId]
                )
            }
    }
}