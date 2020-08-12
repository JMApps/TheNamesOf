package jmapps.thenamesof.ui.input.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import jmapps.thenamesof.ui.input.InputContainerFragment
import jmapps.thenamesof.ui.main.MainContainerFragment

class InputContentPagerAdapter (fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return InputContainerFragment.newInstance(position + 1)
    }

    override fun getCount(): Int {
        return 99
    }
}