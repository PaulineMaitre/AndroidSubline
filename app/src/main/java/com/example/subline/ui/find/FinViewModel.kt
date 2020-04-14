package com.example.subline.ui.find

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FinViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is find Fragment"
    }
    val text: LiveData<String> = _text
}