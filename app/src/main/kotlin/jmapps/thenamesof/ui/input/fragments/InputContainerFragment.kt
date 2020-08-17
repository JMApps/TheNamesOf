package jmapps.thenamesof.ui.input.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.FragmentInputContainerBinding

class InputContainerFragment : Fragment() {

    private lateinit var binding: FragmentInputContainerBinding
    private var sectionNumber: Int? = 0

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input_container, container, false)
        sectionNumber = arguments?.getInt(ARG_CONTENT_NUMBER)
        binding.tvNumberInputName.text = sectionNumber.toString()
        return binding.root
    }
}