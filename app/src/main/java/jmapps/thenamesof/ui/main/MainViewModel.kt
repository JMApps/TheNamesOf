package jmapps.thenamesof.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Толкование имен"
    }
    val text: LiveData<String> = _text
}