package com.deviseworks.mcc.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    @SerialName("uuid")
    var uuid: String,

    @SerialName("name")
    var name: String,

    @SerialName("display_name")
    var displayName: String? = "",

    @SerialName("admin_flag")
    var isAdmin: Boolean = false,

    @SerialName("status")
    var status: String = PlayerConnection.OFFLINE.status
)

enum class PlayerConnection(val status: String) {
    ONLINE("ONLINE"),
    OFFLINE("OFFLINE"),
    AFK("AFK");
}
/*
Expected JSON Example:
{
    "uuid": "This is UUID v4.",
    "name": "Foo",
    "display_name": "Bar",
    "admin_flag": false
}
 */