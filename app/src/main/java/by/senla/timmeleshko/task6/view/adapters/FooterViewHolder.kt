package by.senla.timmeleshko.task6.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R

class FooterViewHolder(
    parent: ViewGroup,
    view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_footer, parent,
            false),
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(view) {

    private val progressBar: ProgressBar = view.findViewById(R.id.progressBarFooter)
    private val retryButton: Button = view.findViewById(R.id.retryButton)

    fun bindTo(loadState: LoadState) {
        retryButton.setOnClickListener { retryCallback() }
        progressBar.isVisible = loadState is LoadState.Loading
        retryButton.isVisible = loadState is LoadState.Error
    }
}