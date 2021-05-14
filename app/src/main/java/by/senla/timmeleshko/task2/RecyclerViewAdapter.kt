package by.senla.timmeleshko.task2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task2.beans.ListItem

abstract class RecyclerViewAdapter(val context: Context, private var listItems: List<ListItem>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ListViewHolder>() {

    abstract fun onClickItem()

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = listItems[position]
        holder.itemId.text = item.id.toString()
        holder.itemTitle.text = item.title
        holder.itemDescription.text = item.description
        holder.itemView.setOnClickListener { onClickItem() }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemId: TextView = view.findViewById(R.id.itemId)
        var itemTitle: TextView = view.findViewById(R.id.itemTitle)
        var itemDescription: TextView = view.findViewById(R.id.itemDescription)
    }
}