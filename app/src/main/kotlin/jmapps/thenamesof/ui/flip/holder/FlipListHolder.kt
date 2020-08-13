package jmapps.thenamesof.ui.flip.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wajahatkarim3.easyflipview.EasyFlipView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.flip.adapter.FlipListAdapter

class FlipListHolder(flipView: View) : RecyclerView.ViewHolder(flipView) {

    private val flViewNames: EasyFlipView = flipView.findViewById(R.id.flViewWords)

    val tvFlipArabicName: TextView = flipView.findViewById(R.id.tvFlipArabicName)
    val tvFlipArabicNameNumber: TextView = flipView.findViewById(R.id.tvFlipArabicNameNumber)
    val tvFlipTranslationName: TextView = flipView.findViewById(R.id.tvFlipTranslationName)
    val tvFlipTranslationNameNumber: TextView = flipView.findViewById(R.id.tvFlipTranslationNameNumber)

    fun findFlipItemClick(flipCardItemClick: FlipListAdapter.FlipCardItemClick, position: Int) {
        itemView.setOnClickListener {
            flViewNames.flipTheView()
            flipCardItemClick.flipItemClick(position)
        }
    }
}