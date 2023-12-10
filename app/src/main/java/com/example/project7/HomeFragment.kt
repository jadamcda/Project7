package com.example.project7

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

private var firstRun = true
private var noteUpdated = -1
private var noteList: ArrayList<Note> = ArrayList()

class HomeFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var database: DatabaseReference
    lateinit var viewModel: FirebaseViewModel

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        database = Firebase.database.reference

        val userMap = HashMap<String?, ArrayList<Note>>()

        val currentUser = viewModel.auth.currentUser

        val noteButton = view.findViewById<ImageView>(R.id.noteButton)
        val userButton = view.findViewById<ImageView>(R.id.userButton)
        val loginText = view.findViewById<TextView>(R.id.loginText)
        recycler = view.findViewById(R.id.recycler)

        recycler.setHasFixedSize(true)
        val layout = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recycler.layoutManager = layout

        val userID = viewModel.auth.currentUser?.uid

        if (currentUser == null) {
            recycler.visibility = View.INVISIBLE
            loginText.visibility = View.VISIBLE
        }
        else{
            recycler.visibility = View.VISIBLE
            loginText.visibility = View.INVISIBLE
        }

        if(firstRun == false) {
            val title = HomeFragmentArgs.fromBundle(requireArguments()).title
            val description = HomeFragmentArgs.fromBundle(requireArguments()).description
            val note = Note(title, description)

            if(noteUpdated >= 0){
                noteList.set(noteUpdated, note)

                userMap.put(userID, noteList)
                database.child("users").setValue(userMap)

                noteAdapter = NoteAdapter(noteList)
                recycler.adapter = noteAdapter
            }
            else if(noteUpdated == -1){
                noteList.add(note)

                userMap.put(userID, noteList)
                database.child("users").setValue(userMap)

                println(userID)

                noteAdapter = NoteAdapter(noteList)
                recycler.adapter = noteAdapter
            }
            else{
                noteAdapter = NoteAdapter(noteList)
                recycler.adapter = noteAdapter
            }

            noteAdapter.onNoteClick = {
                noteUpdated = noteList.indexOf(it)

                val action = HomeFragmentDirections.actionHomeFragmentToNoteFragment(it.title, it.description)
                view.findNavController().navigate(action)

            }

            noteAdapter.onButtonClick = {
                noteList.remove(it)
                noteAdapter.thisNoteList = noteList
                recycler.adapter = noteAdapter
            }
        }

        noteButton.setOnClickListener {
            firstRun = false
            noteUpdated = -1

            val action = HomeFragmentDirections.actionHomeFragmentToNoteFragment("", "")
            view.findNavController().navigate(action)
        }

        userButton.setOnClickListener {
            noteUpdated = -2
            view.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUserFragment())
        }

        return view
    }
}