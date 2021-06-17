package by.senla.timmeleshko.task6.view.adapters

import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R
import by.senla.timmeleshko.task6.model.dto.FilterDto
import by.senla.timmeleshko.task6.view.customView.ExtendedChipGroup
import com.google.android.material.chip.Chip

abstract class HeaderAdapter(
    private var filters: List<FilterDto>
) : RecyclerView.Adapter<HeaderAdapter.ListViewHolder>() {

    private lateinit var view: View

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_chips, parent, false)
        view = itemView
        return ListViewHolder(itemView)
    }

    abstract fun clickChip(uri: String?)

    fun updateFilters(filters: List<FilterDto>?) {
        if (filters != null) {
            this.filters = filters
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val chipGroup = holder.chipGroup
        if (chipGroup.isEmpty()) {
            view.setOnClickListener { chipGroup.update() }
            chipGroup.setChips(filters.map { convertedName(it) })
            chipGroup.setOnCheckedChangeListener { c, i ->
                val chip: Chip? = c.findViewById(i)
                if (chip != null) {
                    filters.find { filter -> convertedName(filter) == chip.text.toString() }?.uri?.let {
                        clickChip(it)
                    }
                } else {
                    clickChip(null)
                }
            }
        }
    }

    private fun convertedName(filterDto: FilterDto) : String {
        return filterDto.count_works.plus(" ").plus(filterDto.name)
    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val chipGroup: ExtendedChipGroup = view.findViewById(R.id.chipGroup)
    }
}