package jmapps.thenamesof.ui.flip

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
import jmapps.thenamesof.databinding.BottomSheetFlipListSettingsBinding

class SettingsFlipListBottomSheet : BottomSheetDialogFragment(), SeekBar.OnSeekBarChangeListener {

    override fun getTheme(): Int = R.style.CustomBottomSheetDialog

    private lateinit var binding: BottomSheetFlipListSettingsBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var textSizeValues = (22..40).toList().filter { it % 2 == 0 }

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_flip_list_settings, container, false)

        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        editor = preferences.edit()

        val lastTextSizeValue = preferences.getInt(keyFlipNamesTextSize, 4)
        binding.sbFlipNameTextSize.progress = lastTextSizeValue
        binding.tvFlipNameTextSizeCount.text = textSizeValues[lastTextSizeValue].toString()

        binding.sbFlipNameTextSize.setOnSeekBarChangeListener(this)

        return binding.root
    }

    override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
        binding.tvFlipNameTextSizeCount.text = textSizeValues[progress].toString()
        editor.putInt(keyFlipNamesTextSize, progress).apply()
    }

    override fun onStartTrackingTouch(seekbar: SeekBar?) {}

    override fun onStopTrackingTouch(seekbar: SeekBar?) {}

    companion object {
        const val keyFlipNamesSettings = "key_flip_names_settings"
        const val keyFlipNamesTextSize = "key_flip_names_text_size"
    }
}