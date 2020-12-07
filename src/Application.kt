package com.haohcraft.ktorgraphql

import com.haohcraft.ktorgraphql.graphql.installGraphQL
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.request.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
            serializeNulls()
            setPrettyPrinting()
        }
    }

    installGraphQL()
}

