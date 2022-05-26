package com.example.evidenta_masina.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * clasa de model pentru masina
 */
class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Completati campurile urmatoare "
    }
    val text: LiveData<String> = _text
}