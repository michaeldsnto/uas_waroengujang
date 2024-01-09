package com.example.uas_waroengujang.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.uas_waroengujang.model.Menu
import com.example.uas_waroengujang.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    private val db = buildDb(application)
    val menuLD = MutableLiveData<Menu>()
    val loadingLD = MutableLiveData<Boolean>()
    val errorLD = MutableLiveData<Boolean>()

    fun getMenuById(menuId: String) {
        loadingLD.value = true
        errorLD.value = false

        launch {
            try {
                val menu = db.waroengDao().selectMenuById(menuId)
                menu.let {
                    menuLD.postValue(it)
                    loadingLD.postValue(false)
                }
            } catch (e: Exception) {
                loadingLD.postValue(false)
                errorLD.postValue(true)
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
