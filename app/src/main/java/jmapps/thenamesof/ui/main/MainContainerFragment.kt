package jmapps.thenamesof.ui.main

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
import jmapps.thenamesof.databinding.FragmentMainContainerBinding
import jmapps.thenamesof.ui.main.names.adapter.MainNamesAdapter
import jmapps.thenamesof.ui.main.names.model.MainNamesModel

class MainContainerFragment : Fragment(), MainNamesAdapter.OnItemMainNameClick {

    private var sectionNumber: Int? = 0

    private lateinit var binding: FragmentMainContainerBinding
    private lateinit var database: SQLiteDatabase

    private lateinit var mainNameList: MutableList<MainNamesModel>
    private lateinit var mainNamesAdapter: MainNamesAdapter

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
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_container, container, false)

        val verticalLayout  = LinearLayoutManager(context)
        binding.rvMainNames.layoutManager = verticalLayout

        mainNamesAdapter = MainNamesAdapter(requireContext(), mainNameList, this)
        binding.rvMainNames.adapter = mainNamesAdapter

        return binding.root
    }

    override fun mainNameClick(mainNameId: Int) {

    }
}