package jmapps.thenamesof.ui.content

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.DBOpenMainContent
import jmapps.thenamesof.data.database.MainContentList
import jmapps.thenamesof.databinding.FragmentContentContainerBinding
import jmapps.thenamesof.ui.content.model.ModelContent

class ContentContainerFragment : Fragment() {

    private lateinit var binding: FragmentContentContainerBinding
    private lateinit var database: SQLiteDatabase
    private lateinit var contentList: MutableList<ModelContent>

    companion object {

        private const val ARG_CONTENT_NUMBER = "content_number"

        @JvmStatic
        fun newInstance(dataNumber: Int): ContentContainerFragment {
            return ContentContainerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CONTENT_NUMBER, dataNumber)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_container, container, false)
        val sectionNumber = arguments?.getInt(ARG_CONTENT_NUMBER)

        database = DBOpenMainContent(requireContext()).readableDatabase
        contentList = MainContentList(database).getContentList

        binding.tvContentText.text = Html.fromHtml(contentList[sectionNumber!! - 1].content)

        return binding.root
    }
}