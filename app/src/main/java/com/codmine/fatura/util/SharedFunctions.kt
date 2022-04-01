package com.codmine.fatura.util

import androidx.compose.ui.text.input.TextFieldValue

fun formatDate (year : Int, month : Int, dayOfMonth : Int) : String {
    val dayOfMonthStr = if(dayOfMonth.toString().length == 1) "0${dayOfMonth}" else dayOfMonth.toString()
    val monthStr = if((month + 1).toString().length == 1) "0${(month + 1)}" else (month + 1).toString()
    return "$dayOfMonthStr/$monthStr/$year"
}

fun formatTime (hour : Int, minute : Int, second : Int) : String {
    val hourStr = if(hour.toString().length == 1) "0${hour}" else hour.toString()
    val minuteStr = if(minute .toString().length == 1) "0${minute}" else minute.toString()
    val secondStr = if(second .toString().length == 1) "0${second}" else second.toString()
    return "$hourStr:$minuteStr:$secondStr"
}

fun stringToFloat(inputValue : String) : Float {
    val output = try {
        inputValue.toFloat()
    } catch (e: NumberFormatException) {
        0F
    }
    return output
}

fun floatToString(inputValue : Float) : String {
    return String.format("%.2f",inputValue)
}

fun textToTextField(inputValue : String) : TextFieldValue {
    return TextFieldValue(floatToString(stringToFloat(inputValue)))
}

fun floatToTextField(inputValue : Float) : TextFieldValue {
    return TextFieldValue(floatToString(inputValue))
}