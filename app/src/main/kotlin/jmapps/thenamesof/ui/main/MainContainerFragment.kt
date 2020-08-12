package jmapps.thenamesof.ui.main

import android.annotation.SuppressLint
import android.content.*
import android.content.ClipData.newPlainText
import android.content.Context.CLIPBOARD_SERVICE
import android.database.sqlite.SQLiteDatabase
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
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
import jmapps.thenamesof.ui.main.settings.MainContentSettingsBottomSheet

class MainContainerFragment : Fragment(), MainNamesAdapter.OnItemMainNameClick, View.OnClickListener,
    CompoundButton.OnCheckedChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private var sectionNumber: Int? = 0
    private var mediaPlayer: MediaPlayer? = null

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var binding: FragmentMainContainerBinding
    private lateinit var database: SQLiteDatabase

    private lateinit var mainNameList: MutableList<MainNamesModel>
    private lateinit var mainNamesAdapter: MainNamesAdapter

    private lateinit var mainAyahList: MutableList<MainAyahsModel>
    private lateinit var mainAyahsAdapter: MainAyahsAdapter

    private lateinit var mainContentList: MutableList<MainContentModel>

    private var myClipboard: ClipboardManager? = null
    private var myClip: ClipData? = null

    private var contentSizeValues = (16..34).toList().filter { it % 2 == 0 }

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

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sectionNumber = arguments?.getInt(ARG_CONTENT_NUMBER)

        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        editor = preferences.edit()

        database = DBOpenMainContent(context).readableDatabase
        mainNameList = MainContentList(database).getMainNamesList(sectionNumber)
        mainAyahList = MainContentList(database).getMainAyahsList(sectionNumber)
        mainContentList = MainContentList(database).getMainContentList
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_container, container, false)

        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .registerOnSharedPreferenceChangeListener(this)

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

        val bookmarkStates = preferences.getBoolean("key_main_favorite_$sectionNumber", false)
        binding.tbFavoriteMainChapter.isChecked = bookmarkStates

        binding.tbFavoriteMainChapter.setOnCheckedChangeListener(this)
        binding.btnCopyMainContent.setOnClickListener(this)
        binding.btnShareMainContent.setOnClickListener(this)

        contentsTextSize()

        return binding.root
    }

    override fun mainNameClick(mainNameId: Int) {
        playName(mainNameId)
    }


    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            R.id.tbFavoriteMainChapter -> {
                val favorite = ContentValues()
                favorite.put("Favorite", isChecked)

                try {
                    database.update(
                        "Table_of_chapters",
                        favorite,
                        "id = ?",
                        arrayOf("$sectionNumber")
                    )
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
                }

                if (isChecked) {
                    Toast.makeText(requireContext(), getString(R.string.action_added_to_bookmark), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), getString(R.string.action_removed_from_bookmark), Toast.LENGTH_SHORT).show()
                }
                editor.putBoolean("key_main_favorite_$sectionNumber", isChecked).apply()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {

            R.id.btnCopyMainContent -> {
                shareAllContent(false)
            }

            R.id.btnShareMainContent -> {
                shareAllContent(true)
            }
        }
    }

    override fun onSharedPreferenceChanged(preferences: SharedPreferences?, key: String?) {
        contentsTextSize()
    }

    private fun playName(position: Int) {
        clearPlayer()
        val resId = context?.resources?.getIdentifier(
            mainNameList[position].mainNameAudio, "raw", "jmapps.thenamesof")
        mediaPlayer = MediaPlayer.create(context, resId!!)
        mediaPlayer?.start()
        mainNamesAdapter.onItemSelected(position)
    }

    private fun clearPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun shareAllContent(contentStateShare: Boolean) {

        val namesSS = StringBuilder()
        val ayahsSS = StringBuilder()
        val chapterContent = mainContentList[sectionNumber!! - 1].chapterContent
        val appLink = "https://play.google.com/store/apps/details?id=jmapps.thenamesof"

        for (i in 0 until mainNameList.size) {
            namesSS.append(mainNameList[i].mainNameArabic)
                .append("<br/>")
                .append(mainNameList[i].mainNameTranscription)
                .append("<br/>")
                .append(mainNameList[i].mainNameTranslation)
                .append("<br/>")
        }

        for (i in 0 until mainAyahList.size) {
            ayahsSS.append(mainAyahList[i].mainAyahArabic)
                .append("<br/>")
                .append(mainAyahList[i].mainAyahTranslation)
                .append("<br/>")
                .append(mainAyahList[i].mainAyahSource)
                .append("<p/>")
        }

        if (contentStateShare) {
            val shareContent = Intent(Intent.ACTION_SEND)
            shareContent.type = "text/plain"
            shareContent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("$namesSS<p/>$ayahsSS<p/>$chapterContent<p/>$appLink").toString())
            context?.startActivity(shareContent)
        } else {
            myClipboard = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            myClip = newPlainText("", Html.fromHtml("$namesSS<br/>$ayahsSS<p/>$chapterContent<p/>$appLink"))
            myClipboard?.setPrimaryClip(myClip!!)
            Toast.makeText(requireContext(), getString(R.string.action_copied_to_clipboard), Toast.LENGTH_SHORT).show()
        }
    }

    private fun contentsTextSize() {
        val lastContentSizeProgress = preferences.getInt(MainContentSettingsBottomSheet.keyMainContentsTextSize, 1)

        binding.tvChapterContent.textSize = contentSizeValues[lastContentSizeProgress].toFloat()
    }
}