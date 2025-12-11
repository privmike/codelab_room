package com.example.codelab_room

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codelab_room.database.Note
import com.example.codelab_room.database.NoteRoomDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var adapterN: adapterNote
    private var arNote: MutableList<Note> = mutableListOf()
    private lateinit var DB: NoteRoomDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        DB = NoteRoomDatabase.getDatabase(this)

        val _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)

        _fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahData::class.java))
        }

        adapterN = adapterNote(arNote)
        val _rvNotes = findViewById<RecyclerView>(R.id.rvNotes)
        _rvNotes.layoutManager = LinearLayoutManager(this)
        _rvNotes.adapter = adapterN

        adapterN.setOnItemClickCallback(
            object : adapterNote.OnItemClickCallback{
                override fun delData(dtnote: Note) {
                    CoroutineScope(Dispatchers.IO).async {
                        DB.funnoteDAO().delete(dtnote)
                        val note = DB.funnoteDAO().selectALl()
                        Log.d("Data Room2", note.toString())

                        withContext(Dispatchers.Main){
                            adapterN.isiData(note)//update data terbaru yg udh omit data yg terhapus
                        }
                    }
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val note = DB.funnoteDAO().selectALl()
            Log.d("data Room", note.toString())
            adapterN.isiData(note)
        }
    }


}