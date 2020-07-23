package jmapps.thenamesof.ui.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InputViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Режим ввода имён"
    }

    val text: LiveData<String> = _text
}