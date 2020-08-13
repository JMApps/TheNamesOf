package jmapps.thenamesof.ui.allnames.holder

import android.content.SharedPreferences
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.allnames.SettingsAllNamesBottomSheet
import jmapps.thenamesof.ui.allnames.adapter.AllNamesAdapter

class AllNamesHolder(allNameView: View) : RecyclerView.ViewHolder(allNameView),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private var namesSizeValues = (16..34).toList().filter { it % 2 == 0 }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(itemView.context)

    val tvAllArabicName: TextView = allNameView.findViewById(R.id.tvAllArabicName)
    val tvAllTranscriptionName: TextView = allNameView.findViewById(R.id.tvAllTranscriptionName)
    val tvAllTranslationName: TextView = allNameView.findViewById(R.id.tvAllTranslationName)

    val ivPlayAllName: ImageView = allNameView.findViewById(R.id.ivPlayAllName)
    val btnShareNames: Button = allNameView.findViewById(R.id.btnShareNames)

    init {
        PreferenceManager.getDefaultSharedPreferences(itemView.context)
            .registerOnSharedPreferenceChangeListener(this)
        namesTextSize()
    }

    fun findAllNameItemClick(allNameItemClick: AllNamesAdapter.AllNameItemClick, position: Int) {
        itemView.setOnClickListener {
            allNameItemClick.allNameItemClick(position)
        }
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        namesTextSize()
    }

    private fun namesTextSize() {
        val lastTextSizeProgress = preferences.getInt(SettingsAllNamesBottomSheet.keyAllNamesTextSize, 1)

        tvAllArabicName.textSize = namesSizeValues[lastTextSizeProgress].toFloat()
        tvAllTranscriptionName.textSize = namesSizeValues[lastTextSizeProgress].toFloat()
        tvAllTranslationName.textSize = namesSizeValues[lastTextSizeProgress].toFloat()
    }
}