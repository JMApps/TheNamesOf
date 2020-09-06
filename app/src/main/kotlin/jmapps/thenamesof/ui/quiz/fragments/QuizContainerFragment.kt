package jmapps.thenamesof.ui.quiz.fragments

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.AllNamesContent
import jmapps.thenamesof.data.database.DBOpenAllNames
import jmapps.thenamesof.data.database.DBOpenQuiz
import jmapps.thenamesof.data.database.QuizList
import jmapps.thenamesof.databinding.FragmentQuizContainerBinding
import jmapps.thenamesof.ui.allnames.model.AllNamesModel
import jmapps.thenamesof.ui.quiz.adapter.QuizAdapter
import jmapps.thenamesof.ui.quiz.model.QuizModel

class QuizContainerFragment : Fragment(), QuizAdapter.QuizItemClick {

    private lateinit var binding: FragmentQuizContainerBinding
    private var database: SQLiteDatabase? = null
    private var sectionNumber: Int? = null
    private var quizMode: Boolean? = null

    private lateinit var allNamesList: MutableList<AllNamesModel>
    private lateinit var quizList: MutableList<QuizModel>
    private lateinit var quizAdapter: QuizAdapter

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

        sectionNumber = arguments?.getInt(ARG_CONTENT_NUMBER)
        quizMode = arguments?.getBoolean(ARG_LANGUAGE_MODE)

        database = DBOpenAllNames(requireContext()).readableDatabase
        database = DBOpenQuiz(requireContext()).readableDatabase

        allNamesList = AllNamesContent(database!!).getAllNamesList
        quizList = QuizList(database!!).getQuizList(sectionNumber!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_container, container, false)

        val verticalLayout = LinearLayoutManager(requireContext())
        binding.rvQuizVariables.layoutManager = verticalLayout

        quizAdapter = QuizAdapter(requireContext(), quizList, this)
        binding.rvQuizVariables.adapter = quizAdapter

        allNamesList.shuffle()
        if (quizMode!!) {
            binding.tvMainQuizName.text = allNamesList[sectionNumber!! - 1].allNameArabic
        } else {
            binding.tvMainQuizName.text = allNamesList[sectionNumber!! - 1].allNameTranslation
        }

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

    override fun quizItemClick(position: Int) {

    }
}