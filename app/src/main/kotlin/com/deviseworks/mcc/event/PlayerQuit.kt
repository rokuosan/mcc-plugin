package com.deviseworks.mcc.event

import com.deviseworks.mcc.API_URL
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuit: Listener {

    // Ktor クライアント
    private val client = HttpClient(CIO){
        install(JsonFeature){
            serializer=KotlinxSerializer()
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun postLeftPlayer(event: PlayerQuitEvent){
        // プレイヤー取得
        val player = event.player

        // POST
        val response = runBlocking<HttpResponse>{
            client.post("${API_URL}/api/player/quit"){
                parameter("uuid", player.uniqueId.toString())
            }
        }

        if(response.status != HttpStatusCode.OK){
            println("/api/player/quit にPOST出来ませんでした")
        }
    }
}