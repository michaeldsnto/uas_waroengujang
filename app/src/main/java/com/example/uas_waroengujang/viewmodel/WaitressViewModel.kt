package com.example.uas_waroengujang.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.uas_waroengujang.model.Waitress
import com.example.uas_waroengujang.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WaitressViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    private var job = Job()
    private val waitressId = MutableLiveData<String>()
    val waitressLD = MutableLiveData<Waitress?>()

    fun getWaitressId(): LiveData<String> {
        return waitressId
    }

    fun setWaitressId(id: String) {
        waitressId.value = id
    }
    fun addWaitress(waitress: Waitress){
        launch {
            val db = buildDb(getApplication())
            db.waroengDao().insertWaitress(waitress)
        }
    }
    fun fetch(id: String) {
        launch {
            val db = buildDb(getApplication())
            val waitress = db.waroengDao().selectWaitress(id)
            waitress?.let {
                waitressLD.postValue(waitress)
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}
