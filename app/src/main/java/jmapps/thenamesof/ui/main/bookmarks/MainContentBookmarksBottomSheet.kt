package jmapps.thenamesof.ui.main.bookmarks

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.DBOpenMainContent
import jmapps.thenamesof.data.database.MainContentList
import jmapps.thenamesof.databinding.BottomSheetMainContentBookmarksBinding
import jmapps.thenamesof.ui.main.bookmarks.adapter.MainBookmarksAdapter
import jmapps.thenamesof.ui.main.bookmarks.model.MainBookmarksModel

class MainContentBookmarksBottomSheet : BottomSheetDialogFragment(),
    MainBookmarksAdapter.OnItemMainBookmarkClick {

    override fun getTheme(): Int = R.style.CustomBottomSheetDialog

    private lateinit var binding: BottomSheetMainContentBookmarksBinding

    private lateinit var database: SQLiteDatabase

    private lateinit var mainBookmarkList: MutableList<MainBookmarksModel>
    private lateinit var mainBookmarksAdapter: MainBookmarksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = DBOpenMainContent(context).readableDatabase
        mainBookmarkList = MainContentList(database).getMainBookmarkList
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_main_content_bookmarks, container, false)

        val verticalLayoutNames  = LinearLayoutManager(context)
        binding.rvMainBookmarks.layoutManager = verticalLayoutNames

        mainBookmarksAdapter = MainBookmarksAdapter(requireContext(), mainBookmarkList, this)
        binding.rvMainBookmarks.adapter = mainBookmarksAdapter

        if (mainBookmarkList.size != 0) {
            binding.llBookmarkListIsEmpty.visibility = View.GONE
        } else {
            binding.llBookmarkListIsEmpty.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun itemBookmarkClick(mainChapterId: Int) {
        Toast.makeText(requireContext(), "To bookmark = $mainChapterId", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val keyMainContentBookmarks = "key_main_content_bookmarks"
    }

}