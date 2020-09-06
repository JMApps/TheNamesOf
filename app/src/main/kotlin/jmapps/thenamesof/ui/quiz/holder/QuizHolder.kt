package jmapps.thenamesof.ui.quiz.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.quiz.adapter.QuizAdapter

class QuizHolder(quizView: View) : RecyclerView.ViewHolder(quizView) {

    val tvQuizAnswerTranslation: TextView = quizView.findViewById(R.id.tvQuizAnswerTranslation)
    val tvQuizAnswerArabic: TextView = quizView.findViewById(R.id.tvQuizAnswerArabic)

    fun findQuizItemClick(quizItemClick: QuizAdapter.QuizItemClick, position: Int) {
        itemView.setOnClickListener {
            quizItemClick.quizItemClick(position)
        }
    }
}