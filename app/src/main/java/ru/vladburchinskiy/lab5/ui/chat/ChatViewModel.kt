package ru.vladburchinskiy.lab5.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Здесь будет чат"
    }
    val text: LiveData<String> = _text
}