package by.senla.timmeleshko.task6.view.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class WorksLoadStateAdapter(
        private val adapter: WorksAdapter
) : LoadStateAdapter<FooterViewHolder>() {

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        return FooterViewHolder(parent) { adapter.retry() }
    }
}