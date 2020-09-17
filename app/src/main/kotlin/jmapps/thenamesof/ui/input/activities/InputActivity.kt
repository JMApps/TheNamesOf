package jmapps.thenamesof.ui.input.activities

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
import jmapps.thenamesof.databinding.ActivityInputBinding
import jmapps.thenamesof.ui.input.adapter.InputContentPagerAdapter
import jmapps.thenamesof.ui.input.fragments.InputContainerFragment
import jmapps.thenamesof.ui.input.fragments.InputFragment

class InputActivity : AppCompatActivity(), InputContainerFragment.ToNextPagerPosition {

    private lateinit var binding: ActivityInputBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var inputMode: Boolean? = null

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_input)
        setSupportActionBar(binding.toolbar)

        LockOrientation(this).lock()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = preferences.edit()

        inputMode = intent.getBooleanExtra(InputFragment.keyInputCategoryState, true)

        val inputContentPagerAdapter = InputContentPagerAdapter(this, inputMode!!)
        binding.inputContentViewPager.adapter = inputContentPagerAdapter
        binding.inputContentViewPager.isUserInputEnabled = false

        TabLayoutMediator(
            binding.inputContentTabLayout,
            binding.inputContentViewPager) { tab, position ->
            tab.view.isClickable = false
            tab.text = (position + 1).toString()
        }.attach()

        loadLastPagerPosition(inputMode!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.input, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()

            R.id.action_refresh_input_content -> {
                refreshInput()
            }

            R.id.action_share_input_content -> {
                shareInputStats(inputMode!!)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun nextPagerPosition(position: Int) {
        binding.inputContentViewPager.currentItem = position
    }

    private fun loadLastPagerPosition(inputMode: Boolean) {
        if (inputMode) {
            val lastPositionArabic =
                preferences.getInt(InputContainerFragment.keyLastInputDataArabic, 0)
            binding.inputContentViewPager.setCurrentItem(lastPositionArabic, false)
        } else {
            val lastPositionTranslation =
                preferences.getInt(InputContainerFragment.keyLastInputDataTranslation, 0)
            binding.inputContentViewPager.setCurrentItem(lastPositionTranslation, false)
        }
    }

    private fun refreshInput() {
        editor.putInt(InputContainerFragment.keyTrueInputDataArabic, 0).apply()
        editor.putInt(InputContainerFragment.keyTrueInputDataTranslation, 0).apply()
        editor.putInt(InputContainerFragment.keyLastInputDataArabic, 0).apply()
        editor.putInt(InputContainerFragment.keyLastInputDataTranslation, 0).apply()
        binding.inputContentViewPager.currentItem = 0
        Toast.makeText(this, getString(R.string.action_refresh), Toast.LENGTH_SHORT).show()
    }

    private fun shareInputStats(inputMode: Boolean) {
        val linkApp = "https://play.google.com/store/apps/details?id=jmapps.thenamesof"

        val trueValueArabic = preferences.getInt(InputContainerFragment.keyTrueInputDataArabic, 0)
        val trueValueTranslation = preferences.getInt(InputContainerFragment.keyTrueInputDataTranslation, 0)

        if (inputMode) {
            shareMode("Из 99 прекрасных имён Аллаха, на русском языке мне удалось ввести правильно – " +
                        "$trueValueArabic\n\nПроверь, сколько сможешь ты?\n$linkApp"
            )
        } else {
            shareMode("Из 99 прекрасных имён Аллаха, на арабском языке мне удалось ввести правильно – " +
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