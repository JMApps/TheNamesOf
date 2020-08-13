package jmapps.thenamesof.ui.allnames

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.AllNamesContent
import jmapps.thenamesof.data.database.DBOpenAllNames
import jmapps.thenamesof.databinding.FragmentAllNamesBinding
import jmapps.thenamesof.ui.allnames.adapter.AllNamesAdapter
import jmapps.thenamesof.ui.allnames.model.AllNamesModel

class AllNamesFragment : Fragment(), AllNamesAdapter.AllNameItemClick,
    AllNamesAdapter.ShareAllNamesItemClick {

    private lateinit var binding: FragmentAllNamesBinding
    private var database: SQLiteDatabase? = null

    private lateinit var allNamesList: MutableList<AllNamesModel>
    private lateinit var allNamesAdapter: AllNamesAdapter

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = DBOpenAllNames(requireContext()).readableDatabase
        allNamesList = AllNamesContent(database!!).getAllNamesList
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_names, container, false)

        retainInstance = true
        setHasOptionsMenu(true)

        val verticalLayout = LinearLayoutManager(requireContext())
        binding.rvAllNames.layoutManager = verticalLayout

        allNamesAdapter = AllNamesAdapter(requireContext(), allNamesList, this, this)
        binding.rvAllNames.adapter = allNamesAdapter

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_all_names, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings_main_all_names -> {
                val settingsAllNamesBottomSheet = SettingsAllNamesBottomSheet()
                settingsAllNamesBottomSheet.show(childFragmentManager, SettingsAllNamesBottomSheet.keyAllNamesSettings)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun allNameItemClick(position: Int) {
        playName(position)
    }

    override fun shareAllNamesItemClick(position: Int) {
        shareNames(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        database?.close()
    }

    private fun playName(position: Int) {
        clearPlayer()
        val resId = context?.resources?.getIdentifier(
            allNamesList[position].allNameAudio, "raw", "jmapps.thenamesof")
        mediaPlayer = MediaPlayer.create(context, resId!!)
        mediaPlayer?.start()
        allNamesAdapter.onItemSelected(position)
    }

    private fun clearPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun shareNames(position: Int) {
        val current = allNamesList[position]
        val names = "${current.allNameArabic}\n${current.allNameTranscription}\n${current.allNameTranslation}"
        val shareNames = Intent(Intent.ACTION_SEND)
        shareNames.type = "text/plain"
        shareNames.putExtra(Intent.EXTRA_TEXT, names)
        context?.startActivity(shareNames)
    }
}