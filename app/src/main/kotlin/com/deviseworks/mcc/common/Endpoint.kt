package com.deviseworks.mcc.common

object Endpoint {
    // Base
    val Base = "https://api-rokuosan.cloud.okteto.net/api/"


    // Server
    private val Server = "$Base/server"
    val Memory = "$Server/memory"
    val Order = "$Server/order"


    // Player
    val Player = "$Base/player"

}