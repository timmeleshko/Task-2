package by.senla.timmeleshko.task6.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R
import by.senla.timmeleshko.task6.adapters.WorksViewHolder.WorksViewHolderConstants.DEFAULT_LIKES
import by.senla.timmeleshko.task6.adapters.WorksViewHolder.WorksViewHolderConstants.DEFAULT_PLACEHOLDER_COLOR
import by.senla.timmeleshko.task6.adapters.WorksViewHolder.WorksViewHolderConstants.DEFAULT_TEXT
import by.senla.timmeleshko.task6.adapters.WorksViewHolder.WorksViewHolderConstants.DOWNLOAD_IMAGE_SIZE
import by.senla.timmeleshko.task6.model.beans.WorkDto
import by.senla.timmeleshko.task6.utils.MediaRatio
import by.senla.timmeleshko.task6.utils.MediaSide
import by.senla.timmeleshko.task6.utils.buildMediaUrl
import by.senla.timmeleshko.task6.utils.submitImage

class WorksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    object WorksViewHolderConstants {
        const val DEFAULT_TEXT = "NO TEXT"
        const val DEFAULT_LIKES = "0"
        const val DOWNLOAD_IMAGE_SIZE = 200f
        const val DEFAULT_PLACEHOLDER_COLOR = "cccccc"
    }

    private val name: TextView = view.findViewById(R.id.itemTitle)
    private val counters: TextView = view.findViewById(R.id.itemLikesAmount)
    private var work : WorkDto? = null

    fun bind(work: WorkDto?) {
        this.work = work
        if (work != null) {
            name.text = work.name ?: DEFAULT_TEXT
            counters.text = work.counters?.likes ?: DEFAULT_LIKES
            val url = work.media_dto?.let {
                buildMediaUrl(
                    DOWNLOAD_IMAGE_SIZE,
                    it, MediaRatio.s, MediaSide.x)
            }
            if (url != null) {
                var hexColor = work.colors?.middle
                if (hexColor.isNullOrEmpty()) {
                    hexColor = DEFAULT_PLACEHOLDER_COLOR
                }
                itemView.findViewById<ImageView>(R.id.itemImage).submitImage(url, ColorDrawable(
                    Color.parseColor("#$hexColor"))
                )
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): WorksViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            return WorksViewHolder(view)
        }
    }

    fun updateCounters(item: WorkDto?) {
        work = item
        counters.text = item?.counters?.likes ?: DEFAULT_LIKES
    }
}