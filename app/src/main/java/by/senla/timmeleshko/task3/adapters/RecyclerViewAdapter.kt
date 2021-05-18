package by.senla.timmeleshko.task3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task3.R
import by.senla.timmeleshko.task3.model.beans.Work

open class RecyclerViewAdapter(private var listItems: List<Work>) : RecyclerView.Adapter<RecyclerViewAdapter.ListViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = listItems[position]
        holder.itemId.text = item.work_id.toString() // а если у тебя item.work_id = null ?
        holder.itemTitle.text = item.name // то же самое
        holder.itemDescription.text = item.description // -//-
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

//    fun updateData(data: List<Work>) {
//        listItems = data
//        notifyDataSetChanged()
//    }

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // неизменяемые переменные нужно использовать везде где это возможно
        var itemId: TextView = view.findViewById(R.id.itemId)
        var itemTitle: TextView = view.findViewById(R.id.itemTitle)
        var itemDescription: TextView = view.findViewById(R.id.itemDescription)

//        val itemId: TextView = view.findViewById(R.id.itemId)
//        val itemTitle: TextView = view.findViewById(R.id.itemTitle)
//        val itemDescription: TextView = view.findViewById(R.id.itemDescription)
    }
}