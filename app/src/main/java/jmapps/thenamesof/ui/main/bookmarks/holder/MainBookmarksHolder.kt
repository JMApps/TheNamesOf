package jmapps.thenamesof.ui.main.bookmarks.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.main.bookmarks.adapter.MainBookmarksAdapter

class MainBookmarksHolder(bookmarksView: View) : RecyclerView.ViewHolder(bookmarksView) {

    val tvMainBookmarkNumber: TextView = bookmarksView.findViewById(R.id.tvMainBookmarkNumber)
    val tvMainBookmarkTitle: TextView = bookmarksView.findViewById(R.id.tvMainBookmarkTitle)

    fun findMainBookmarksItemClick(onItemMainBookmarkClick: MainBookmarksAdapter.OnItemMainBookmarkClick, mainBookmarkId: Int) {
        itemView.setOnClickListener {
            onItemMainBookmarkClick.itemBookmarkClick(mainBookmarkId)
        }
    }
}