package com.example.uas_waroengujang.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.uas_waroengujang.model.Cart
import com.example.uas_waroengujang.model.Menu
import com.example.uas_waroengujang.model.Waitress
import com.example.uas_waroengujang.util.buildDb
import com.example.uas_waroengujang.view.SetTableNumber
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CartViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    private var job = Job()
    private val db = buildDb(application)
    val cartLD = MutableLiveData<ArrayList<Cart>>()
    val menuLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    // MASIH SALAH INI VIEW MODELNYA
    val subtotalLD = MutableLiveData<Int>()
    val taxLD = MutableLiveData<Int>()
    val totalLD = MutableLiveData<Int>()

    init {
        cartLD.value = ArrayList()
        subtotalLD.value = 0
        taxLD.value = 0
        totalLD.value = 0
    }

    fun updateQuantity(jumlah: Int, nama: String, tableNumber: String, id :Int) {
        launch {
            val db = buildDb(getApplication())
            db.waroengDao().updateCart(jumlah, nama, tableNumber, id)
            fetchMenuFromDatabase(tableNumber)
        }
    }

    fun addQuantity(jumlah: Int, nama: String, tableNumber: String, id :Int) {
        launch {
            val newQuantity = jumlah + 1
            updateQuantity(jumlah, nama, tableNumber, id)
        }
    }

    fun minQuantity(cart: Cart, jumlah: Int, nama: String, tableNumber: String, id :Int) {
        launch {
            val newQuantity = jumlah - 1
            if (newQuantity > 0) {
                updateQuantity(jumlah, nama, tableNumber, id)
            } else {
                removeItemFromCart(cart)
            }
        }
    }

    private fun removeItemFromCart(cart: Cart) {
        launch {
            val db = buildDb(getApplication())
            db.waroengDao().deleteCartByNama(cart.nama, cart.tableNumber)
            fetchMenuFromDatabase(cart.tableNumber)
        }
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

    fun fetchMenuFromDatabase(tableNumber: String) {
        loadingLD.value = true
        menuLoadErrorLD.value = false

        launch {
            try {
                val db = buildDb(getApplication())
                cartLD.postValue(db.waroengDao().selectCartByTableNumber(tableNumber) as ArrayList<Cart>?)
                loadingLD.postValue(false)
            } catch (e: Exception) {
                menuLoadErrorLD.postValue(true)
                loadingLD.postValue(false)
            }
        }
    }

    fun addMenuCart(cart: Cart){
        launch {
            val db = buildDb(getApplication())
            db.waroengDao().insertCart(cart)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}