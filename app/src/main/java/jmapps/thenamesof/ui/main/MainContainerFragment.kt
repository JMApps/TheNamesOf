package jmapps.thenamesof.ui.main

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.DBOpenMainContent
import jmapps.thenamesof.data.database.MainContentList
import jmapps.thenamesof.databinding.FragmentMainContainerBinding
import jmapps.thenamesof.ui.main.ayahs.adapter.MainAyahsAdapter
import jmapps.thenamesof.ui.main.ayahs.model.MainAyahsModel
import jmapps.thenamesof.ui.main.model.MainContentModel
import jmapps.thenamesof.ui.main.names.adapter.MainNamesAdapter
import jmapps.thenamesof.ui.main.names.model.MainNamesModel

class MainContainerFragment : Fragment(), MainNamesAdapter.OnItemMainNameClick,
    NestedScrollView.OnScrollChangeListener {

    private var sectionNumber: Int? = 0

    private lateinit var binding: FragmentMainContainerBinding
    private lateinit var database: SQLiteDatabase

    private lateinit var mainNameList: MutableList<MainNamesModel>
    private lateinit var mainNamesAdapter: MainNamesAdapter

    private lateinit var mainAyahList: MutableList<MainAyahsModel>
    private lateinit var mainAyahsAdapter: MainAyahsAdapter

    private lateinit var mainContentList: MutableList<MainContentModel>

    companion object {

        private const val ARG_CONTENT_NUMBER = "main_content_number"

        @JvmStatic
        fun newInstance(dataNumber: Int): MainContainerFragment {
            return MainContainerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CONTENT_NUMBER, dataNumber)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sectionNumber = arguments?.getInt(ARG_CONTENT_NUMBER)

        database = DBOpenMainContent(context).readableDatabase
        mainNameList = MainContentList(database).getMainNamesList(sectionNumber)
        mainAyahList = MainContentList(database).getMainAyahsList(sectionNumber)
        mainContentList = MainContentList(database).getMainContentList
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_container, container, false)

        val verticalLayoutNames  = LinearLayoutManager(context)
        binding.rvMainNames.layoutManager = verticalLayoutNames

        val verticalLayoutAyahs  = LinearLayoutManager(context)
        binding.rvMainAyahs.layoutManager = verticalLayoutAyahs

        mainNamesAdapter = MainNamesAdapter(requireContext(), mainNameList, this)
        binding.rvMainNames.adapter = mainNamesAdapter

        mainAyahsAdapter = MainAyahsAdapter(requireContext(), mainAyahList)
        binding.rvMainAyahs.adapter = mainAyahsAdapter

        binding.tvChapterNumber.text = Html.fromHtml(mainContentList[sectionNumber!! - 1].chapterNumber)
        binding.tvChapterContent.text = Html.fromHtml(mainContentList[sectionNumber!! - 1].chapterContent)

        binding.nsMainContent.setOnScrollChangeListener(this)

        return binding.root
    }

    override fun mainNameClick(mainNameId: Int) {

    }

    override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        if (oldScrollY < scrollY) {
            binding.fabMainChapters.hide()
            binding.fabMainFavorites.hide()
        } else {
            binding.fabMainChapters.show()
            binding.fabMainFavorites.show()
        }
    }
}