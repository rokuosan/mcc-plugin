package com.deviseworks.mcc.event

import com.deviseworks.mcc.common.Endpoint
import com.deviseworks.mcc.common.client
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuit: Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun postLeftPlayer(event: PlayerQuitEvent){
        // プレイヤー取得
        val player = event.player

        // POST
        val response = runBlocking<HttpResponse>{
            client.post("${Endpoint.Player}/quit"){
                parameter("uuid", player.uniqueId.toString())
            }
        }

        if(response.status != HttpStatusCode.OK){
            println("/api/player/quit にPOST出来ませんでした")
        }
    }
}