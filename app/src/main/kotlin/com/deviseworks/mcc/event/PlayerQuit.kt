package com.deviseworks.mcc.event

import com.deviseworks.mcc.API_URL
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
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
        val formBody = FormBody.Builder()
            .add("uuid", player.uniqueId.toString())
            .build()

        val request = Request.Builder()
            .url("$API_URL/api/player/quit")
            .post(formBody)
            .build()

        OkHttpClient().newCall(request).execute().use { response ->
            if(!response.isSuccessful) println("POSTできませんでした。")
        }
    }
}