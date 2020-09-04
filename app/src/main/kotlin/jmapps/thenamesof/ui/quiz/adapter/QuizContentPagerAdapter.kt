package jmapps.thenamesof.ui.quiz.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import jmapps.thenamesof.ui.quiz.fragments.QuizContainerFragment

class QuizContentPagerAdapter(activity: AppCompatActivity, private val quizMode: Boolean) :
    FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return QuizContainerFragment.newInstance(position + 1, quizMode)
    }

    override fun getItemCount(): Int {
        return 99
    }
}