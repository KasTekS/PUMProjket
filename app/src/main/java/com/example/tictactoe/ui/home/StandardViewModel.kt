package com.example.tictactoe.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StandardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Witaj Miłej Zabawy \n Wybierz tryb gry:"
    }
    val text: LiveData<String> = _text

}