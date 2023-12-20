package com.example.uas_waroengujang.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.uas_waroengujang.model.Waitress

class LoginViewModel:ViewModel() {
    val usersLD = MutableLiveData<List<Waitress>>()

    fun login() {
        val waitress1 = Waitress(
            "1",
            "Michael",
            "michael",
            "12345",
            "Work since 8th August 2023",
            "https://www.mens-hairstyle.com/wp-content/uploads/2014/12/Trendy-Men-Haircut.jpg")
        val waitress2 = Waitress(
            "2",
            "Jeconiah",
            "jeconiah",
            "12345",
            "Work since 9th August 2023",
            "https://menshairstyletrends.com/wp-content/uploads/2015/08/Best-Mens-Haircuts-for-Summer-Sebastian-Hallqvist.jpg"
        )
        val waitress3 = Waitress(
            "3",
            "Bryan",
            "bryan",
            "12345",
            "Work since 10th August 2023",
            "https://i.pinimg.com/736x/ce/ef/0d/ceef0d01dede265d7b4f659658fa21ab.jpg"
        )
        usersLD.value = arrayListOf<Waitress>(waitress1, waitress2, waitress3)

    }

}


