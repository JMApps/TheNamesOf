package jmapps.thenamesof.ui.content.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.BottomSheetContentSettingsBinding

class SettingsContentBottomSheet : BottomSheetDialogFragment(), SeekBar.OnSeekBarChangeListener {

    override fun getTheme(): Int = R.style.CustomBottomSheetDialog

    private lateinit var binding: BottomSheetContentSettingsBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var textSizeValues = (16..34).toList().filter { it % 2 == 0 }

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_content_settings, container, false)

        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        editor = preferences.edit()

        val lastTextSizeValue = preferences.getInt(keyContentTextSize, 1)
        binding.sbContentTextSize.progress = lastTextSizeValue
        binding.tvContentTextSizeCount.text = textSizeValues[lastTextSizeValue].toString()

        binding.sbContentTextSize.setOnSeekBarChangeListener(this)

        return binding.root
    }

    override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
        binding.tvContentTextSizeCount.text = textSizeValues[progress].toString()
        editor.putInt(keyContentTextSize, progress).apply()
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {}

    override fun onStopTrackingTouch(p0: SeekBar?) {}

    companion object {
        const val keyContentSettings = "key_content_settings"
        const val keyContentTextSize = "key_content_text_size"
    }
}