package com.deviseworks.mcc.scheduler

import com.deviseworks.mcc.API_URL
import com.deviseworks.mcc.entity.Order
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scheduler.BukkitRunnable
import org.json.JSONArray

/**
 * このクラスは/api/server/order にスタックしている命令を順次実行するクラスです。
 * 一秒ごとにGETして命令を待機します。
 * 命令を受け取った場合、順次実行して実行終了した時点でPOSTを非同期で行う。
 */
class OrderSchedule: BukkitRunnable() {
    // OkHttp クライアント
    private val client = OkHttpClient()

    override fun run() {
        // リクエスト作成
        var request = Request.Builder()
            .url("$API_URL/api/server/order")
            .build()

        var body:String

        // リクエスト送信
        client.newCall(request).execute().use { response ->
            if(!response.isSuccessful){
                println("GET に失敗しました URL($API_URL/api/server/order)")
                return
            }
            body = response.body?.string()?:return
        }

        // JSONの配列をパース
        val array = JSONArray(body)
        var order: Order? = null
        var count = 0
        val max = array.length()

        while(count < max){
            order = Json.decodeFromString(array.getJSONObject(count).toString())?:return
            if(order.isCanceled!! || order.isDone!!){
                count++
                continue
            }else{
                break
            }
        }

        // 例外
        order?:return
        if(order.isDone!!) return

        // 実行
        try{
            // コマンドを実行可能な形にする
            var cmd = order.command!!.replace("(space)", " ", true)
            cmd = cmd.replace("(at)", "@", true)
            cmd = cmd.replace("(tilde)", "~", true)

            // 実行する
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd)
            println("${ChatColor.AQUA}RemoteTask: /${ChatColor.ITALIC}$cmd")

            // POST
            val formBody = FormBody.Builder()
                .add("id", order.id!!.toString())
                .build()

            request = Request.Builder()
                .url("$API_URL/api/server/order/done")
                .post(formBody)
                .build()

            OkHttpClient().newCall(request).execute().use { response ->
                if(!response.isSuccessful) println("POSTできませんでした。")
            }

//            println("/api/server/order/done にid: ${order.id} をPOSTしました。")
        }catch(e: Exception){
            println("コマンド実行に失敗 (${order.command!!})")
        }
    }
}