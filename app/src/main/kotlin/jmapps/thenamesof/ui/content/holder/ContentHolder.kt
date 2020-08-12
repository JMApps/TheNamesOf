package jmapps.thenamesof.ui.content.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.content.adapter.ContentRecyclerViewAdapter

class ContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val tvContentNumber: TextView = itemView.findViewById(R.id.tvContentNumber)
    val tvContentTitle: TextView = itemView.findViewById(R.id.tvContentTitle)

    fun findItemContentClick(contentItemClick: ContentRecyclerViewAdapter.ContentItemClick, contentId: Int) {
        itemView.setOnClickListener { contentItemClick.contentItemClick(contentId) }
    }
}