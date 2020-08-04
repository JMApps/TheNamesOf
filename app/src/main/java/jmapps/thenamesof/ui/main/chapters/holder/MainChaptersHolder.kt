package jmapps.thenamesof.ui.main.chapters.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.main.chapters.adapter.MainChaptersAdapter

class MainChaptersHolder(chaptersView: View) : RecyclerView.ViewHolder(chaptersView) {

    val tvMainChapterNumber: TextView = chaptersView.findViewById(R.id.tvMainChapterNumber)
    val tvMainChapterTitle: TextView = chaptersView.findViewById(R.id.tvMainChapterTitle)

    fun findMainChaptersItemClick(onItemMainChapterClick: MainChaptersAdapter.OnItemMainChapterClick, mainChapterId: Int) {
        itemView.setOnClickListener {
            onItemMainChapterClick.itemChapterClick(mainChapterId)
        }
    }
}