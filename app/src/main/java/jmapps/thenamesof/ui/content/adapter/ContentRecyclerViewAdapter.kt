package jmapps.thenamesof.ui.content.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.content.holder.ContentHolder
import jmapps.thenamesof.ui.content.model.ModelContent

class ContentRecyclerViewAdapter internal constructor(
    context: Context,
    private val contentList: MutableList<ModelContent>,
    private val contentItemClick: ContentItemClick) : RecyclerView.Adapter<ContentHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    interface ContentItemClick {
        fun contentItemClick(contentId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentHolder {
        val contentView = inflater.inflate(R.layout.item_book_chapter, parent, false)
        return ContentHolder(contentView)
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    override fun onBindViewHolder(holder: ContentHolder, position: Int) {
        val currentContent = contentList[position]
        holder.tvContentNumber.text = currentContent.contentNumber
        holder.tvContentTitle.text = currentContent.contentTitle

        holder.findItemContentClick(contentItemClick, currentContent.contentId)
    }
}