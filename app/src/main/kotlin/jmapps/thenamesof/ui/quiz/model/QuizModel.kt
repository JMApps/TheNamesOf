package jmapps.thenamesof.ui.quiz.model

data class QuizModel(
    val quizId: Int,
    val quizAnswerArabic: String,
    val quizAnswerTranslation: String,
    val answerState: String)