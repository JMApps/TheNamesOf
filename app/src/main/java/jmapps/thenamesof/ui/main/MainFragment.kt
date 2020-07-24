package jmapps.thenamesof.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.FragmentInputBinding
import jmapps.thenamesof.databinding.FragmentMainBinding
import jmapps.thenamesof.ui.input.InputViewModel
import jmapps.thenamesof.ui.main.adapter.MainContentPagerAdapter

class MainFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        val mainContentPagerAdapter = MainContentPagerAdapter(childFragmentManager)
        binding.mainContentViewPager.adapter = mainContentPagerAdapter

        mainViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        return binding.root
    }
}