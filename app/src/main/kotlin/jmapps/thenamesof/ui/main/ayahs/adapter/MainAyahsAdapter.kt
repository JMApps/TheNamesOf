package jmapps.thenamesof.ui.main.ayahs.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.main.ayahs.holder.MainAyahsHolder
import jmapps.thenamesof.ui.main.ayahs.model.MainAyahsModel

class MainAyahsAdapter(
    context: Context,
    private val mainAyahsList: MutableList<MainAyahsModel>) : RecyclerView.Adapter<MainAyahsHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAyahsHolder {
        val ayahsView = inflater.inflate(R.layout.item_main_ayah, parent, false)
        return MainAyahsHolder(ayahsView)
    }

    override fun getItemCount(): Int {
        return mainAyahsList.size
    }

    override fun onBindViewHolder(holder: MainAyahsHolder, position: Int) {
        val current = mainAyahsList[position]

        val mainAyahArabic = current.mainAyahArabic
        val mainAyahTranslation = current.mainAyahTranslation
        val mainAyahSource = current.mainAyahSource

        holder.tvMainArabicAyah.text = mainAyahArabic
        holder.tvMainTranslationAyah.text = mainAyahTranslation
        holder.tvMainSourceAyah.text = mainAyahSource
    }
}