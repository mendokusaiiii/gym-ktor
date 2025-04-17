package org.example.routes

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.data.models.Activity
import org.example.services.ActivityService
import org.koin.ktor.ext.inject

fun Route.activityRoutes() {
    val activityService by inject<ActivityService>()

    authenticate("auth-jwt") {
        route("/activities") {
            post {
                val principal = call.principal<JWTPrincipal>()
                principal?.payload?.getClaim("username")?.asString()
                val userId = call.receive<Activity>().userId
                val activity = call.receive<Activity>()
                if (activity.userId == userId) {
                    val createdActivity = activityService.createActivity(activity)
                    if (createdActivity != null) {
                        call.respond(HttpStatusCode.Created, createdActivity)
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "Failed to create activity")
                    }
                } else {
                    call.respond(HttpStatusCode.Forbidden, "Unauthorized user")
                }
            }

            get {
                val principal = call.principal<JWTPrincipal>()
                principal?.payload?.getClaim("username")?.asString()
                val userId = call.parameters["userId"]?.toIntOrNull()
                if (userId != null) {
                    val activities = activityService.getUserActivities(userId)
                    if (activities.isNotEmpty()) {
                        call.respond(HttpStatusCode.OK, activities)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "No activities found for user ID: $userId")
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid user ID")
                }
            }
        }
    }
}