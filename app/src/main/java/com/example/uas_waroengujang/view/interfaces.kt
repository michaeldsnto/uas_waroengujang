package com.example.uas_waroengujang.view

interface LoginClickListener {
    fun onLoginClicked(username: String, password: String)
}
interface  SetTableNumber {
    fun onSubmitTableNumber(number: Int)
}