package org.example

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.example.di.module
import org.example.routes.activityRoutes
import org.example.routes.authRoutes
import org.example.utils.configureSecurity

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(Koin) {
            slf4jLogger()
            modules(module)
        }
        configureSerialization()
        configureRouting()
        configureSecurity()
        configureRouting()
    }.start(wait = true)
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

fun Application.configureRouting() {
    routing {
        authRoutes()
        activityRoutes()
    }
}