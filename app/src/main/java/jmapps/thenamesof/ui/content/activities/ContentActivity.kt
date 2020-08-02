package jmapps.thenamesof.ui.content.activities

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.DBOpenMainContent
import jmapps.thenamesof.data.database.MainContentList
import jmapps.thenamesof.databinding.ActivityContentBinding
import jmapps.thenamesof.ui.content.adapter.ContentPagerAdapter
import jmapps.thenamesof.ui.content.model.ModelContent
import jmapps.thenamesof.ui.main.adapter.MainContentPagerAdapter

class ContentActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private lateinit var binding: ActivityContentBinding
    private lateinit var database: SQLiteDatabase
    private lateinit var contentList: MutableList<ModelContent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_content)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val getContentId = intent.getIntExtra(keyContentId, 0)

        database = DBOpenMainContent(this).readableDatabase
        contentList = MainContentList(database).getContentList

        val contentPagerAdapter = ContentPagerAdapter(supportFragmentManager)
        binding.contentViewPager.adapter = contentPagerAdapter

        binding.diContentContainer.attachViewPager(binding.contentViewPager)
        binding.diContentContainer.setDotTintRes(R.color.white)

        binding.tvContentTitle.text = contentList[getContentId - 1].contentTitle
        binding.contentViewPager.currentItem = getContentId - 1

        binding.contentViewPager.addOnPageChangeListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val keyContentId = "key_content_id"
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        binding.tvContentTitle.text = contentList[position].contentTitle
    }
}