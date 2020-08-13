package jmapps.thenamesof.ui.flip.holder

import android.content.SharedPreferences
import android.view.View
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.wajahatkarim3.easyflipview.EasyFlipView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.flip.SettingsFlipListBottomSheet
import jmapps.thenamesof.ui.flip.adapter.FlipListAdapter

class FlipListHolder(flipView: View) : RecyclerView.ViewHolder(flipView),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private var namesSizeValues = (22..40).toList().filter { it % 2 == 0 }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(itemView.context)

    private val flViewNames: EasyFlipView = flipView.findViewById(R.id.flViewWords)

    val tvFlipArabicName: TextView = flipView.findViewById(R.id.tvFlipArabicName)
    val tvFlipArabicNameNumber: TextView = flipView.findViewById(R.id.tvFlipArabicNameNumber)
    val tvFlipTranslationName: TextView = flipView.findViewById(R.id.tvFlipTranslationName)
    val tvFlipTranslationNameNumber: TextView = flipView.findViewById(R.id.tvFlipTranslationNameNumber)

    init {
        PreferenceManager.getDefaultSharedPreferences(itemView.context)
            .registerOnSharedPreferenceChangeListener(this)
        namesTextSize()
    }

    fun findFlipItemClick(flipCardItemClick: FlipListAdapter.FlipCardItemClick, position: Int) {
        itemView.setOnClickListener {
            flViewNames.flipTheView()
            flipCardItemClick.flipItemClick(position)
        }
    }

    override fun onSharedPreferenceChanged(preferences: SharedPreferences?, key: String?) {
        namesTextSize()
    }

    private fun namesTextSize() {
        val lastTextSizeProgress = preferences.getInt(SettingsFlipListBottomSheet.keyFlipNamesTextSize, 4)

        tvFlipArabicName.textSize = namesSizeValues[lastTextSizeProgress].toFloat()
        tvFlipTranslationName.textSize = namesSizeValues[lastTextSizeProgress].toFloat()
    }
}