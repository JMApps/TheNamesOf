package jmapps.thenamesof.ui.content.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import jmapps.mvp.content.ScrollReadPresenterImpl
import jmapps.thenamesof.R
import jmapps.thenamesof.data.database.DBOpenMainContent
import jmapps.thenamesof.data.database.MainContentList
import jmapps.thenamesof.databinding.FragmentContentContainerBinding
import jmapps.thenamesof.ui.content.model.ModelContent

class ContentContainerFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var binding: FragmentContentContainerBinding
    private lateinit var scrollReadPresenterImpl: ScrollReadPresenterImpl
    private lateinit var database: SQLiteDatabase
    private lateinit var contentList: MutableList<ModelContent>

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var textSizeValues = (16..34).toList().filter { it % 2 == 0 }

    companion object {

        private const val ARG_CONTENT_NUMBER = "content_number"

        @JvmStatic
        fun newInstance(dataNumber: Int): ContentContainerFragment {
            return ContentContainerFragment()
                .apply {
                arguments = Bundle().apply {
                    putInt(ARG_CONTENT_NUMBER, dataNumber)
                }
            }
        }
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_container, container, false)
        val sectionNumber = arguments?.getInt(ARG_CONTENT_NUMBER)

        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        editor = preferences.edit()
        PreferenceManager.getDefaultSharedPreferences(requireContext()).registerOnSharedPreferenceChangeListener(this)

        database = DBOpenMainContent(requireContext()).readableDatabase
        contentList = MainContentList(database).getContentList

        scrollReadPresenterImpl = ScrollReadPresenterImpl(sectionNumber!!, binding.nvContentContainer, binding.pbReadProgress)
        scrollReadPresenterImpl.scrollCount()
        scrollReadPresenterImpl.loadLastCount(preferences)

        getContentTextSize()
        binding.tvContentText.text = Html.fromHtml(contentList[sectionNumber - 1].content)

        return binding.root
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        getContentTextSize()
    }

    private fun getContentTextSize() {
        val lastContentTextSize = preferences.getInt(SettingsContentBottomSheet.keyContentTextSize, 0)
        binding.tvContentText.textSize = textSizeValues[lastContentTextSize].toFloat()
    }

    override fun onDestroy() {
        super.onDestroy()
        scrollReadPresenterImpl.saveLastCount(editor)
    }
}