package jmapps.thenamesof.ui.quiz

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
import jmapps.thenamesof.databinding.FragmentMainBinding
import jmapps.thenamesof.databinding.FragmentQuizBinding
import jmapps.thenamesof.ui.main.MainViewModel

class QuizFragment : Fragment() {

    private lateinit var quizViewModel: QuizViewModel
    private lateinit var binding: FragmentQuizBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)

        quizViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.mainTextView.text = it
        })

        return binding.root
    }
}