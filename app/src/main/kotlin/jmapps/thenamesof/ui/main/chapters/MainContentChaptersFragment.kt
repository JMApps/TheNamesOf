package jmapps.thenamesof.ui.main.chapters

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.DBOpenMainContent
import jmapps.thenamesof.data.database.MainContentList
import jmapps.thenamesof.databinding.FragmentMainContentChaptersBinding
import jmapps.thenamesof.ui.main.MainContentActivity
import jmapps.thenamesof.ui.main.chapters.adapter.MainChaptersAdapter
import jmapps.thenamesof.ui.main.chapters.model.MainChaptersModel

class MainContentChaptersFragment : Fragment(),
    MainChaptersAdapter.OnItemMainChapterClick, TextWatcher {

    private lateinit var binding: FragmentMainContentChaptersBinding
    private lateinit var database: SQLiteDatabase

    private lateinit var mainChapterList: MutableList<MainChaptersModel>
    private lateinit var mainChaptersAdapter: MainChaptersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = DBOpenMainContent(context).readableDatabase
        mainChapterList = MainContentList(database).getMainChapterList
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_content_chapters, container, false)

        val verticalLayoutNames  = LinearLayoutManager(context)
        binding.rvMainChapters.layoutManager = verticalLayoutNames

        mainChaptersAdapter = MainChaptersAdapter(requireContext(), mainChapterList, this)
        binding.rvMainChapters.adapter = mainChaptersAdapter

        binding.etSearchChapter.addTextChangedListener(this)

        return binding.root
    }

    override fun itemChapterClick(mainChapterId: Int) {
        val toMainContentActivity = Intent(requireContext(), MainContentActivity::class.java)
        toMainContentActivity.putExtra(MainContentActivity.keyMainChapterId, mainChapterId)
        startActivity(toMainContentActivity)
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        mainChaptersAdapter.filter.filter(s.toString())
    }
}