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
    private val allNameItemClick: AllNameItemClick) : RecyclerView.Adapter<AllNamesHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)

    interface AllNameItemClick {
        fun allNameItemClick(position: Int)
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
    }
}