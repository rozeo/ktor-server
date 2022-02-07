package net.roz

import io.ktor.application.*
import net.roz.core.application.Application as ProjectApplication


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.bootstrap() {
    ProjectApplication(this)
        .containerBinding()
        .routeBinding()
}