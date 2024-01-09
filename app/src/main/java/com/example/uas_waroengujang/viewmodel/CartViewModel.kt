package com.example.uas_waroengujang.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.uas_waroengujang.model.Cart

class CartViewModel: ViewModel() {
    // MASIH SALAH INI VIEW MODELNYA
    val cartLD = MutableLiveData<ArrayList<Cart>?>()
    val subtotalLD = MutableLiveData<Int>()
    val taxLD = MutableLiveData<Int>()
    val totalLD = MutableLiveData<Int>()

    init {
        cartLD.value = ArrayList()
        subtotalLD.value = 0
        taxLD.value = 0
        totalLD.value = 0
    }

    fun addMenuToCart(namaMenu: String, jumlah: Int, harga: Int, photoUrl: String) {
        val cartList = cartLD.value ?: ArrayList()
        var cartItem: Cart? = null

        for (item in cartList) {
            if (item.nama == namaMenu) {
                cartItem = item
                break
            }
        }

        if (cartItem != null) {
            cartItem.jumlah += jumlah
        } else {
            cartList.add(Cart(namaMenu, jumlah, harga))
        }

        cartLD.value = cartList
        recalculateTotals()
    }
    fun updateQuantity(namaMenu: String, newQuantity: Int, harga:Int) {
        val cartList = cartLD.value ?: return
        val updatedCartList = ArrayList<Cart>()
        for (menu in cartList) {
            if (menu.nama == namaMenu) {
                if (newQuantity > 0) {
                    menu.jumlah = newQuantity
                    menu.harga*newQuantity
                    updatedCartList.add(menu)
                }
            } else {
                updatedCartList.add(menu)
            }
        }
        cartLD.value = updatedCartList
        recalculateTotals()
    }
    private fun recalculateTotals() {
        val cartList = cartLD.value

        if (cartList != null) {
            var subtotal = 0
            for (item in cartList) {
                subtotal += item.harga * item.jumlah
            }
            subtotalLD.value = subtotal

            val tax = (subtotal * 0.1).toInt()
            taxLD.value = tax

            val total = subtotal + tax
            totalLD.value = total
        }
    }
}