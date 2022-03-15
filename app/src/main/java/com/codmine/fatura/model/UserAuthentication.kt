package com.codmine.fatura.model

import com.google.gson.annotations.SerializedName

data class UserAuthentication(
    @SerializedName("GirisSonucu")
    val loginResult: String,
    @SerializedName("Msg")
    val loginMessage: String
)