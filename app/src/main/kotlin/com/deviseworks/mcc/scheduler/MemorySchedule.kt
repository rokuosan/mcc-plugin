package com.deviseworks.mcc.scheduler

import com.deviseworks.mcc.entity.Memory
import com.deviseworks.mcc.util.Request
import kotlinx.serialization.json.Json
import org.bukkit.scheduler.BukkitRunnable

class MemorySchedule: BukkitRunnable() {
    // Server VM
    private val runtime: Runtime = Runtime.getRuntime()

    override fun run() {
        // Get memory size
        val totalKB = runtime.totalMemory() / 1024
        val maxKB = runtime.maxMemory() / 1024
        val freeKB = runtime.freeMemory() / 1024
        val usedKB = totalKB - freeKB
        val ratio: Double = (usedKB / totalKB.toDouble()) * 100

        // Serialize
        val memory = Memory(totalKB, maxKB, freeKB, usedKB, ratio)

        // POST
        val json = Json.encodeToString(Memory.serializer(), memory)

        Request().post("http://localhost:8080/api/server/memory", json)
    }
}