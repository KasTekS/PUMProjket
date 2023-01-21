package com.example.tictactoe.ui.onlyx

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnlyXViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Witaj Miłej Zabawy \n Wybierz tryb gry:"
    }
    val text: LiveData<String> = _text
}