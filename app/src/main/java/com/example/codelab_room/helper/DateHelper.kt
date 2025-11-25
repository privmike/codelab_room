package com.example.codelab_room.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper {

    fun getCurrentDate(): String{
        val dateFormat = SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss", Locale.getDefault()
        )
        val data = Date()
        return dateFormat.format(data)
    }
}