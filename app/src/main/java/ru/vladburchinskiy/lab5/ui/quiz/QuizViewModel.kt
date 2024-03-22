package ru.vladburchinskiy.lab5.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Здесь будет викторина"
    }
    val text: LiveData<String> = _text
}