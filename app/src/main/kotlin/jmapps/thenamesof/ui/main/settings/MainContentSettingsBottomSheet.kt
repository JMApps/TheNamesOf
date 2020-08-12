package jmapps.thenamesof.ui.main.settings

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.BottomSheetMainContentSettingsBinding

class MainContentSettingsBottomSheet : BottomSheetDialogFragment(),
    SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    override fun getTheme() = R.style.CustomBottomSheetDialog

    private lateinit var binding: BottomSheetMainContentSettingsBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var namesSizeValues = (16..34).toList().filter { it % 2 == 0 }
    private var ayahSizeValues = (16..34).toList().filter { it % 2 == 0 }
    private var contentSizeValues = (16..34).toList().filter { it % 2 == 0 }

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_main_content_settings, container, false)

        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        editor = preferences.edit()

        val lastTextSizeProgress = preferences.getInt(keyMainNamesTextSize, 1)
        val lastAyahSizeProgress = preferences.getInt(keyMainAyahsTextSize, 1)
        val lastContentSizeProgress = preferences.getInt(keyMainContentsTextSize, 1)

        val lastShowArabicState = preferences.getBoolean(keyShowNameArabicState, true)
        val lastShowTranscriptionState = preferences.getBoolean(keyShowNameTranscriptionState, true)
        val lastShowTranslationState = preferences.getBoolean(keyShowNameTranslationState, true)

        binding.apply {
            tvMainNamesTextSizeCount.text = namesSizeValues[lastTextSizeProgress].toString()
            tvMainAyahsTextSizeCount.text = ayahSizeValues[lastAyahSizeProgress].toString()
            tvMainContentsTextSizeCount.text = contentSizeValues[lastContentSizeProgress].toString()

            sbMainNamesTextSize.progress = lastTextSizeProgress
            sbMainAyahsTextSize.progress = lastAyahSizeProgress
            sbMainContentTextSize.progress = lastContentSizeProgress

            swShowNameArabic.isChecked = lastShowArabicState
            swShowNameTranscription.isChecked = lastShowTranscriptionState
            swShowNameTranslation.isChecked = lastShowTranslationState
        }

        binding.sbMainNamesTextSize.setOnSeekBarChangeListener(this)
        binding.sbMainAyahsTextSize.setOnSeekBarChangeListener(this)
        binding.sbMainContentTextSize.setOnSeekBarChangeListener(this)

        binding.swShowNameArabic.setOnCheckedChangeListener(this)
        binding.swShowNameTranscription.setOnCheckedChangeListener(this)
        binding.swShowNameTranslation.setOnCheckedChangeListener(this)

        return binding.root
    }

    override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
        when (seekbar?.id) {

            R.id.sbMainNamesTextSize -> {
                binding.tvMainNamesTextSizeCount.text = namesSizeValues[progress].toString()
                editor.putInt(keyMainNamesTextSize, progress).apply()
            }

            R.id.sbMainAyahsTextSize -> {
                binding.tvMainAyahsTextSizeCount.text = ayahSizeValues[progress].toString()
                editor.putInt(keyMainAyahsTextSize, progress).apply()
            }

            R.id.sbMainContentTextSize -> {
                binding.tvMainContentsTextSizeCount.text = contentSizeValues[progress].toString()
                editor.putInt(keyMainContentsTextSize, progress).apply()
            }
        }
    }

    override fun onStartTrackingTouch(seekbar: SeekBar?) {}

    override fun onStopTrackingTouch(seekbar: SeekBar?) {}

    companion object {
        const val keyMainContentSettings = "key_main_content_settings"

        const val keyMainNamesTextSize = "key_main_names_text_size"
        const val keyMainAyahsTextSize = "key_main_ayahs_text_size"
        const val keyMainContentsTextSize = "key_main_contents_text_size"

        const val keyShowNameArabicState = "key_show_name_arabic"
        const val keyShowNameTranscriptionState = "key_show_name_transcription"
        const val keyShowNameTranslationState = "key_show_name_translation"
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {

            R.id.swShowNameArabic -> {
                if (!isChecked) {
                    binding.swShowNameTranslation.isChecked = true
                }
                editor.putBoolean(keyShowNameArabicState, isChecked).apply()
            }

            R.id.swShowNameTranscription -> {
                editor.putBoolean(keyShowNameTranscriptionState, isChecked).apply()
            }

            R.id.swShowNameTranslation -> {
                if (!isChecked) {
                    binding.swShowNameArabic.isChecked = true
                }
                editor.putBoolean(keyShowNameTranslationState, isChecked).apply()
            }
        }
    }
}