package jmapps.thenamesof.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.FragmentMainBinding
import jmapps.thenamesof.ui.main.adapter.MainContentPagerAdapter

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        val mainContentPagerAdapter = MainContentPagerAdapter(childFragmentManager)
        binding.mainContentViewPager.adapter = mainContentPagerAdapter

        return binding.root
    }
}