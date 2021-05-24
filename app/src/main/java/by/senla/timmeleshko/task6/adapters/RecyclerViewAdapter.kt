package by.senla.timmeleshko.task6.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R
import by.senla.timmeleshko.task6.model.Constants.DEFAULT_LIKES
import by.senla.timmeleshko.task6.model.Constants.DEFAULT_TEXT
import by.senla.timmeleshko.task6.model.beans.Work

open class RecyclerViewAdapter(private var listItems: List<Work>) : RecyclerView.Adapter<RecyclerViewAdapter.ListViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = listItems[position]
        holder.itemTitle.text = item.name ?: DEFAULT_TEXT
        holder.itemLikesAmount.text = item.counters?.likes?.toString() ?: DEFAULT_LIKES
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun updateData(data: List<Work>) {
        listItems = data
        notifyDataSetChanged()
    }

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val itemTitle: TextView = view.findViewById(R.id.itemTitle)
        val itemLikesAmount: TextView = view.findViewById(R.id.itemLikesAmount)
        val itemInfoImage: ImageView = view.findViewById(R.id.itemInfoImage)
    }
}