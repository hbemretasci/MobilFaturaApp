package com.codmine.fatura.viewmodel

import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.codmine.fatura.repository.FaturaRepository
import com.codmine.fatura.util.CustomDataStore
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

    //context açığa sebep olmamak için parametre ile geçirildi.
    //@SuppressLint("StaticFieldLeak")
    //private val context = getApplication<Application>().applicationContext

    //data store
    var customDataStore = CustomDataStore()

    //1.Başlık
    //1.1.Fatura Bilgileri
    var faturaDate = mutableStateOf("")
    var faturaTime = mutableStateOf("")
    val faturaParaBirimiList = listOf("Türk Lirası", "Canadian Dollar", "Euro", "Pound Sterling", "US Dollar", "Yen")
    var faturaDovizKuru = mutableStateOf("0.0")
    val faturaTipiList = listOf("SATIŞ", "İADE", "TEVKİFAT", "İSTİSNA", "ÖZEL MATRAH", "İHRAÇ KAYITLI")

    //1.2.Alıcı Bilgileri
    var faturaVknTckn = mutableStateOf("")
    var faturaAdi = mutableStateOf("")
    var faturaSoyadi = mutableStateOf("")
    var faturaUnvan = mutableStateOf("")
    var faturaVergiDairesi = mutableStateOf("")
    var faturaAdres = mutableStateOf("")

    //1.3.İrsaliye Bilgisi
    var faturaIrsaliyeNum = mutableStateOf("")
    var faturaIrsaliyeDate = mutableStateOf("")

    //2.Kalemler
    var kalemMalHizmet = mutableStateOf("")
    var kalemMiktar = mutableStateOf("0.0")
    val kalemBirim = listOf("Adet", "Paket", "Kutu", "kg", "lt", "ton")
    var kalemBirimFiyat = mutableStateOf("0.0")
    var kalemIskontoOrani = mutableStateOf("0.0")
    var kalemIskontoTutari = mutableStateOf("0.0")
    var kalemMalHizmetTutari = mutableStateOf("0.0")
    val kalemKDVOrani = listOf("0", "1", "8", "18")
    var kalemKDVTutari = mutableStateOf("0.0")

    //private val emptyUserAuthentication = UserAuthentication("","")
    //private val emptyMaliMusavir = MaliMusavir("","","","","","")
    //var errorMessage = mutableStateOf("")

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

    fun initializeFieldsValue() {
        faturaDate.value = getCurrentDate()
        faturaTime.value = getCurrentTime()
        faturaDovizKuru.value = "0.0"
        faturaVknTckn.value = ""
        faturaAdi.value =""
        faturaSoyadi.value =""
        faturaUnvan.value =""
        faturaVergiDairesi.value = ""
        faturaAdres.value = ""
        faturaIrsaliyeNum.value = ""
        faturaIrsaliyeDate.value = ""

    }

    fun selectDate(context : Context, dateType : String) {
        val currentDateTime = Calendar.getInstance()
        val year = currentDateTime.get(Calendar.YEAR)
        val month = currentDateTime.get(Calendar.MONTH)
        val day = currentDateTime.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            context,
            {_: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                when (dateType) {
                    "FAT" -> faturaDate.value = formatDate(year, month, dayOfMonth)
                    "IRS" -> faturaIrsaliyeDate.value = formatDate(year, month, dayOfMonth)
                }
            }, year, month, day
        ).show()
    }

}