package com.deviseworks.mcc

import com.deviseworks.mcc.event.PlayerJoin
import com.deviseworks.mcc.event.PlayerQuit
import com.deviseworks.mcc.scheduler.MemorySchedule
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

const val API_URL = "http://localhost:8080"

class Main: JavaPlugin() {
    override fun onEnable() {
        // EventListenerを登録
        Bukkit.getServer().pluginManager.registerEvents(PlayerJoin(), this)
        Bukkit.getServer().pluginManager.registerEvents(PlayerQuit(), this)

        // スケジューラを登録
        MemorySchedule().runTaskTimerAsynchronously(this, 0L, 40L) // 2秒に一回実行
    }
}