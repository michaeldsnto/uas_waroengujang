package com.example.uas_waroengujang.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val tableNumber = MutableLiveData<String>()

    fun getTableNumber(): LiveData<String> {
        return tableNumber
    }

    fun setTableNumber(number: String) {
        tableNumber.value = number
    }
}