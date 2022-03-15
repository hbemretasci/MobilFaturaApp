package com.codmine.fatura.model

import com.google.gson.annotations.SerializedName

data class Mukellef(
    @SerializedName("islemzamani")
    val processingTime: String,
    @SerializedName("kullanici")
    val user: User,
    @SerializedName("kullanicidogrulama")
    val userAuthentication: UserAuthentication,
    @SerializedName("malimusavir")
    val maliMusavir: MaliMusavir
)