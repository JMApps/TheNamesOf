package jmapps.thenamesof.ui.main.ayahs.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R

class MainAyahsHolder(ayahsView: View) : RecyclerView.ViewHolder(ayahsView) {

    val tvMainArabicAyah: TextView = ayahsView.findViewById(R.id.tvMainArabicAyah)
    val tvMainTranslationAyah: TextView = ayahsView.findViewById(R.id.tvMainTranslationAyah)
    val tvMainSourceAyah: TextView = ayahsView.findViewById(R.id.tvMainSourceAyah)
}