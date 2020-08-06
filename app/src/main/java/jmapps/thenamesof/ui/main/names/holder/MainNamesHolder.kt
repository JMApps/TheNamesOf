package jmapps.thenamesof.ui.main.names.holder

import android.content.SharedPreferences
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.main.names.adapter.MainNamesAdapter
import jmapps.thenamesof.ui.main.settings.MainContentSettingsBottomSheet

class MainNamesHolder(namesView: View) : RecyclerView.ViewHolder(namesView),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private var namesSizeValues = (16..34).toList().filter { it % 2 == 0 }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(itemView.context)

    val tvMainArabicName: TextView = namesView.findViewById(R.id.tvMainArabicName)
    val tvMainTranscriptionName: TextView = namesView.findViewById(R.id.tvMainTranscriptionName)
    val tvMainTranslateName: TextView = namesView.findViewById(R.id.tvMainTranslationName)
    val ivPlayName: ImageView = namesView.findViewById(R.id.ivPlayName)

    init {
        PreferenceManager.getDefaultSharedPreferences(itemView.context)
            .registerOnSharedPreferenceChangeListener(this)

        namesTextSize()
        namesShowState()
    }

    fun findMainNamesItemClick(onItemMainNameClick: MainNamesAdapter.OnItemMainNameClick, mainNameId: Int) {
        itemView.setOnClickListener {
            onItemMainNameClick.mainNameClick(mainNameId)
        }
    }

    override fun onSharedPreferenceChanged(prerences: SharedPreferences?, key: String?) {
        namesTextSize()
        namesShowState()
    }

    private fun namesTextSize() {
        val lastTextSizeProgress = preferences.getInt(MainContentSettingsBottomSheet.keyMainNamesTextSize, 1)

        tvMainArabicName.textSize = namesSizeValues[lastTextSizeProgress].toFloat()
        tvMainTranscriptionName.textSize = namesSizeValues[lastTextSizeProgress].toFloat()
        tvMainTranslateName.textSize = namesSizeValues[lastTextSizeProgress].toFloat()
    }

    private fun namesShowState() {
        val lastShowArabicState = preferences.getBoolean(MainContentSettingsBottomSheet.keyShowNameArabicState, true)
        val lastShowTranscriptionState = preferences.getBoolean(MainContentSettingsBottomSheet.keyShowNameTranscriptionState, true)
        val lastShowTranslationState = preferences.getBoolean(MainContentSettingsBottomSheet.keyShowNameTranslationState, true)

        if (lastShowArabicState) {
            tvMainArabicName.visibility = View.VISIBLE
        } else {
            tvMainArabicName.visibility = View.INVISIBLE
        }

        if (lastShowTranscriptionState) {
            tvMainTranscriptionName.visibility = View.VISIBLE
        } else {
            tvMainTranscriptionName.visibility = View.GONE
        }

        if (lastShowTranslationState) {
            tvMainTranslateName.visibility = View.VISIBLE
        } else {
            tvMainTranslateName.visibility = View.INVISIBLE
        }
    }
}