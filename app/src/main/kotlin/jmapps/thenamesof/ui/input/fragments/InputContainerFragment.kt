package jmapps.thenamesof.ui.input.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.AllNamesContent
import jmapps.thenamesof.data.database.DBOpenAllNames
import jmapps.thenamesof.databinding.FragmentInputContainerBinding
import jmapps.thenamesof.ui.flip.model.FlipListModel

class InputContainerFragment : Fragment(), View.OnClickListener, TextView.OnEditorActionListener {

    private lateinit var binding: FragmentInputContainerBinding
    private var database: SQLiteDatabase? = null

    private var sectionNumber: Int? = null
    private var inputMode: Boolean? = null
    private var mediaPlayer: MediaPlayer? = null

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var flipNameList: MutableList<FlipListModel>
    private lateinit var toNextPagerPosition: ToNextPagerPosition

    companion object {

        private const val ARG_CONTENT_NUMBER = "key_input_number"
        private const val ARG_LANGUAGE_MODE = "key_language_mode"

        const val keyLastInputDataArabic = "key_last_input_data_arabic"
        const val keyLastInputDataTranslation = "key_last_input_data_translation"

        const val keyTrueInputDataArabic = "key_true_input_data_arabic"
        const val keyTrueInputDataTranslation = "key_true_input_data_translation"

        @JvmStatic
        fun newInstance(dataNumber: Int, inputMode: Boolean): InputContainerFragment {
            return InputContainerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CONTENT_NUMBER, dataNumber)
                    putBoolean(ARG_LANGUAGE_MODE, inputMode)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = DBOpenAllNames(requireContext()).readableDatabase
        flipNameList = AllNamesContent(database!!).getFlipNamesList
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input_container, container, false)
        sectionNumber = arguments?.getInt(ARG_CONTENT_NUMBER)
        inputMode = arguments?.getBoolean(ARG_LANGUAGE_MODE)

        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        editor = preferences.edit()

        if (inputMode!!) {
            binding.tvInputName.text = flipNameList[sectionNumber!! - 1].flipNameArabic
        } else {
            binding.tvInputName.text = flipNameList[sectionNumber!! - 1].flipNameTranslation
        }
        
        binding.ibtnPlayInputName.setOnClickListener(this)
        binding.ibtnToNextInputName.setOnClickListener(this)
        binding.etInputName.setOnEditorActionListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ibtnPlayInputName -> {
                playName()
            }

            R.id.ibtnToNextInputName -> {
                toNextInputName(inputMode!!)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ToNextPagerPosition) {
            toNextPagerPosition = context
        } else throw RuntimeException("$context must implement this interface")
    }

    interface ToNextPagerPosition {
        fun nextPagerPosition(position: Int)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        database?.close()
    }

    private fun playName() {
        clearPlayer()
        val resId = context?.resources?.getIdentifier(
            flipNameList[sectionNumber!! - 1].flipNameAudio, "raw", "jmapps.thenamesof")
        mediaPlayer = MediaPlayer.create(context, resId!!)
        mediaPlayer?.start()
    }

    private fun clearPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun toNextInputName(inputMode: Boolean) {
        if (!binding.etInputName.text.isNullOrEmpty()) {
            saveInputData(inputMode)
            checkInput()
            closeKeyboard()
            binding.tvWriteInputNameState.text = binding.etInputName.text.toString()
            binding.etInputName.text!!.clear()
            invisibleInputViews()
        } else {
            Toast.makeText(requireContext(), getString(R.string.action_add_text), Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveInputData(inputMode: Boolean) {
        val trueValueArabic = preferences.getInt(keyTrueInputDataArabic, 0)
        val trueValueTranslation = preferences.getInt(keyTrueInputDataTranslation, 0)

        val current = flipNameList[sectionNumber!! - 1]
        if (inputMode) {
            if (binding.etInputName.text.toString() == current.flipNameTranslation) {
                editor.putInt(keyTrueInputDataArabic, trueValueArabic + 1).apply()
            }
            editor.putInt(keyLastInputDataArabic, sectionNumber!!).apply()
        } else {
            if (binding.etInputName.text.toString() == current.flipNameArabic) {
                editor.putInt(keyTrueInputDataTranslation, trueValueTranslation + 1).apply()
            }
            editor.putInt(keyLastInputDataTranslation, sectionNumber!!).apply()
        }
    }

    private fun closeKeyboard() {
        val keyboard: InputMethodManager =
            (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)!!
        keyboard.hideSoftInputFromWindow(binding.etInputName.windowToken, 0)
    }

    private fun checkInput() {
        val current = flipNameList[sectionNumber!! - 1]
        if (binding.etInputName.text.toString() == current.flipNameTranslation ||
            binding.etInputName.text.toString() == current.flipNameArabic) {
            binding.tvCheckInputTrue.visibility = View.VISIBLE
            binding.tvCheckInputFalse.visibility = View.GONE
        } else {
            binding.tvCheckInputTrue.visibility = View.GONE
            binding.tvCheckInputFalse.visibility = View.VISIBLE
        }
    }

    private fun invisibleInputViews() {
        if (sectionNumber!! < 99) {
            binding.etInputName.visibility = View.GONE
            binding.ibtnToNextInputName.visibility = View.GONE
            Handler().postDelayed({
                toNextPagerPosition.nextPagerPosition(sectionNumber!!)
            }, 2500)
        }
    }

    override fun onEditorAction(view: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            toNextInputName(inputMode!!)
            return true
        }
        return false
    }
}