package jmapps.thenamesof.ui.main.ayahs.holder

import android.content.SharedPreferences
import android.view.View
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.main.settings.MainContentSettingsBottomSheet

class MainAyahsHolder(ayahsView: View) : RecyclerView.ViewHolder(ayahsView),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private var ayahSizeValues = (16..34).toList().filter { it % 2 == 0 }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(itemView.context)

    val tvMainArabicAyah: TextView = ayahsView.findViewById(R.id.tvMainArabicAyah)
    val tvMainTranslationAyah: TextView = ayahsView.findViewById(R.id.tvMainTranslationAyah)
    val tvMainSourceAyah: TextView = ayahsView.findViewById(R.id.tvMainSourceAyah)

    init {
        PreferenceManager.getDefaultSharedPreferences(itemView.context)
            .registerOnSharedPreferenceChangeListener(this)

        ayahsTextSize()
    }

    override fun onSharedPreferenceChanged(preferences: SharedPreferences?, key: String?) {
        ayahsTextSize()
    }

    private fun ayahsTextSize() {
        val lastAyahSizeProgress = preferences.getInt(MainContentSettingsBottomSheet.keyMainAyahsTextSize, 1)

        tvMainArabicAyah.textSize = ayahSizeValues[lastAyahSizeProgress].toFloat()
        tvMainTranslationAyah.textSize = ayahSizeValues[lastAyahSizeProgress].toFloat()
        tvMainSourceAyah.textSize = ayahSizeValues[lastAyahSizeProgress].toFloat() - 6
    }
}