package by.senla.timmeleshko.task6.view.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import by.senla.timmeleshko.task6.model.dto.WorkDto
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.DATA_VIEW_TYPE
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.FOOTER_VIEW_TYPE

class WorksAdapter(
    private val context: Context,
    private var lastPosition: Int = -1
) : PagingDataAdapter<WorkDto, WorksViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: WorksViewHolder, position: Int) {
        holder.bind(getItem(position))
        setAnimation(holder.itemView, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorksViewHolder {
        return WorksViewHolder.create(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            in 0 until itemCount -> {
                DATA_VIEW_TYPE
            }
            else -> {
                FOOTER_VIEW_TYPE
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: WorksViewHolder) {
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
