package com.deviseworks.mcc.util

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.bukkit.ChatColor
import java.io.IOException

class Request {
    companion object{
        val MediaType_JSON = "application/json; charset=utf-8".toMediaType()
    }

    /**
     * JSONを丸ごとPOSTする関数。
     * ヘッダー情報などの操作はできないので注意してください。
     *
     * @param url POSTするURL
     * @param body POSTする内容
     * @param output trueの場合、レスポンスを出力します。
     */
    fun postJSON(url: String, body: String, output: Boolean = false){
        try{
            val request = Request.Builder()
                .url(url)
                .post(body.toRequestBody(MediaType_JSON))
                .build()

            OkHttpClient().newCall(request).execute().use { response ->
                if(!response.isSuccessful){
                    throw IOException("Unexpected code $response")
                }
                if(output) println(response.body!!.string())
            }
        }catch(e: Exception){
            println("${ChatColor.RED}[POST:ERROR] ${ChatColor.RESET}POSTに失敗しました。(URL: $url)")
        }
    }
}