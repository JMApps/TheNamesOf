package jmapps.thenamesof.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.FragmentMainContainerBinding

class MainContainerFragment : Fragment() {

    private lateinit var binding: FragmentMainContainerBinding

    companion object {

        private const val ARG_CONTENT_NUMBER = "main_content_number"

        @JvmStatic
        fun newInstance(dataNumber: Int): MainContainerFragment {
            return MainContainerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CONTENT_NUMBER, dataNumber)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_container, container, false)

        return binding.root
    }
}