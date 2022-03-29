package com.codmine.fatura.viewmodel

import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import com.codmine.fatura.repository.FaturaRepository
import com.codmine.fatura.util.CustomDataStore
import com.codmine.fatura.util.StringToFloat
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
    var selectedParaBirimi = mutableStateOf(faturaParaBirimiList[0])
    var faturaDovizKuru = mutableStateOf(TextFieldValue("0.0"))
    val faturaTipiList = listOf("Satış", "İade", "Tevkifat", "İstisna", "Özel Matrah", "İhraç Kayıtlı")
    var selectedFaturaTipi = mutableStateOf(faturaTipiList[0])

    //1.2.Alıcı Bilgileri
    var faturaVknTckn = mutableStateOf(TextFieldValue(""))
    var faturaAdi = mutableStateOf(TextFieldValue(""))
    var faturaSoyadi = mutableStateOf(TextFieldValue(""))
    var faturaUnvan = mutableStateOf(TextFieldValue(""))
    var faturaVergiDairesi = mutableStateOf(TextFieldValue(""))
    var faturaAdres = mutableStateOf(TextFieldValue(""))

    //1.3.İrsaliye Bilgisi
    var faturaIrsaliyeNum = mutableStateOf(TextFieldValue(""))
    var faturaIrsaliyeDate = mutableStateOf("")

    //2.Kalemler
    var kalemMalHizmet = mutableStateOf(TextFieldValue(""))
    var kalemMiktar = mutableStateOf(TextFieldValue("0.0"))
    val kalemBirimList = listOf("Adet", "Paket", "Kutu", "kg", "lt", "ton")
    var kalemBirimFiyat = mutableStateOf(TextFieldValue("0.0"))
    var kalemIskontoOrani = mutableStateOf(TextFieldValue("0.0"))
    var kalemIskontoTutari = mutableStateOf(TextFieldValue("0.0"))
    var kalemMalHizmetTutari = mutableStateOf(TextFieldValue("0.0"))
    val kalemKDVOraniList = listOf("0", "1", "8", "18")
    var selectedKDV = mutableStateOf(kalemKDVOraniList[0])
    var kalemKDVTutari = mutableStateOf(TextFieldValue("0.0"))

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

    fun initializeBaslikFields() {
        faturaDate.value = getCurrentDate()
        faturaTime.value = getCurrentTime()
        faturaDovizKuru.value = TextFieldValue("0.0")
        faturaVknTckn.value = TextFieldValue("")
        faturaAdi.value = TextFieldValue("")
        faturaSoyadi.value = TextFieldValue("")
        faturaUnvan.value = TextFieldValue("")
        faturaVergiDairesi.value = TextFieldValue("")
        faturaAdres.value = TextFieldValue("")
        faturaIrsaliyeNum.value = TextFieldValue("")
        faturaIrsaliyeDate.value = ""
    }

    fun initializeKalemlerFields() {

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

    fun updateFaturaKalemValues() {
        val miktar = StringToFloat(kalemMiktar.value.text)
        val fiyat = StringToFloat(kalemBirimFiyat.value.text)
        val iskontoOrani = StringToFloat(kalemIskontoOrani.value.text)
        val kdvOrani = StringToFloat(kalemIskontoOrani.value.text)


        var iskontoTutari = StringToFloat(kalemIskontoTutari.value.text)
        if (iskontoOrani > 0F) {
            iskontoTutari = miktar * fiyat * iskontoOrani / 100
            kalemIskontoTutari.value = TextFieldValue(iskontoTutari.toString())
        }
        var tutar = (miktar * fiyat) - iskontoTutari
        kalemMalHizmetTutari.value = TextFieldValue(tutar.toString())

        var kdvTutari = tutar * selectedKDV.value.toFloat() / 100
        kalemKDVTutari.value = TextFieldValue(kdvTutari.toString())
    }

    fun updateIskontoOraniValue() {
        val miktar = StringToFloat(kalemMiktar.value.text)
        val fiyat = StringToFloat(kalemBirimFiyat.value.text)
        val iskontoOrani = StringToFloat(kalemIskontoOrani.value.text)

        if (iskontoOrani in 0F..100F) {
            val iskontoTutari = miktar * fiyat * iskontoOrani / 100
            kalemIskontoTutari.value = TextFieldValue(iskontoTutari.toString())
        }
    }

    fun updateIskontoTutariValue() {
        val miktar = StringToFloat(kalemMiktar.value.text)
        val fiyat = StringToFloat(kalemBirimFiyat.value.text)
        var iskontoOrani = StringToFloat(kalemIskontoOrani.value.text)
        val iskontoTutari = StringToFloat(kalemIskontoTutari.value.text)

        if (miktar * fiyat * iskontoOrani / 100 != iskontoTutari) {
            iskontoOrani = 0F
            kalemIskontoOrani.value = TextFieldValue(iskontoOrani.toString())
        }
    }

}