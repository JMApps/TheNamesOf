package jmapps.thenamesof.ui.quiz.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.FragmentQuizBinding
import jmapps.thenamesof.ui.quiz.activities.QuizActivity

class QuizFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentQuizBinding

    companion object {
        const val keyQuizCategoryState = "key_quiz_category_state"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)

        binding.btnQuizArabicCategory.setOnClickListener(this)
        binding.btnQuizRussianCategory.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnQuizArabicCategory -> toQuizActivity(true)
            R.id.btnQuizRussianCategory -> toQuizActivity(false)
        }
    }

    private fun toQuizActivity(categoryState: Boolean) {
        val toQuizCategoryActivity = Intent(requireContext(), QuizActivity::class.java)
        toQuizCategoryActivity.putExtra(keyQuizCategoryState, categoryState)
        startActivity(toQuizCategoryActivity)
    }
}