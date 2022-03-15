package com.codmine.fatura.model

import com.google.gson.annotations.SerializedName

data class RelatedUser(
    @SerializedName("Bagli_Ad")
    val name: String,
    @SerializedName("Bagli_Durum")
    val status: String,
    @SerializedName("Bagli_Email")
    val eMail: String,
    @SerializedName("Bagli_Fax")
    var fax: String,
    @SerializedName("Bagli_Gsm")
    val gsm: String,
    @SerializedName("Bagli_Pass")
    val password: String,
    @SerializedName("Bagli_Tc")
    val kimlikNo: String,
    @SerializedName("Bagli_Tel")
    val tel: String,
    @SerializedName("Bagli_Tip")
    val tip: String,
    @SerializedName("Bagli_Vk")
    val vkNo: String,
    @SerializedName("Bagli_id")
    val id: String,
    @SerializedName("Bagli_Bos")
    val unReadCount: String
)