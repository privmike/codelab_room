package com.example.codelab_room

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.codelab_room.database.Note
import com.example.codelab_room.database.NoteRoomDatabase
import com.example.codelab_room.helper.DateHelper
import com.example.codelab_room.helper.DateHelper.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class TambahData : AppCompatActivity() {
    val DB : NoteRoomDatabase = NoteRoomDatabase.getDatabase(this)
    var tanggal : String = getCurrentDate()

    lateinit var _etJudul : EditText
    lateinit var _etDeskripsi : EditText
    lateinit var _btnTambah : Button
    lateinit var _btnUpdate : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_data)



        _etJudul = findViewById(R.id.etJudul)
        _etDeskripsi = findViewById(R.id.etDeskripsi)
        _btnTambah = findViewById(R.id.btnTambah)
        _btnUpdate = findViewById(R.id.btnUpdate)

        _btnTambah.setOnClickListener {
            _btnTambah.isEnabled = false
            CoroutineScope(Dispatchers.IO).async {
                DB.funnoteDAO().insert(
                    Note(
                        0,
                        _etJudul.text.toString(),
                        _etDeskripsi.text.toString(),
                        tanggal
                    )
                )
                finish()
            }
        }

        var iID: Int =0
        var iAddEdit: Int = 0
        iID = intent.getIntExtra("noteId",0)
        iAddEdit= intent.getIntExtra("addEdit",0)

        if (iAddEdit ==0){
            _btnTambah.visibility = View.VISIBLE
            _btnUpdate.visibility = View.GONE
            _etJudul.isEnabled = true
        }else{
            _btnTambah.visibility = View.GONE
            _btnUpdate.visibility = View.VISIBLE
            _etJudul.isEnabled = false
            CoroutineScope(Dispatchers.IO).async {
                val noteItem = DB.funnoteDAO().getNote(iID)
                _etJudul.setText(noteItem.judul)
                _etDeskripsi.setText(noteItem.deskripsi)
            }
        }

        _btnUpdate.setOnClickListener {
            _btnUpdate.isEnabled = false // solusi duolikat entry saat buttn diklik berkali2
            CoroutineScope(Dispatchers.IO).async {
                DB.funnoteDAO().update(
                    _etJudul.text.toString(),
                    _etDeskripsi.text.toString(),
                    iID
                )
                finish()
            }
        }


    }
}