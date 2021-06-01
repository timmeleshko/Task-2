package by.senla.timmeleshko.task6.adapters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.adapters.WorksListAdapter.WorksListAdapterConstants.DATA_VIEW_TYPE
import by.senla.timmeleshko.task6.adapters.WorksListAdapter.WorksListAdapterConstants.FOOTER_VIEW_TYPE
import by.senla.timmeleshko.task6.model.data.dto.WorkDto
import by.senla.timmeleshko.task6.model.enums.State

class WorksListAdapter(private val retry: () -> Unit) : PagedListAdapter<WorkDto, RecyclerView.ViewHolder>(WorksDiffCallback) {

    object WorksListAdapterConstants {
        const val DATA_VIEW_TYPE = 1
        const val FOOTER_VIEW_TYPE = 2
    }

    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) {
            WorksViewHolder.create(parent)
        } else {
            FooterViewHolder.create(retry, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            (holder as WorksViewHolder).bind(getItem(position))
        } else {
            (holder as FooterViewHolder)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val WorksDiffCallback = object : DiffUtil.ItemCallback<WorkDto>() {
            override fun areItemsTheSame(oldItem: WorkDto, newItem: WorkDto): Boolean {
                return oldItem.work_id == newItem.work_id
            }

            override fun areContentsTheSame(oldItem: WorkDto, newItem: WorkDto): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}