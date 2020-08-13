package jmapps.thenamesof.ui.allnames.holder

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.allnames.adapter.AllNamesAdapter

class AllNamesHolder(allNameView: View) : RecyclerView.ViewHolder(allNameView) {

    val tvAllArabicName: TextView = allNameView.findViewById(R.id.tvAllArabicName)
    val tvAllTranscriptionName: TextView = allNameView.findViewById(R.id.tvAllTranscriptionName)
    val tvAllTranslationName: TextView = allNameView.findViewById(R.id.tvAllTranslationName)
    val ivPlayAllName: ImageView = allNameView.findViewById(R.id.ivPlayAllName)
    val btnShareNames: Button = allNameView.findViewById(R.id.btnShareNames)

    fun findAllNameItemClick(allNameItemClick: AllNamesAdapter.AllNameItemClick, position: Int) {
        itemView.setOnClickListener {
            allNameItemClick.allNameItemClick(position)
        }
    }
}