package by.senla.timmeleshko.task6.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R
import by.senla.timmeleshko.task6.model.dto.FilterDto
import by.senla.timmeleshko.task6.model.dto.WorkDto
import by.senla.timmeleshko.task6.view.ExtendedChipGroup
import com.google.android.material.chip.Chip

abstract class ChipsViewHolder(
    val view: View
) : RecyclerView.ViewHolder(view) {

    private val chipGroup: ExtendedChipGroup = view.findViewById(R.id.chipGroup)

    abstract fun clickChip(uri: String)

    fun bind(work: WorkDto?) {
        if (work != null) {
            view.setOnClickListener { chipGroup.update() }
            val filterDto = work.filter_dto
            if (filterDto != null && chipGroup.isEmpty()) {
                chipGroup.setChips(filterDto.map { convertedName(it) })
                chipGroup.isSingleSelection = true
                chipGroup.setOnCheckedChangeListener { chipGroup, i ->
                    val chip: Chip? = chipGroup.findViewById(i)
                    if (chip != null) {
                        if (chip.isChecked) {
                            filterDto.find { filter -> convertedName(filter) == chip.text.toString() }?.uri?.let {
                                clickChip(it)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun convertedName(filterDto: FilterDto) : String {
        return filterDto.count_works.plus(" ").plus(filterDto.name)
    }

    companion object {
        fun create(parent: ViewGroup, adapter: WorksAdapter): ChipsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_chips, parent, false)
            return object : ChipsViewHolder(view) {
                override fun clickChip(uri: String) {
                    adapter.clickAdapterChip(uri)
                }
            }
        }
    }
}