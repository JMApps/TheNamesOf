package jmapps.thenamesof.ui.content.fragments

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.DBOpenMainContent
import jmapps.thenamesof.data.database.MainContentList
import jmapps.thenamesof.databinding.FragmentContentBinding
import jmapps.thenamesof.ui.content.activities.ContentActivity
import jmapps.thenamesof.ui.content.adapter.ContentRecyclerViewAdapter
import jmapps.thenamesof.ui.content.model.ModelContent

class ContentFragment : Fragment(), ContentRecyclerViewAdapter.ContentItemClick {

    private lateinit var binding: FragmentContentBinding
    private lateinit var database: SQLiteDatabase

    private lateinit var contentList: MutableList<ModelContent>
    private lateinit var contentAdapter: ContentRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content, container, false)

        database = DBOpenMainContent(context).readableDatabase
        contentList = MainContentList(database).getContentList

        val verticalLayout  = LinearLayoutManager(context)
        binding.rvMainContentTitles.layoutManager = verticalLayout

        contentAdapter = ContentRecyclerViewAdapter(requireContext(), contentList, this)
        binding.rvMainContentTitles.adapter = contentAdapter

        return binding.root
    }

    override fun contentItemClick(contentId: Int) {
        val toContentActivity = Intent(requireContext(), ContentActivity::class.java)
        toContentActivity.putExtra(ContentActivity.keyContentId, contentId)
        startActivity(toContentActivity)
    }
}