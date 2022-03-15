package com.codmine.fatura.model

import com.google.gson.annotations.SerializedName

data class MaliMusavir(
    @SerializedName("MM_User_Email")
    val eMail: String,
    @SerializedName("MM_User_Fax")
    val fax: String,
    @SerializedName("MM_User_Gsm")
    val gsm: String,
    @SerializedName("MM_User_Tel")
    val tel: String,
    @SerializedName("MM_User_Unvan")
    val unvan: String,
    @SerializedName("MM_User_id")
    val id: String
)