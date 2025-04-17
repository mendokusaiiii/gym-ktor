package org.example.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.data.models.User
import org.example.services.AuthService
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    val authService by inject<AuthService>()

    route("/auth") {
        post("/register") {
            val user = call.receive<User>()
            val createdUser = authService.register(user)
            if (createdUser != null) {
                call.respond(HttpStatusCode.Created, createdUser)
            } else {
                call.respond(HttpStatusCode.BadRequest, "User already exists")
            }
        }

        post("/login") {
            val user = call.receive<User>()
            val foundUser = authService.login(user.username, user.password)
            if (foundUser != null) {
                val token = JWT.create()
                    .withAudience("http://localhost:8080/")
                    .withIssuer("http://localhost:8080/")
                    .withClaim("username", foundUser.username)
                    .sign(Algorithm.HMAC256("secret-token"))
                call.respond(hashMapOf("token" to token))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
            }
        }
    }
}