package jmapps.thenamesof.ui.input.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import jmapps.thenamesof.ui.input.fragments.InputContainerFragment

class InputContentPagerAdapter(activity: AppCompatActivity, private val inputMode: Boolean) :
    FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return InputContainerFragment.newInstance(position + 1, inputMode)
    }

    override fun getItemCount(): Int {
        return 99
    }
}