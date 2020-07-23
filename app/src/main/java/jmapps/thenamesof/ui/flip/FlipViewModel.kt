package jmapps.thenamesof.ui.flip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FlipViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Карточки"
    }

    val text: LiveData<String> = _text
}