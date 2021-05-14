package by.senla.timmeleshko.task2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task2.beans.ListItem

abstract class RecyclerViewAdapterWithButtons(val context: Context, private var listItems: List<ListItem>) :
    RecyclerView.Adapter<RecyclerViewAdapterWithButtons.ListViewHolder>() {

    abstract fun goToPostFragment()
    abstract fun goToImageViewActivity()
    abstract fun goToArtistFragment()

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_with_buttons, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = listItems[position]
        holder.itemId.text = item.id.toString()
        holder.itemTitle.text = item.title
        holder.itemDescription.text = item.description
        holder.itemGoToPostFragment.setOnClickListener { goToPostFragment() }
        holder.itemGoToImageViewActivity.setOnClickListener { goToImageViewActivity() }
        holder.itemGoToArtistFragment.setOnClickListener { goToArtistFragment() }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemId: TextView = view.findViewById(R.id.itemId)
        var itemTitle: TextView = view.findViewById(R.id.itemTitle)
        var itemDescription: TextView = view.findViewById(R.id.itemDescription)
        var itemGoToPostFragment: Button = view.findViewById(R.id.goToPostFragment)
        var itemGoToImageViewActivity: Button = view.findViewById(R.id.goToImageViewActivity)
        var itemGoToArtistFragment: Button = view.findViewById(R.id.goToArtistFragment)
    }
}