package jmapps.thenamesof.ui.main.chapters.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.main.chapters.holder.MainChaptersHolder
import jmapps.thenamesof.ui.main.chapters.model.MainChaptersModel

class MainChaptersAdapter(
    context: Context,
    private val mainChapterList: MutableList<MainChaptersModel>,
    private val onItemMainChapterClick: OnItemMainChapterClick) : RecyclerView.Adapter<MainChaptersHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

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
}