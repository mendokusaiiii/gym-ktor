package org.example.data.repositories

import org.example.data.models.User
import org.example.data.models.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {
    fun createUser(user: User): User = transaction {
        val insertedId = Users.insert {
            it[username] = user.username
            it[password] = user.password
            it[email] = user.email
        } get Users.id

        user.copy(id = insertedId)
    }

    fun findUserById(username: String): User? = transaction {
        Users.selectAll().where { Users.username eq username }
            .mapNotNull {
                User(
                    id = it[Users.id],
                    username = it[Users.username],
                    password = it[Users.password],
                    email = it[Users.email],
                    role = it[Users.role]
                )
            }
            .singleOrNull()
    }
}