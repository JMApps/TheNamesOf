package jmapps.thenamesof.ui.flip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jmapps.thenamesof.R
import jmapps.thenamesof.ui.flip.holder.FlipListHolder
import jmapps.thenamesof.ui.flip.model.FlipListModel

class FlipListAdapter(
    context: Context,
    private val cardState: Boolean,
    private val flipNameList: MutableList<FlipListModel>,
    private val flipCardItemClick: FlipCardItemClick) : RecyclerView.Adapter<FlipListHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    interface FlipCardItemClick {
        fun flipItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlipListHolder {
        return if (cardState) {
            val cardArabic = inflater.inflate(R.layout.flip_state_one, parent, false)
            FlipListHolder(cardArabic)
        } else {
            val cardTranslation = inflater.inflate(R.layout.flip_state_two, parent, false)
            FlipListHolder(cardTranslation)
        }
    }

    override fun getItemCount(): Int {
        return flipNameList.size
    }

    override fun onBindViewHolder(holder: FlipListHolder, position: Int) {
        val current = flipNameList[position]

        holder.tvFlipArabicNameNumber.text = current.flipNameId.toString()
        holder.tvFlipArabicName.text = current.flipNameArabic
        holder.tvFlipTranslationNameNumber.text = current.flipNameId.toString()
        holder.tvFlipTranslationName.text = current.flipNameTranslation

        holder.findFlipItemClick(flipCardItemClick, position)
    }
}