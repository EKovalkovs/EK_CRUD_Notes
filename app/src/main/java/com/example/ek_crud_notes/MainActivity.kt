package com.example.ek_crud_notes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.ek_crud_notes.database.NoteDatabase
import com.example.ek_crud_notes.repository.NoteRepository
import com.example.ek_crud_notes.viewmodel.NoteViewModelFactory
import com.example.ek_crud_notes.viewmodel.NoteViewmodel

class MainActivity : AppCompatActivity() {

    lateinit var noteViewmodel: NoteViewmodel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewmodel()

    }

    private fun setupViewmodel(){
        val noteRepository = NoteRepository (NoteDatabase(this))
        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepository)
        noteViewmodel = ViewModelProvider (this, viewModelProviderFactory)[NoteViewmodel::class.java]
    }
}