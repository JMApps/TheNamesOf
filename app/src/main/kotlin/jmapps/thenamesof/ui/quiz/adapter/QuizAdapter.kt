package jmapps.thenamesof.ui.quiz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.quiz.holder.QuizHolder
import jmapps.thenamesof.ui.quiz.model.QuizModel

class QuizAdapter(
    context: Context,
    private val quizList: MutableList<QuizModel>,
    private val quizItemClick: QuizItemClick) : RecyclerView.Adapter<QuizHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    interface QuizItemClick {
        fun quizItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizHolder {
        val quizView = layoutInflater.inflate(R.layout.item_quiz, parent, false)
        return QuizHolder(quizView)
    }

    override fun onBindViewHolder(holder: QuizHolder, position: Int) {
        val current = quizList[position]

        holder.tvQuizAnswerArabic.text = current.quizAnswerArabic
        holder.tvQuizAnswerTranslation.text = current.quizAnswerTranslation

        holder.findQuizItemClick(quizItemClick, position)
    }

    override fun getItemCount(): Int {
        return quizList.size
    }
}