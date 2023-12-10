package com.example.project7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val noteList:ArrayList<Note>)
    : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

    var onButtonClick : ((Note) -> Unit)? = null
    var onNoteClick : ((Note) -> Unit)? = null
    var thisNoteList = noteList

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageButton: ImageButton = itemView.findViewById(R.id.imageButton)
        val title : TextView = itemView.findViewById(R.id.title)
        val description : TextView = itemView.findViewById(R.id.description)
        val card : CardView = itemView.findViewById(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_view, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = thisNoteList[position]
        holder.title.text = note.title
        holder.description.text = note.description

        holder.imageButton.setOnClickListener{
            onButtonClick?.invoke(note)
        }

        holder.card.setOnClickListener{
            onNoteClick?.invoke(note)
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }










}