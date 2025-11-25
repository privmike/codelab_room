package com.example.codelab_room.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Note(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    var id: Int=0,

    @ColumnInfo(name = "judul")
    var judul: String?= null,

    @ColumnInfo(name = "deskripsi")
    var deskripsi: String?=null,

    @ColumnInfo(name = "tanggal")
    val tanggal: String?=null
)
