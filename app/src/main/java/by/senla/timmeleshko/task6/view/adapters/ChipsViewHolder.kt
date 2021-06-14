package by.senla.timmeleshko.task6.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R
import by.senla.timmeleshko.task6.model.dto.WorkDto
import by.senla.timmeleshko.task6.view.ExtendedChipGroup

class ChipsViewHolder(
    val view: View
) : RecyclerView.ViewHolder(view) {

    private val chipGroup: ExtendedChipGroup = view.findViewById(R.id.chipGroup)

    fun bind(work: WorkDto?) {
        if (work != null) {
            view.setOnClickListener { chipGroup.update() }
            val filterDto = work.filter_dto
            if (filterDto != null && chipGroup.isEmpty()) {
                chipGroup.setChips(filterDto.map { it.count_works.plus(" ").plus(it.uri) })
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): ChipsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_chips, parent, false)
            return ChipsViewHolder(view)
        }
    }
}