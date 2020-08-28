package jmapps.thenamesof.ui.input.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.AllNamesContent
import jmapps.thenamesof.data.database.DBOpenAllNames
import jmapps.thenamesof.databinding.FragmentInputContainerBinding
import jmapps.thenamesof.ui.flip.model.FlipListModel

class InputContainerFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentInputContainerBinding
    private var database: SQLiteDatabase? = null

    private var sectionNumber: Int? = 0
    private var mediaPlayer: MediaPlayer? = null

    private var trueValue: Int? = null

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var flipNameList: MutableList<FlipListModel>
    private lateinit var toNextPagerPosition: ToNextPagerPosition

    companion object {

        private const val ARG_CONTENT_NUMBER = "input_number"
        const val keyLastInputData = "key_last_input_data"
        const val keyTrueInputData = "key_true_input_data"

        @JvmStatic
        fun newInstance(dataNumber: Int): InputContainerFragment {
            return InputContainerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CONTENT_NUMBER, dataNumber)
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

        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        editor = preferences.edit()

        binding.tvInputName.text = flipNameList[sectionNumber!! - 1].flipNameArabic

        trueValue = preferences.getInt(keyTrueInputData, 0)
        Toast.makeText(requireContext(), "$trueValue", Toast.LENGTH_SHORT).show()

        binding.ibtnPlayInputName.setOnClickListener(this)
        binding.ibtnToNextInputName.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.ibtnPlayInputName -> {
                playName()
            }

            R.id.ibtnToNextInputName -> {
                toNextInputName()
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

    private fun toNextInputName() {
        if (editTextIsNull()) {
            binding.tvWriteInputNameState.text = binding.etInputName.text
            saveInputData()
            closeKeyboard()
            checkInput()
            binding.etInputName.text.clear()
            invisibleInputViews()
        } else {
            Toast.makeText(requireContext(), "Введите текст", Toast.LENGTH_SHORT).show()
        }
    }

    private fun editTextIsNull(): Boolean {
        return !binding.etInputName.text.isNullOrEmpty()
    }

    private fun closeKeyboard() {
        val keyboard: InputMethodManager =
            (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)!!
        keyboard.hideSoftInputFromWindow(binding.etInputName.windowToken, 0)
    }

    private fun checkInput() {
        if (binding.etInputName.text.toString() == flipNameList[sectionNumber!! - 1].flipNameTranslation) {
            binding.tvCheckInputTrue.visibility = View.VISIBLE
            binding.tvCheckInputFalse.visibility = View.GONE
        } else {
            binding.tvCheckInputFalse.visibility = View.VISIBLE
            binding.tvCheckInputTrue.visibility = View.GONE
        }
    }

    private fun saveInputData() {
        if (binding.etInputName.text.toString() == flipNameList[sectionNumber!! - 1].flipNameTranslation) {
            editor.putInt(keyTrueInputData, trueValue!! + 1).apply()
        }
        editor.putInt(keyLastInputData, sectionNumber!!).apply()
    }

    private fun invisibleInputViews() {
        binding.etInputName.visibility = View.GONE
        binding.ibtnToNextInputName.visibility = View.GONE
        Handler().postDelayed({
            toNextPagerPosition.nextPagerPosition(sectionNumber!!)
        }, 2500)
    }
}