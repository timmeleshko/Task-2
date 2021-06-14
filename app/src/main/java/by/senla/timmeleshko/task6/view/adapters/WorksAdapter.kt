package by.senla.timmeleshko.task6.view.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.model.dto.WorkDto
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.CHIPS_VIEW_TYPE
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.DATA_VIEW_TYPE
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.FOOTER_VIEW_TYPE
import by.senla.timmeleshko.task6.view.adapters.WorksAdapter.WorksAdapterConstants.CHIPS_POS

class WorksAdapter(
    private val context: Context,
    private var lastPosition: Int = -1
) : PagingDataAdapter<WorkDto, RecyclerView.ViewHolder>(POST_COMPARATOR) {

    object WorksAdapterConstants {
        const val CHIPS_POS = 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == CHIPS_POS) {
            holder as ChipsViewHolder
            holder.bind(getItem(position))
        } else if (position > CHIPS_POS) {
            holder as WorksViewHolder
            holder.bind(getItem(position - 1))
            setAnimation(holder.itemView, position - 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == CHIPS_VIEW_TYPE) {
            ChipsViewHolder.create(parent)
        } else {
            WorksViewHolder.create(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            CHIPS_POS -> {
                CHIPS_VIEW_TYPE
            }
            in (CHIPS_POS + 1) until itemCount -> {
                DATA_VIEW_TYPE
            }
            else -> {
                FOOTER_VIEW_TYPE
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() - 1
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        holder.itemView.clearAnimation()
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation: Animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<WorkDto>() {
            override fun areContentsTheSame(oldItem: WorkDto, newItem: WorkDto): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: WorkDto, newItem: WorkDto): Boolean =
                    oldItem.work_id == newItem.work_id
        }
    }
}
