package jmapps.thenamesof.ui.input.activities

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.google.android.material.tabs.TabLayoutMediator
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = preferences.edit()

        inputMode = intent.getBooleanExtra(InputFragment.keyInputCategoryState, true)

        val inputContentPagerAdapter = InputContentPagerAdapter(this, inputMode!!)
        binding.inputContentViewPager.adapter = inputContentPagerAdapter
        binding.inputContentViewPager.isUserInputEnabled = false

        TabLayoutMediator(binding.inputContentTabLayout, binding.inputContentViewPager) { tab, position ->
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

            }

            R.id.action_share_input_content -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun nextPagerPosition(position: Int) {
        binding.inputContentViewPager.currentItem = position
    }

    private fun loadLastPagerPosition(inputMode: Boolean) {
        if (inputMode) {
            val lastPositionArabic = preferences.getInt(InputContainerFragment.keyLastInputDataArabic, 0)
            binding.inputContentViewPager.setCurrentItem(lastPositionArabic, false)
        } else {
            val lastPositionTranslation = preferences.getInt(InputContainerFragment.keyLastInputDataTranslation, 0)
            binding.inputContentViewPager.setCurrentItem(lastPositionTranslation, false)
        }
    }
}