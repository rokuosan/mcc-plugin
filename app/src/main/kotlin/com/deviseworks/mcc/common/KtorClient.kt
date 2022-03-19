package com.deviseworks.mcc.common

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

val client = HttpClient(CIO){
    install(JsonFeature){
        serializer = KotlinxSerializer()
    }
}