package jmapps.thenamesof.ui.quiz.fragments

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.DBOpenAllNames
import jmapps.thenamesof.databinding.FragmentQuizContainerBinding

class QuizContainerFragment : Fragment() {

    private lateinit var binding: FragmentQuizContainerBinding
    private var database: SQLiteDatabase? = null

    private lateinit var toNextPagerPosition: ToNextPagerPosition

    companion object {

        private const val ARG_CONTENT_NUMBER = "key_quiz_number"
        private const val ARG_LANGUAGE_MODE = "key_quiz_language_mode"

        const val keyLastQuizDataArabic = "key_last_quiz_data_arabic"
        const val keyLastQuizDataTranslation = "key_last_quiz_data_translation"

        const val keyTrueQuizDataArabic = "key_true_quiz_data_arabic"
        const val keyTrueQuizDataTranslation = "key_true_quiz_data_translation"

        @JvmStatic
        fun newInstance(dataNumber: Int, quizMode: Boolean): QuizContainerFragment {
            return QuizContainerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CONTENT_NUMBER, dataNumber)
                    putBoolean(ARG_LANGUAGE_MODE, quizMode)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = DBOpenAllNames(requireContext()).readableDatabase
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_container, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ToNextPagerPosition) {
            toNextPagerPosition = context
        } else throw RuntimeException("$context must implement this interface")
    }

    interface ToNextPagerPosition {
        fun nextPagerPosition(position: Int)
    }
}