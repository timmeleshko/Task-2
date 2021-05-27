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
import by.senla.timmeleshko.task6.model.Constants
import by.senla.timmeleshko.task6.model.beans.WorkDto
import by.senla.timmeleshko.task6.utils.MediaRatio
import by.senla.timmeleshko.task6.utils.MediaSide
import by.senla.timmeleshko.task6.utils.buildMediaUrl
import by.senla.timmeleshko.task6.utils.submitImage

class WorksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(work: WorkDto?) {
        if (work != null) {
            itemView.findViewById<TextView>(R.id.itemTitle).text = work.name ?: Constants.DEFAULT_TEXT
            itemView.findViewById<TextView>(R.id.itemLikesAmount).text = work.counters?.likes ?: Constants.DEFAULT_LIKES
            val url = work.media_dto?.let {
                buildMediaUrl(Constants.DOWNLOAD_IMAGE_SIZE,
                    it, MediaRatio.s, MediaSide.x)
            }
            if (url != null) {
                var hexColor = work.colors?.middle
                if (hexColor.isNullOrEmpty()) {
                    hexColor = Constants.DEFAULT_PLACEHOLDER_COLOR
                }
                itemView.findViewById<ImageView>(R.id.itemImage).submitImage(url, ColorDrawable(
                    Color.parseColor("#$hexColor")))
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): WorksViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            return WorksViewHolder(view)
        }
    }
}