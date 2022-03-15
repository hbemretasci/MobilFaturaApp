package com.codmine.fatura.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("MM_User_id")
    val id: String,
    @SerializedName("User_Email")
    val eMail: String,
    @SerializedName("User_Fax")
    val fax: String,
    @SerializedName("User_Gibno")
    val gibNo: String,
    @SerializedName("User_Gibparola")
    val gibParola: String,
    @SerializedName("User_Gibsifre")
    val gibSifre: String,
    @SerializedName("User_Gsm")
    val gsm: String,
    @SerializedName("User_Tc")
    val kimlikNo: String,
    @SerializedName("User_Tel")
    val tel: String,
    @SerializedName("User_Tip")
    val tip: String,
    @SerializedName("User_Unvan")
    val unvan: String,
    @SerializedName("User_Vk")
    val vkNo: String,
    @SerializedName("baglikullanicilar")
    val relatedUsers: List<RelatedUser>
)