package jmapps.thenamesof.ui.main

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.FragmentMainBinding
import jmapps.thenamesof.ui.main.adapter.MainContentPagerAdapter
import jmapps.thenamesof.ui.main.settings.MainContentSettingsBottomSheet

class MainFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var binding: FragmentMainBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        editor = preferences.edit()

        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .registerOnSharedPreferenceChangeListener(this)

        retainInstance = true
        setHasOptionsMenu(true)

        val mainContentPagerAdapter = MainContentPagerAdapter(childFragmentManager)
        binding.mainContentViewPager.adapter = mainContentPagerAdapter


        val lastPosition = preferences.getInt(keyLastPosition, 0)
        binding.mainContentViewPager.currentItem = lastPosition

        toCurrentPosition()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_content, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings_main_content -> {
                val mainContentSettingBottomSheet = MainContentSettingsBottomSheet()
                mainContentSettingBottomSheet.show(childFragmentManager, MainContentSettingsBottomSheet.keyMainContentSettings)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSharedPreferenceChanged(preferences: SharedPreferences?, key: String?) {
        toCurrentPosition()
    }

    override fun onDestroy() {
        super.onDestroy()
        editor.putInt(keyLastPosition, binding.mainContentViewPager.currentItem).apply()
    }

    private fun toCurrentPosition() {
        val currentPosition = preferences.getInt(keyCurrentPosition, binding.mainContentViewPager.currentItem)
        binding.mainContentViewPager.currentItem = currentPosition
    }

    companion object {
        const val  keyCurrentPosition = "key_to_chapter_position"
        const val  keyLastPosition = "key_last_pager_position"
    }
}