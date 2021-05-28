package by.senla.timmeleshko.task6.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R
import by.senla.timmeleshko.task6.model.enums.State

class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): FooterViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_footer, parent, false)
            view.setOnClickListener { retry() }
            return FooterViewHolder(view)
        }
    }
}