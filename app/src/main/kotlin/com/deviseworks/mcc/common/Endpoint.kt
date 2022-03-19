package com.deviseworks.mcc.common

object Endpoint {
    // Base
    private const val Base = "https://api-rokuosan.cloud.okteto.net/api/"


    // Server
    private const val Server = "$Base/server"
    const val Memory = "$Server/memory"
    const val Order = "$Server/order"


    // Player
    const val Player = "$Base/player"

}