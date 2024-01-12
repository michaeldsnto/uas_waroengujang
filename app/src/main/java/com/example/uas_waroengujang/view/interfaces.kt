package com.example.uas_waroengujang.view

import android.view.View
import com.example.uas_waroengujang.model.Cart
import com.example.uas_waroengujang.model.Menu

interface LoginClickListener {
    fun onLoginClicked(username: String, password: String)
}
interface SetTableNumber {
    fun onSubmitTableNumber(number: Int)
}
interface MenuDetailListener {
    fun onMenuDetailClick(menu: Menu, v: View)
}
interface HomeListener {
    fun onChangeClick(v: View)
}
interface CheckoutListener {
    fun onCheckoutClicked()
}

