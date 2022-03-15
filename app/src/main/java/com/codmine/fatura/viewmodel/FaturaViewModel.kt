package com.codmine.fatura.viewmodel

import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.codmine.fatura.model.MaliMusavir
import com.codmine.fatura.model.UserAuthentication
import com.codmine.fatura.repository.FaturaRepository
import com.codmine.fatura.util.Constants.QUERY_TYPE_MUKELLEF_DATA
import com.codmine.fatura.util.CustomDataStore
import com.codmine.fatura.util.Resource
import com.codmine.fatura.util.formatDate
import com.codmine.fatura.util.formatTime
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FaturaViewModel @Inject constructor(
    application: Application,
    private val repository: FaturaRepository
) : AndroidViewModel(application) {

    //@SuppressLint("StaticFieldLeak")
    //private val context = getApplication<Application>().applicationContext

    //data store
    var customDataStore = CustomDataStore()

    var faturaDate = mutableStateOf("")
    var faturaTime = mutableStateOf("")
    val faturaParaBirimiList = listOf("Türk Lirası", "Canadian Dollar", "Euro", "Pound Sterling", "US Dollar", "Yen")
    val faturaTipiList = listOf("SATIŞ", "İADE", "TEVKİFAT", "İSTİSNA", "ÖZEL MATRAH", "İHRAÇ KAYITLI")
    var faturaVknTckn = mutableStateOf("")
    var faturaAdi = mutableStateOf("")
    var faturaSoyadi = mutableStateOf("")
    var faturaUnvan = mutableStateOf("")
    var faturaVergiDairesi = mutableStateOf("")
    var faturaAdres = mutableStateOf("")
    var faturaIrsaliyeNum = mutableStateOf("")
    var faturaIrsaliyeDate = mutableStateOf("")

    //private val emptyUserAuthentication = UserAuthentication("","")
    //private val emptyMaliMusavir = MaliMusavir("","","","","","")

    //general
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    //load User Data
    //var userAuthentication = mutableStateOf(emptyUserAuthentication)
    //private var maliMusavir = mutableStateOf(emptyMaliMusavir)

    /*
    suspend fun loadUserData(user: String, pass: String) {
        isLoading.value = true
        when(val result = repository.getLoginData(QUERY_TYPE_MUKELLEF_DATA, user, pass)) {
            is Resource.Loading -> {
            }
            is Resource.Success -> {
                errorMessage.value = ""
                isLoading.value = false
                userAuthentication.value = result.data!!.userAuthentication
                maliMusavir.value = result.data.maliMusavir
            }
            is Resource.Error -> {
                userAuthentication.value = emptyUserAuthentication
                maliMusavir.value = emptyMaliMusavir
                errorMessage.value = result.message!!
                isLoading.value = false
            }
        }
    }
     */

    fun getCurrentDate() : String {
        val currentDateTime = Calendar.getInstance()
        val year = currentDateTime.get(Calendar.YEAR)
        val month = currentDateTime.get(Calendar.MONTH)
        val dayOfMonth = currentDateTime.get(Calendar.DAY_OF_MONTH)
        return formatDate(year, month, dayOfMonth)
    }

    fun getCurrentTime() : String {
        val currentDateTime = Calendar.getInstance()
        val hour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentDateTime.get(Calendar.MINUTE)
        val second = currentDateTime.get(Calendar.SECOND)
        return formatTime(hour, minute, second)
    }

    fun getCurrentDateAndTime() {
        faturaDate.value = getCurrentDate()
        faturaTime.value = getCurrentTime()
    }

    fun selectDate(context : Context) {
        val currentDateTime = Calendar.getInstance()
        val year = currentDateTime.get(Calendar.YEAR)
        val month = currentDateTime.get(Calendar.MONTH)
        val day = currentDateTime.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            context,
            {_: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                faturaDate.value = formatDate(year, month, dayOfMonth)
            }, year, month, day
        ).show()
    }

}
