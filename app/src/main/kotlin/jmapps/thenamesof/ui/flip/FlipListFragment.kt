package jmapps.thenamesof.ui.flip

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
import jmapps.thenamesof.databinding.FragmentFlipBinding
import jmapps.thenamesof.ui.flip.adapter.FlipListAdapter
import jmapps.thenamesof.ui.flip.model.FlipListModel

class FlipListFragment : Fragment(), FlipListAdapter.FlipCardItemClick {

    private lateinit var binding: FragmentFlipBinding
    private lateinit var database: SQLiteDatabase

    private lateinit var flipNameList: MutableList<FlipListModel>
    private lateinit var flipNameAdapter: FlipListAdapter

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = DBOpenAllNames(requireContext()).readableDatabase
        flipNameList = AllNamesContent(database).getFlipNamesList
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_flip, container, false)

        retainInstance = true
        setHasOptionsMenu(true)

        val verticalLayout = LinearLayoutManager(requireContext())
        binding.rvFlipListNames.layoutManager = verticalLayout

        initFlipCardList(binding.tbChangeCardMode.isChecked)
        binding.tbChangeCardMode.setOnCheckedChangeListener { _, isChecked ->
            initFlipCardList(isChecked)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_flip_names, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings_main_flip_names -> {
                val settingsFlipListBottomSheet = SettingsFlipListBottomSheet()
                settingsFlipListBottomSheet.show(childFragmentManager, SettingsFlipListBottomSheet.keyFlipNamesSettings)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun flipItemClick(position: Int) {
        if (binding.tbFlipAudioState.isChecked) {
            playName(position)
        }
    }

    private fun initFlipCardList(cardState: Boolean) {
        flipNameList.shuffle()
        flipNameAdapter = FlipListAdapter(requireContext(), cardState, flipNameList, this)
        binding.rvFlipListNames.adapter = flipNameAdapter
    }

    private fun playName(position: Int) {
        clearPlayer()
        val resId = context?.resources?.getIdentifier(
            flipNameList[position].flipNameAudio, "raw", "jmapps.thenamesof")
        mediaPlayer = MediaPlayer.create(context, resId!!)
        mediaPlayer?.start()
    }

    private fun clearPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}