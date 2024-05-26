package com.example.ek_crud_notes.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ek_crud_notes.repository.NoteRepository

class NoteViewModelFactory (val app: Application, private val noteRepository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewmodel(app, noteRepository) as T
    }
}