package com.deviseworks.mcc.event

import com.deviseworks.mcc.entity.Player
import com.deviseworks.mcc.entity.PlayerConnection
import com.deviseworks.mcc.util.Request
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin: Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun postJoinedPlayer(event: PlayerJoinEvent){
        // プレイヤー取得
        val player = event.player

        // プレイヤーをシリアライズ
        val body = Json.encodeToString(
            Player(
                uuid = player.uniqueId.toString(),
                name = player.name,
                displayName = player.displayName,
                isAdmin = player.hasPermission("admin.staff"),
                status = PlayerConnection.ONLINE.status
            )
        )

        // POST
        Request().postJSON("http://localhost:8080/api/player/join", body)
    }
}