package jmapps.thenamesof.ui.content.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import jmapps.thenamesof.ui.content.fragments.ContentContainerFragment

class ContentPagerAdapter (fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return ContentContainerFragment.newInstance(position + 1)
    }

    override fun getCount(): Int {
        return 16
    }
}