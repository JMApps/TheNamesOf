package jmapps.thenamesof.ui.content.activities

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.ContentList
import jmapps.thenamesof.data.database.DBOpenContent
import jmapps.thenamesof.databinding.ActivityContentBinding
import jmapps.thenamesof.ui.content.adapter.ContentPagerAdapter
import jmapps.thenamesof.ui.content.fragments.SettingsContentBottomSheet
import jmapps.thenamesof.ui.content.model.ModelContent

class ContentActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private lateinit var binding: ActivityContentBinding
    private var database: SQLiteDatabase? = null
    private lateinit var contentList: MutableList<ModelContent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_content)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val getContentId = intent.getIntExtra(keyContentId, 1)

        database = DBOpenContent(this).readableDatabase
        contentList = ContentList(database!!).getContentList

        val contentPagerAdapter = ContentPagerAdapter(supportFragmentManager)
        binding.contentViewPager.adapter = contentPagerAdapter

        binding.diContentContainer.attachViewPager(binding.contentViewPager)
        binding.diContentContainer.setDotTintRes(R.color.white)

        binding.tvContentTitle.text = contentList[getContentId - 1].contentTitle
        binding.contentViewPager.currentItem = getContentId - 1

        binding.contentViewPager.addOnPageChangeListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.content, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()

            R.id.action_settings_content -> {
                val contentSettings = SettingsContentBottomSheet()
                contentSettings.show(supportFragmentManager, SettingsContentBottomSheet.keyContentSettings)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        binding.tvContentTitle.text = contentList[position].contentTitle
    }

    override fun onDestroy() {
        super.onDestroy()
        database?.close()
    }

    companion object {
        const val keyContentId = "key_content_id"
    }
}