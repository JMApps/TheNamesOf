package jmapps.thenamesof.ui.main.chapters

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.DBOpenMainContent
import jmapps.thenamesof.data.database.MainContentList
import jmapps.thenamesof.databinding.BottomSheetMainContentChaptersBinding
import jmapps.thenamesof.ui.main.chapters.adapter.MainChaptersAdapter
import jmapps.thenamesof.ui.main.chapters.model.MainChaptersModel

class MainContentChaptersBottomSheet : BottomSheetDialogFragment(),
    MainChaptersAdapter.OnItemMainChapterClick, TextWatcher {

    override fun getTheme(): Int = R.style.CustomBottomSheetDialog

    private lateinit var binding: BottomSheetMainContentChaptersBinding

    private lateinit var database: SQLiteDatabase

    private lateinit var mainChapterList: MutableList<MainChaptersModel>
    private lateinit var mainChaptersAdapter: MainChaptersAdapter

    private lateinit var toCurrentItemMainContent: ToCurrentItemMainContent

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

        binding.etSearchChapter.addTextChangedListener(this)

        return binding.root
    }

    override fun itemChapterClick(mainChapterId: Int) {
        toCurrentItemMainContent.toCurrentItemMainContent(mainChapterId)
        dialog?.dismiss()
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        mainChaptersAdapter.filter.filter(s.toString())
    }

    interface ToCurrentItemMainContent {
        fun toCurrentItemMainContent(currentItem: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ToCurrentItemMainContent) {
            toCurrentItemMainContent = context
        } else throw RuntimeException("$context must implement this interface")
    }

    companion object {
        const val keyMainContentChapters = "key_main_content_chapters"
    }
}