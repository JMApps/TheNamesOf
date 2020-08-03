package jmapps.thenamesof.ui.main.names.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.main.names.adapter.MainNamesAdapter

class MainNamesHolder(namesView: View) : RecyclerView.ViewHolder(namesView) {

    val tvMainArabicName: TextView = namesView.findViewById(R.id.tvMainArabicName)
    val tvMainTranscriptionName: TextView = namesView.findViewById(R.id.tvMainTranscriptionName)
    val tvMainTranslateName: TextView = namesView.findViewById(R.id.tvMainTranslationName)
    val ivPlayName: ImageView = namesView.findViewById(R.id.ivPlayName)

    fun findMainNamesItemClick(onItemMainNameClick: MainNamesAdapter.OnItemMainNameClick, mainNameId: Int) {
        itemView.setOnClickListener {
            onItemMainNameClick.mainNameClick(mainNameId)
        }
    }
}