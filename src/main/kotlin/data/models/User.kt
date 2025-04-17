package org.example.data.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class User(
    val id: Int,
    val username: String,
    val password: String,
    val email: String,
    val role: String
)

object Users: Table() {
    val id = integer("id").autoIncrement()
    val username = varchar("username", 50)
    val password = varchar("password", 64)
    val email = varchar("email", 100)
    val role = varchar("role", 20)

    override val primaryKey = PrimaryKey(id, name = "PK_User_ID")
}