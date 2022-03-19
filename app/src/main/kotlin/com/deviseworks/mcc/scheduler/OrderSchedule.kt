package com.deviseworks.mcc.scheduler

import com.deviseworks.mcc.API_URL
import com.deviseworks.mcc.entity.Order
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

/**
 * このクラスは/api/server/order にスタックしている命令を順次実行するクラスです。
 * 一秒ごとにGETして命令を待機します。
 * 命令を受け取った場合、順次実行して実行終了した時点でPOSTを非同期で行う。
 */
class OrderSchedule(private val plugin: JavaPlugin): BukkitRunnable() {
    // OkHttp クライアント
//    private val client = OkHttpClient()

    // Ktor クライアント
    private val client = HttpClient(CIO){
        install(JsonFeature){
            serializer=KotlinxSerializer()
        }
    }

    override fun run() {
        // 命令を取得
        val orderList = fetchOrder()?: return
        val order = orderList.first()

        // 実行
        try{
            // コマンドを実行可能な形にする
            val cmd = order.command!!
                .replace("(space)", " ", true)
                .replace("(at)", "@", true)
                .replace("(tilde)", "~", true)

            // 実行する
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd)
            plugin.logger.info("RPC: /$cmd")

            // POST
            val response = doneOrder(order)

            if(response.status != HttpStatusCode.OK){
                plugin.logger.warning("POSTに失敗しました")
            }

        }catch(e: Exception){
            println("コマンド実行に失敗 (${order.command!!})")
        }
    }

    private fun fetchOrder() = runBlocking<List<Order>?> {
        try {
            client.get("$API_URL/api/server/order")
        }catch(e: Exception){
            null
        }
    }

    private fun doneOrder(order: Order) = runBlocking<HttpResponse> {
        client.post("$API_URL/api/server/order/done"){
            parameter("id", order.id)
        }
    }
}