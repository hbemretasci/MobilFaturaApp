package com.codmine.fatura.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.codmine.fatura.util.Constants.DATA_FILE_KEY
import com.codmine.fatura.util.Constants.KEY_KULLANICI
import com.codmine.fatura.util.Constants.KEY_SIFRE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class CustomDataStore {

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_FILE_KEY)
    }

    /*
    suspend fun saveMukellefData(
        context: Context,
        gibKoduValue: String,
        vkNoValue: String,
        sifreValue: String
    ) {
        val gibKoduKey = stringPreferencesKey(KEY_GIBKODU)
        val vkNoKey = stringPreferencesKey(KEY_VKNO)
        val sifreKey = stringPreferencesKey(KEY_SIFRE)
        context.dataStore.edit {
            it[gibKoduKey] = gibKoduValue
            it[vkNoKey] = vkNoValue
            it[sifreKey] = sifreValue
        }
    }
     */

    /*
    suspend fun getLoginData(context: Context): Boolean {

        val wrapKey = booleanPreferencesKey(KEY_LOGIN)
        val valueFlow: Flow<Boolean> = context.dataStore.data.map {
            it[wrapKey] ?: false
        }
        return valueFlow.first()
    }
     */

    /*
suspend fun saveLoginData(context: Context, valueLogin: Boolean) {

    val wrapKey = booleanPreferencesKey(KEY_LOGIN)
    context.dataStore.edit {
        it[wrapKey] = valueLogin
    }

    if (!valueLogin) {
        exitProcess(0)
    }
}
 */

    suspend fun saveKullaniciSifreData(
        context: Context,
        kullanici: String,
        sifre: String
    ) {
        val kullaniciKey = stringPreferencesKey(KEY_KULLANICI)
        val sifreKey = stringPreferencesKey(KEY_SIFRE)
        context.dataStore.edit {
            it[kullaniciKey] = kullanici
            it[sifreKey] = sifre
        }
    }

    suspend fun getKullaniciData(context: Context): String {

        val wrapKey = stringPreferencesKey(KEY_KULLANICI)
        val valueFlow: Flow<String> = context.dataStore.data.map {
            it[wrapKey] ?: ""
        }
        return valueFlow.first()
    }

    suspend fun getSifreData(context: Context): String {

        val wrapKey = stringPreferencesKey(KEY_SIFRE)
        val valueFlow: Flow<String> = context.dataStore.data.map {
            it[wrapKey] ?: ""
        }
        return valueFlow.first()
    }
}