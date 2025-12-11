package com.example.codelab_room

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codelab_room.database.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class adapterNote(private val listNote: MutableList<Note>) : RecyclerView.Adapter<adapterNote.ListViewHolder>(){


    private lateinit var onItemClickCallback: OnItemClickCallback



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterNote.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_recyclerview,parent,false)

        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    override fun onBindViewHolder(holder: adapterNote.ListViewHolder, position: Int) {
        var note: Note = listNote[position]

        holder._tvJudul.setText(note.judul)
        holder._tvDeskripsi.setText(note.deskripsi)
        holder._tvTanggal.setText(note.tanggal)

        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahData::class.java)
            intent.putExtra("noteId", note.id)
            intent.putExtra("addEdit",1)
            it.context.startActivity(intent)
        }
        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(note)
        }
    }
    fun isiData(list: List<Note>){
        listNote.clear()
        listNote.addAll(list)
        notifyDataSetChanged()
    }

    inner class ListViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        var _tvJudul : TextView = itemView.findViewById(R.id.judulnote)
        var _tvDeskripsi : TextView = itemView.findViewById(R.id.deskripsinote)
        var _tvTanggal : TextView = itemView.findViewById(R.id.tanggalnote)

        var _btnEdit : ImageButton = itemView.findViewById(R.id.btnedit)
        var _btnDelete : ImageButton = itemView.findViewById(R.id.btndelete)

    }

    interface OnItemClickCallback{
        fun delData(dtnote: Note){

        }
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

}