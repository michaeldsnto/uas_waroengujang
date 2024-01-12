package com.example.uas_waroengujang.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.uas_waroengujang.model.Menu
import com.example.uas_waroengujang.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.IOException
import java.net.URL
import kotlin.coroutines.CoroutineContext

class MenuViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private var job = Job()
    private val db = buildDb(application)
    val menuLD = MutableLiveData<List<Menu>>()
    val menuLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    fun fetchMenuDataFromJson() {
        loadingLD.value = true
        menuLoadErrorLD.value = false

        launch {
            try {
                val jsonStr = URL("http://10.0.2.2/anmp/menu.json").readText()
                val jsonArray = JSONArray(jsonStr)
                val menuList = mutableListOf<Menu>()

                for (i in 0 until jsonArray.length()) {
                    val jsonObj = jsonArray.getJSONObject(i)
                    val menu = Menu(
                        jsonObj.getString("id"),
                        jsonObj.getString("nama_makanan"),
                        jsonObj.getString("kategori"),
                        jsonObj.getInt("harga"),
                        jsonObj.getString("deskripsi"),
                        jsonObj.getString("photoUrl")
                    )
                    menuList.add(menu)
                }

                db.waroengDao().insertMenu(menuList)

                loadingLD.postValue(false)
            } catch (e: IOException) {
                menuLoadErrorLD.postValue(true)
                loadingLD.postValue(false)
            }

        }
    }

    fun fetchMenuFromDatabase() {
        loadingLD.value = true
        menuLoadErrorLD.value = false

        launch {
            try {
                val db = buildDb(getApplication())

                menuLD.postValue(db.waroengDao().selectAllMenu())

                loadingLD.postValue(false)
            } catch (e: Exception) {
                menuLoadErrorLD.postValue(true)
                loadingLD.postValue(false)
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
