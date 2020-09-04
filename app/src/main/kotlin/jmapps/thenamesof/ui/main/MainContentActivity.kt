package jmapps.thenamesof.ui.main

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.google.android.material.appbar.AppBarLayout
import jmapps.LockOrientation
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.ActivityMainContentBinding
import jmapps.thenamesof.ui.main.adapter.MainContentPagerAdapter
import jmapps.thenamesof.ui.main.bookmarks.MainContentBookmarksBottomSheet
import jmapps.thenamesof.ui.main.settings.MainContentSettingsBottomSheet

class MainContentActivity : AppCompatActivity(), View.OnClickListener,
    MainContentBookmarksBottomSheet.ToCurrentItemMainContent, AppBarLayout.OnOffsetChangedListener {

    private var mainChapterId: Int = 1

    private lateinit var binding: ActivityMainContentBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    companion object {
        const val keyMainChapterId = "key_main_chapter_id"
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_content)
        setSupportActionBar(binding.toolbar)

        LockOrientation(this).lock()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainChapterId = intent.getIntExtra(keyMainChapterId, 1) - 1

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = preferences.edit()

        val mainContentPagerAdapter = MainContentPagerAdapter(supportFragmentManager)
        binding.mainContentViewPager.adapter = mainContentPagerAdapter
        binding.mainContentViewPager.currentItem = mainChapterId

        binding.appBar.addOnOffsetChangedListener(this)
        binding.fabMainFavorites.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_content, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            android.R.id.home -> finish()

            R.id.action_settings_main_content -> {
                val mainContentSettingBottomSheet = MainContentSettingsBottomSheet()
                mainContentSettingBottomSheet.show(
                    supportFragmentManager,
                    MainContentSettingsBottomSheet.keyMainContentSettings
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabMainFavorites -> {
                val mainContentBookmarksBottomSheet = MainContentBookmarksBottomSheet()
                mainContentBookmarksBottomSheet.show(
                    supportFragmentManager,
                    MainContentBookmarksBottomSheet.keyMainContentBookmarks
                )
            }
        }
    }

    override fun toCurrentItemMainContent(currentItem: Int) {
        binding.mainContentViewPager.currentItem = currentItem - 1
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (verticalOffset != 0) {
            binding.fabMainFavorites.hide()
        } else {
            binding.fabMainFavorites.show()
        }
    }
}