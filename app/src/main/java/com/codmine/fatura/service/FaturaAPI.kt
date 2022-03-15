package com.codmine.fatura.service

import com.codmine.fatura.model.Mukellef
import retrofit2.http.GET
import retrofit2.http.Query

interface FaturaAPI {

    //http://www.ipeksu.com/mobil/mobil_islem.asp?CodMineMobil=1&GibNo=95600494&User_Vk=2111196061&User_Pass=456
    @GET("mobil_islem.asp")
    suspend fun getLoginData(
        @Query("CodMineMobil") queryType : String,
        @Query("User_Vk") userName : String,
        @Query("User_Pass") userPassword : String
    ) : Mukellef

}