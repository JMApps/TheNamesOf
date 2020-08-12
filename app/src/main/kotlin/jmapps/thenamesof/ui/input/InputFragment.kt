package jmapps.thenamesof.ui.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.FragmentInputBinding
import jmapps.thenamesof.ui.input.adapter.InputContentPagerAdapter

class InputFragment : Fragment() {

    private lateinit var inputViewModel: InputViewModel
    private lateinit var binding: FragmentInputBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inputViewModel = ViewModelProvider(this).get(InputViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input, container, false)

        val inputContentPagerAdapter = InputContentPagerAdapter(childFragmentManager)
        binding.inputContentViewPager.adapter = inputContentPagerAdapter

        inputViewModel.text.observe(viewLifecycleOwner, Observer {

        })

        return binding.root
    }
}