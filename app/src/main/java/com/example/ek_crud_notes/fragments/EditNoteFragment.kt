package com.example.ek_crud_notes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ek_crud_notes.MainActivity
import com.example.ek_crud_notes.R
import com.example.ek_crud_notes.databinding.FragmentEditNoteBinding
import com.example.ek_crud_notes.model.Note
import com.example.ek_crud_notes.viewmodel.NoteViewmodel


class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {

    private var editNoteBinding: FragmentEditNoteBinding? = null
    private val binding get() = editNoteBinding!!

    private lateinit var notesViewmodel: NoteViewmodel
    private lateinit var currentNote: Note

    private val args: EditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editNoteBinding = FragmentEditNoteBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

        notesViewmodel = (activity as MainActivity).noteViewmodel
        currentNote = args.note!!

        binding.editNoteTitle.setText(currentNote.noteTitle)
        binding.editNoteDesc.setText(currentNote.noteDesc)

        binding.editNoteFab.setOnClickListener{
            val noteTitle = binding.editNoteTitle.text.toString().trim()
            val noteDesc = binding.editNoteDesc.text.toString().trim()

            if(noteTitle.isNotEmpty()){
                val note = Note(currentNote.id, noteTitle, noteDesc)
                notesViewmodel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment, false)

            } else {
                Toast.makeText(context, "Enter note title", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteNote(){
        AlertDialog.Builder(activity as MainActivity).apply {
            setTitle("Delete note")
            setMessage("Do you want to delete this note?")
            setPositiveButton("Delete"){_,_ ->
                notesViewmodel.deleteNote(currentNote)
                Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deleteNote()
                true
            } else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding = null
    }
}