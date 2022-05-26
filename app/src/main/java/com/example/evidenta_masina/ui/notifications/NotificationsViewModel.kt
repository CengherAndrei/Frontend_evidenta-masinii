package com.example.evidenta_masina.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
/**
 * clasa de model pentru act
 */
class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Completati campurile urmatoare "
    }
    val text: LiveData<String> = _text
}