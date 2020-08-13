package jmapps.thenamesof.ui.allnames

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.AllNamesContent
import jmapps.thenamesof.data.database.DBOpenAllNames
import jmapps.thenamesof.databinding.FragmentAllNamesBinding
import jmapps.thenamesof.ui.allnames.adapter.AllNamesAdapter
import jmapps.thenamesof.ui.allnames.model.AllNamesModel

class AllNamesFragment : Fragment(), AllNamesAdapter.AllNameItemClick {

    private lateinit var binding: FragmentAllNamesBinding
    private lateinit var database: SQLiteDatabase

    private lateinit var allNamesList: MutableList<AllNamesModel>
    private lateinit var allNamesAdapter: AllNamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = DBOpenAllNames(requireContext()).readableDatabase
        allNamesList = AllNamesContent(database).getAllNamesList
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_names, container, false)

        val verticalLayout = LinearLayoutManager(requireContext())
        binding.rvAllNames.layoutManager = verticalLayout

        allNamesAdapter = AllNamesAdapter(requireContext(), allNamesList, this)
        binding.rvAllNames.adapter = allNamesAdapter

        return binding.root
    }

    override fun allNameItemClick(position: Int) {

    }
}