package jmapps.thenamesof.ui.quiz.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.google.android.material.tabs.TabLayoutMediator
import jmapps.LockOrientation
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.ActivityQuizBinding
import jmapps.thenamesof.ui.quiz.adapter.QuizContentPagerAdapter
import jmapps.thenamesof.ui.quiz.fragments.QuizContainerFragment
import jmapps.thenamesof.ui.quiz.fragments.QuizFragment

class QuizActivity : AppCompatActivity(), QuizContainerFragment.ToNextPagerPosition {

    private lateinit var binding: ActivityQuizBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var quizMode: Boolean? = null

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz)
        setSupportActionBar(binding.toolbar)

        LockOrientation(this).lock()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = preferences.edit()

        quizMode = intent.getBooleanExtra(QuizFragment.keyQuizCategoryState, true)

        val quizContentPagerAdapter = QuizContentPagerAdapter(this, quizMode!!)
        binding.quizContentViewPager.adapter = quizContentPagerAdapter
        //binding.quizContentViewPager.isUserInputEnabled = false

        TabLayoutMediator(
            binding.quizContentTabLayout,
            binding.quizContentViewPager) { tab, position ->
            tab.view.isClickable = false
            tab.text = (position + 1).toString()
        }.attach()

        loadLastPagerPosition(quizMode!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.quiz, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()

            R.id.action_refresh_quiz_content -> {
                refreshQuiz()
            }

            R.id.action_share_quiz_content -> {
                shareQuizStats(quizMode!!)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun nextPagerPosition(position: Int) {
        binding.quizContentViewPager.currentItem = position
    }

    private fun loadLastPagerPosition(quizMode: Boolean) {
        if (quizMode) {
            val lastPositionArabic = preferences.getInt(QuizContainerFragment.keyLastQuizDataArabic, 0)
            binding.quizContentViewPager.setCurrentItem(lastPositionArabic, false)
        } else {
            val lastPositionTranslation = preferences.getInt(QuizContainerFragment.keyLastQuizDataTranslation, 0)
            binding.quizContentViewPager.setCurrentItem(lastPositionTranslation, false)
        }
    }

    private fun refreshQuiz() {
        editor.putInt(QuizContainerFragment.keyTrueQuizDataArabic, 0).apply()
        editor.putInt(QuizContainerFragment.keyTrueQuizDataTranslation, 0).apply()
        editor.putInt(QuizContainerFragment.keyLastQuizDataArabic, 0).apply()
        editor.putInt(QuizContainerFragment.keyLastQuizDataTranslation, 0).apply()
        binding.quizContentViewPager.currentItem = 0
        Toast.makeText(this, getString(R.string.action_refresh), Toast.LENGTH_SHORT).show()
    }

    private fun shareQuizStats(quizMode: Boolean) {
        val linkApp = "https://play.google.com/store/apps/details?id=jmapps.thenamesof"

        val trueValueArabic = preferences.getInt(QuizContainerFragment.keyTrueQuizDataArabic, 0)
        val trueValueTranslation =
            preferences.getInt(QuizContainerFragment.keyTrueQuizDataTranslation, 0)

        if (quizMode) {
            shareMode("Из 99 прекрасных имён Аллаха, на русском языке мне удалось ответить правильно на – " +
                        "$trueValueArabic\n\nПроверь, сколько сможешь ты?\n$linkApp"
            )
        } else {
            shareMode("Из 99 прекрасных имён Аллаха, на арабском языке мне удалось ответить правильно на – " +
                        "$trueValueTranslation\n\nПроверь, сколько сможешь ты?\n$linkApp"
            )
        }
    }

    private fun shareMode(shareText: String) {
        val shareContent = Intent(Intent.ACTION_SEND)
        shareContent.type = "text/plain"
        shareContent.putExtra(Intent.EXTRA_TEXT, shareText).toString()
        startActivity(shareContent)
    }
}