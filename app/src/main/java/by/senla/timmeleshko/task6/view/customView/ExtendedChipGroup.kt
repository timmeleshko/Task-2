package by.senla.timmeleshko.task6.view.customView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.view.ViewCompat
import by.senla.timmeleshko.task6.R
import by.senla.timmeleshko.task6.view.customView.ExtendedChipGroup.ExtendedChipGroupConstants.CHIP_MORE_TITLE
import by.senla.timmeleshko.task6.view.customView.ExtendedChipGroup.ExtendedChipGroupConstants.ITEM_SPACING
import by.senla.timmeleshko.task6.view.customView.ExtendedChipGroup.ExtendedChipGroupConstants.MAX_ROW
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup

class ExtendedChipGroup : ChipGroup {

    object ExtendedChipGroupConstants {
        const val CHIP_MORE_TITLE = "+"
        const val MAX_ROW = 2
        const val ITEM_SPACING = 30
    }

    private var maxRow = MAX_ROW
    private var lastChipsList: ArrayList<String> = ArrayList()
    private var row = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, com.google.android.material.R.attr.chipGroupStyle)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun update() {
        maxRow = MAX_ROW
        setChips(ArrayList(lastChipsList))
    }

    fun setChips(chipsList: List<String>) {
        removeAllViews()
        lastChipsList.clear()
            // тренируй навык работы со стримами, они сокращают код и больше соответствуют стилистике котлин
        for (chipListItem in chipsList) {
            if (chipListItem.isNotEmpty()) {
                val chip = Chip(context)
                val chipDrawable = ChipDrawable.createFromAttributes(context, null,
                    0, R.style.Widget_MaterialComponents_Chip_Choice)
                // для работы с одной и той же переменной используй соответствующие функции котлин with, let, also
                chip.setChipDrawable(chipDrawable)
                chip.text = chipListItem
                chip.isCheckable = true
                lastChipsList.add(chipListItem)
                addView(chip)
            }
        }
    }

    override fun onLayout(sizeChanged: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (childCount == 0) {
            row = 0
            return
        }
        row = 1
        val isRtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL
        val paddingStart = if (isRtl) paddingRight else paddingLeft
        val paddingEnd = if (isRtl) paddingLeft else paddingRight
        var childStart = paddingStart
        var childTop = paddingTop
        var childBottom = childTop
        var childEnd: Int
        val maxChildEnd = right - left - paddingEnd
        for (i in 0 until childCount) {
            if (getChildAt(i) != null) {
                val child = getChildAt(i) as Chip
                val penultimate = i - 1
                if (child.visibility == View.GONE) {
                    child.setTag(R.id.row_index_key, -1)
                    continue
                }
                val lp = child.layoutParams
                var startMargin = 0
                var endMargin = 0
                if (lp is MarginLayoutParams) {
                    startMargin = MarginLayoutParamsCompat.getMarginStart(lp)
                    endMargin = MarginLayoutParamsCompat.getMarginEnd(lp)
                }
                childEnd = childStart + startMargin + child.measuredWidth
                if (!isSingleLine && (childEnd > maxChildEnd)) {
                    childStart = paddingStart
                    childTop = childBottom
                    if (row == maxRow && (penultimate) > 0) {
                        val showChip = (getChildAt(penultimate) as Chip)
                        showChip.text = CHIP_MORE_TITLE
                        showChip.isCheckable = false
                        showChip.setOnClickListener {
                            maxRow = Int.MAX_VALUE
                            setChips(ArrayList(lastChipsList))
                        }
                    }
                    row++
                }
                child.visibility = if (row > maxRow) View.GONE else View.VISIBLE
                child.setTag(R.id.row_index_key, row - 1)
                childEnd = childStart + startMargin + child.measuredWidth
                childBottom = childTop + child.measuredHeight
                if (isRtl) {
                    child.layout(
                        maxChildEnd - childEnd,
                        childTop,
                        maxChildEnd - childStart - startMargin,
                        childBottom
                    )
                } else {
                   child.layout(childStart + startMargin, childTop, childEnd, childBottom)
                }
                childStart += startMargin + endMargin + child.measuredWidth + ITEM_SPACING
            }
        }
    }
}