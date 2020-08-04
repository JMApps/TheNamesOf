package jmapps.thenamesof.ui.main.chapters

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
import jmapps.thenamesof.databinding.BottomSheetMainContentChaptersBinding
import jmapps.thenamesof.ui.main.ayahs.adapter.MainAyahsAdapter
import jmapps.thenamesof.ui.main.chapters.adapter.MainChaptersAdapter
import jmapps.thenamesof.ui.main.chapters.model.MainChaptersModel

class MainContentChaptersBottomSheet : BottomSheetDialogFragment(),
    MainChaptersAdapter.OnItemMainChapterClick {

    override fun getTheme(): Int = R.style.CustomBottomSheetDialog

    private lateinit var binding: BottomSheetMainContentChaptersBinding

    private lateinit var database: SQLiteDatabase

    private lateinit var mainChapterList: MutableList<MainChaptersModel>
    private lateinit var mainChaptersAdapter: MainChaptersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = DBOpenMainContent(context).readableDatabase
        mainChapterList = MainContentList(database).getMainChapterList
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_main_content_chapters, container, false)

        val verticalLayoutNames  = LinearLayoutManager(context)
        binding.rvMainChapters.layoutManager = verticalLayoutNames

        mainChaptersAdapter = MainChaptersAdapter(requireContext(), mainChapterList, this)
        binding.rvMainChapters.adapter = mainChaptersAdapter

        return binding.root
    }

    companion object {
        const val keyMainContentChapters = "key_main_content_chapters"
    }

    override fun itemChapterClick(mainChapterId: Int) {
        Toast.makeText(requireContext(), "To chapter = $mainChapterId", Toast.LENGTH_SHORT).show()
    }
}