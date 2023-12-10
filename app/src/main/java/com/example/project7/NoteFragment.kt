package com.example.project7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController


class NoteFragment : Fragment() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note, container, false)

        val titleEditTxt = view.findViewById<EditText>(R.id.titleEditTxt)
        val descriptionEditTxt = view.findViewById<EditText>(R.id.descriptionEditTxt)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        val title = NoteFragmentArgs.fromBundle(requireArguments()).title
        val description = NoteFragmentArgs.fromBundle(requireArguments()).description

        titleEditTxt.setText(title)
        descriptionEditTxt.setText(description)


        saveButton.setOnClickListener {
            val action = NoteFragmentDirections
                .actionNoteFragmentToHomeFragment(titleEditTxt.text.toString(), descriptionEditTxt.text.toString())
            view.findNavController().navigate(action)
        }

        return view
    }
}