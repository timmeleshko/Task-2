package by.senla.timmeleshko.task6.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import by.senla.timmeleshko.task6.model.beans.WorkDto
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.DATA_VIEW_TYPE
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.FOOTER_VIEW_TYPE

class WorksAdapter : PagingDataAdapter<WorkDto, WorksViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: WorksViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
            holder: WorksViewHolder,
            position: Int,
            payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            holder.updateCounters(item)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorksViewHolder {
        return WorksViewHolder.create(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<WorkDto>() {
            override fun areContentsTheSame(oldItem: WorkDto, newItem: WorkDto): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: WorkDto, newItem: WorkDto): Boolean =
                    oldItem.name == newItem.name
        }
    }
}
