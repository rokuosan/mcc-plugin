package com.deviseworks.mcc.util

import com.deviseworks.mcc.common.client
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.bukkit.ChatColor

class Request {
    /**
     * JSONを丸ごとPOSTする関数。
     * ヘッダー情報などの操作はできないので注意してください。
     *
     * @param url POSTするURL
     * @param json POSTする内容
     * @param output trueの場合、レスポンスを出力します。
     */
    fun postJSON(url: String, json: String, output: Boolean = false) = runBlocking{
        try{
            val response: HttpResponse = client.post(url){
                body = json
            }

            if(output){
                val body: String = response.receive()
                println(body)
            }
        }catch(e: Exception){
            println("${ChatColor.RED}[POST:ERROR] ${ChatColor.RESET}POSTに失敗しました。(URL: $url)")
        }
    }
}