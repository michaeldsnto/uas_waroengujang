package com.example.uas_waroengujang.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.uas_waroengujang.model.Cart
import com.example.uas_waroengujang.util.buildDb
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

    fun updateCartJumlah(cartId: Int, newJumlah: Int, tableNumber: String) {
        launch {
            try {
                val db = buildDb(getApplication())
                db.waroengDao().updateCartJumlah(cartId, newJumlah)
                fetchMenuFromDatabase(tableNumber)
            } catch (e: Exception) {
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}