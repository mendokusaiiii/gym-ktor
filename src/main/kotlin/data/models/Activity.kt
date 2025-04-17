package org.example.data.models

import org.jetbrains.exposed.sql.Table

data class Activity(
    val id: Int,
    val name: String,
    val description: String,
    val userId : Int,
)

object Activities : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val description = varchar("description", 255)
    val userId = integer("user_id")

    override val primaryKey = PrimaryKey(id, name = "PK_Activity_ID")
}
