package com.codmine.fatura.repository

import com.codmine.fatura.model.Mukellef
import com.codmine.fatura.service.FaturaAPI
import com.codmine.fatura.util.Constants.SERVER_CONN_ERROR
import com.codmine.fatura.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class FaturaRepository @Inject constructor(
    private val api : FaturaAPI
) {

    suspend fun getLoginData(getType : String,
                            userVkNo : String,
                            userPass : String) : Resource<Mukellef> {
        val response = try {
            api.getLoginData(getType, userVkNo, userPass)
        } catch (e : Exception) {
            return Resource.Error(SERVER_CONN_ERROR)
        }
        return Resource.Success(response)
    }

}