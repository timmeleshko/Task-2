package by.senla.timmeleshko.task6.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R

class ChipsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup): ChipsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_chips, parent, false)
            return ChipsViewHolder(view)
        }
    }
}