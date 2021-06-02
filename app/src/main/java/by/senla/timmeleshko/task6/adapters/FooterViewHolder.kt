package by.senla.timmeleshko.task6.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R

class FooterViewHolder(
    parent: ViewGroup,
    view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_footer, parent, false)
) : RecyclerView.ViewHolder(view) {

    private val progressBar: ProgressBar = view.findViewById(R.id.progressBarFooter)

    fun bindTo(loadState: LoadState) {
        progressBar.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
    }
}