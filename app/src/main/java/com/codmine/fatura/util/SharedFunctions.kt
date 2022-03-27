package com.codmine.fatura.util

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

fun StringToFloat(inputString : String) : Float {
    val output = try {
        inputString.toFloat()
    } catch (e: NumberFormatException) {
        0F
    }
    return output
}