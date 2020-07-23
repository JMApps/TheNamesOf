package jmapps.thenamesof.ui.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContentViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Основной материал"
    }

    val text: LiveData<String> = _text
}