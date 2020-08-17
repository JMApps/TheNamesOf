package jmapps.thenamesof.ui.input.fragments

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.AllNamesContent
import jmapps.thenamesof.data.database.DBOpenAllNames
import jmapps.thenamesof.databinding.FragmentInputContainerBinding
import jmapps.thenamesof.ui.flip.model.FlipListModel

class InputContainerFragment : Fragment(), TextWatcher {

    private lateinit var binding: FragmentInputContainerBinding
    private var database: SQLiteDatabase? = null

    private var sectionNumber: Int? = 0

    private lateinit var flipNameList: MutableList<FlipListModel>
    private lateinit var toNextPagerPosition: ToNextPagerPosition

    companion object {

        private const val ARG_CONTENT_NUMBER = "input_number"

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input_container, container, false)
        sectionNumber = arguments?.getInt(ARG_CONTENT_NUMBER)
        toNextPagerPosition.nextPagerPosition(sectionNumber!!)

        binding.tvInputName.text = flipNameList[sectionNumber!!].flipNameArabic
        binding.tvWriteInputNameState.text = flipNameList[sectionNumber!!].flipNameTranslation

        binding.etInputName.addTextChangedListener(this)

        return binding.root
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        binding.tvWriteInputNameState.text = "$s"
    }

    interface ToNextPagerPosition {
        fun nextPagerPosition(position: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ToNextPagerPosition) {
            toNextPagerPosition = context
        } else throw RuntimeException("$context must implement this interface")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        database?.close()
    }
}