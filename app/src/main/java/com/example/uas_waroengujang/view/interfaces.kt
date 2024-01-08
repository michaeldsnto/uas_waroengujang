package com.example.uas_waroengujang.view

import android.view.View

interface LoginClickListener {
    fun onLoginClicked(username: String, password: String)
}
interface  SetTableNumber {
    fun onSubmitTableNumber(number: Int)
}
interface MenuDetailListener {
    fun onMenuDetailClick(v: View)
}
interface HomeListener {
    fun onChangeClick(v: View)
}