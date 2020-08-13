package jmapps.thenamesof.ui.allnames.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.allnames.holder.AllNamesHolder
import jmapps.thenamesof.ui.allnames.model.AllNamesModel

class AllNamesAdapter(
    context: Context,
    private val allNamesList: MutableList<AllNamesModel>,
    private val allNameItemClick: AllNameItemClick,
    private val shareAllNamesItemClick: ShareAllNamesItemClick) : RecyclerView.Adapter<AllNamesHolder>() {

    private var currentItem: Int = -1
    val inflater: LayoutInflater = LayoutInflater.from(context)

    interface AllNameItemClick {
        fun allNameItemClick(position: Int)
    }

    interface ShareAllNamesItemClick {
        fun shareAllNamesItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllNamesHolder {
        val allNamesView = inflater.inflate(R.layout.item_all_name, parent, false)
        return AllNamesHolder(allNamesView)
    }

    override fun getItemCount(): Int {
        return allNamesList.size
    }

    override fun onBindViewHolder(holder: AllNamesHolder, position: Int) {
        val current = allNamesList[position]

        holder.tvAllArabicName.text = current.allNameArabic
        holder.tvAllTranscriptionName.text = current.allNameTranscription
        holder.tvAllTranslationName.text = current.allNameTranslation

        holder.findAllNameItemClick(allNameItemClick, position)
        holder.findShareAllNamesItemClick(shareAllNamesItemClick, position)

        if (currentItem == position) {
            holder.ivPlayAllName.setImageResource(R.drawable.ic_play_primary)
        } else {
            holder.ivPlayAllName.setImageResource(R.drawable.ic_play_gray)
        }
    }

    fun onItemSelected(currentItem: Int) {
        this.currentItem = currentItem
        notifyDataSetChanged()
    }
}