package by.senla.timmeleshko.task6.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class WorksLoadStateAdapter : LoadStateAdapter<FooterViewHolder>() {

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        return FooterViewHolder(parent)
    }
}