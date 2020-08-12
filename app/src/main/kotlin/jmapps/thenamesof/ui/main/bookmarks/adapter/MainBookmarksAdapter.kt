package jmapps.thenamesof.ui.main.bookmarks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.main.bookmarks.holder.MainBookmarksHolder
import jmapps.thenamesof.ui.main.bookmarks.model.MainBookmarksModel

class MainBookmarksAdapter(
    context: Context,
    private val mainBookmarkList: MutableList<MainBookmarksModel>,
    private val onItemMainBookmarkClick: OnItemMainBookmarkClick) : RecyclerView.Adapter<MainBookmarksHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    interface OnItemMainBookmarkClick {
        fun itemBookmarkClick(mainChapterId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainBookmarksHolder {
        val bookmarkView = inflater.inflate(R.layout.item_main_bookmark_name, parent, false)
        return MainBookmarksHolder(bookmarkView)
    }

    override fun getItemCount(): Int {
        return mainBookmarkList.size
    }

    override fun onBindViewHolder(holder: MainBookmarksHolder, position: Int) {
        val current = mainBookmarkList[position]

        val mainBookmarkId = current.mainBookmarkId
        val mainBookmarkNumber = current.mainBookmarkNumber
        val mainBookmarkTitle = current.mainBookmarkTitle

        holder.tvMainBookmarkNumber.text = mainBookmarkNumber
        holder.tvMainBookmarkTitle.text = mainBookmarkTitle

        holder.findMainBookmarksItemClick(onItemMainBookmarkClick, mainBookmarkId)
    }
}