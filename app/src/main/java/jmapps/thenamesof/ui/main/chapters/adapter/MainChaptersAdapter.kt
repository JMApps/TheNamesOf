package jmapps.thenamesof.ui.main.chapters.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.main.chapters.holder.MainChaptersHolder
import jmapps.thenamesof.ui.main.chapters.model.MainChaptersModel

class MainChaptersAdapter(
    context: Context,
    private var mainChapterList: MutableList<MainChaptersModel>,
    private val onItemMainChapterClick: OnItemMainChapterClick) : RecyclerView.Adapter<MainChaptersHolder>(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var mainChapterListNull: MutableList<MainChaptersModel>? = null

    init {
        this.mainChapterListNull = mainChapterList
    }

    interface OnItemMainChapterClick {
        fun itemChapterClick(mainChapterId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainChaptersHolder {
        val chapterView = inflater.inflate(R.layout.item_main_chapter_name, parent, false)
        return MainChaptersHolder(chapterView)
    }

    override fun getItemCount(): Int {
        return mainChapterList.size
    }

    override fun onBindViewHolder(holder: MainChaptersHolder, position: Int) {
        val current = mainChapterList[position]

        val mainChapterId = current.mainChapterId
        val mainChapterNumber = current.mainChapterNumber
        val mainChapterTitle = current.mainChapterTitle

        holder.tvMainChapterNumber.text = mainChapterNumber
        holder.tvMainChapterTitle.text = mainChapterTitle

        holder.findMainChaptersItemClick(onItemMainChapterClick, mainChapterId)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            @SuppressLint("DefaultLocale")
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                mainChapterList = if (charString.isEmpty()) {
                    mainChapterListNull as MutableList<MainChaptersModel>
                } else {
                    val filteredList = ArrayList<MainChaptersModel>()
                    for (row in mainChapterListNull!!) {
                        if (row.mainChapterNumber.toLowerCase().contains(charString.toLowerCase()) ||
                            row.mainChapterTitle.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = mainChapterList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                mainChapterList = filterResults.values as ArrayList<MainChaptersModel>
                notifyDataSetChanged()
            }
        }
    }
}