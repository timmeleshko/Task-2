package by.senla.timmeleshko.task6.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R
import by.senla.timmeleshko.task6.model.Constants
import by.senla.timmeleshko.task6.model.Constants.DEFAULT_LIKES
import by.senla.timmeleshko.task6.model.Constants.DEFAULT_TEXT
import by.senla.timmeleshko.task6.model.beans.Data
import by.senla.timmeleshko.task6.model.beans.MediaDto
import by.senla.timmeleshko.task6.model.beans.WorkDto
import by.senla.timmeleshko.task6.utils.MediaRatio
import by.senla.timmeleshko.task6.utils.MediaSide
import by.senla.timmeleshko.task6.utils.buildMediaUrl
import by.senla.timmeleshko.task6.utils.submitImage

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ListViewHolder>() {

    private var listItems: List<WorkDto> = listOf()
    private var data: Data? = null

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = listItems[position]
        holder.itemTitle.text = item.name ?: DEFAULT_TEXT
        holder.itemLikesAmount.text = item.counters?.likes ?: DEFAULT_LIKES
        val url = data?.media?.let { getUrl(it, item) }
        if (url != null) {
            Log.i(Constants.IMAGE_URL, url)
            val colorDrawable = ColorDrawable(Color.parseColor("#${item.colors?.middle}"))
            holder.itemImage.submitImage(url, colorDrawable)
        }
    }

    private fun getUrl(mediaDto: List<MediaDto>, item: WorkDto): String? {
        for (media in mediaDto) {
            if (media.media_id == item.media_id) {
                return media.data?.sizes?.orig?.x?.let { buildMediaUrl(it, media, MediaRatio.s, MediaSide.x) }
            }
        }
        return null
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun updateData(data: Data) {
        this.data = data
        listItems = data.works!!
        notifyDataSetChanged()
    }

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val itemTitle: TextView = view.findViewById(R.id.itemTitle)
        val itemLikesAmount: TextView = view.findViewById(R.id.itemLikesAmount)
    }
}