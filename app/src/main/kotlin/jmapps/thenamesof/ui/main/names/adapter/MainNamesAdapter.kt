package jmapps.thenamesof.ui.main.names.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.main.names.holder.MainNamesHolder
import jmapps.thenamesof.ui.main.names.model.MainNamesModel

class MainNamesAdapter(
    context: Context,
    private val mainNameList: MutableList<MainNamesModel>,
    private val onItemMainNameClick: OnItemMainNameClick) : RecyclerView.Adapter<MainNamesHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)

    private var currentItem: Int = -1

    interface OnItemMainNameClick {
        fun mainNameClick(mainNameId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainNamesHolder {
        val mainNamesView = inflater.inflate(R.layout.item_main_name, parent, false)
        return MainNamesHolder(mainNamesView)
    }

    override fun getItemCount(): Int {
        return mainNameList.size
    }

    override fun onBindViewHolder(holder: MainNamesHolder, position: Int) {
        val current = mainNameList[position]

        val mainNameId = current.mainNameId
        val mainNameArabic = current.mainNameArabic
        val mainNameTranscription = current.mainNameTranscription
        val mainNameTranslation = current.mainNameTranslation

        holder.tvMainArabicName.text = mainNameArabic
        holder.tvMainTranscriptionName.text = mainNameTranscription
        holder.tvMainTranslateName.text = mainNameTranslation

        holder.findMainNamesItemClick(onItemMainNameClick, position)

        if (currentItem == position) {
            holder.ivPlayName.setImageResource(R.drawable.ic_play_primary)
        } else {
            holder.ivPlayName.setImageResource(R.drawable.ic_play_gray)
        }
    }

    fun onItemSelected(currentItem: Int) {
        this.currentItem = currentItem
        notifyDataSetChanged()
    }
}