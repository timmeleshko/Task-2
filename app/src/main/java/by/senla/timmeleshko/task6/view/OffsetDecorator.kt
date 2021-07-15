package by.senla.timmeleshko.task6.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class OffsetDecorator(
    private val offset: Int
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPos = parent.getChildLayoutPosition(view)
        val divided = offset / 2
        if (itemPos > 0) {
            if (itemPos % 2 == 0) {
                outRect.left = divided
                outRect.right = offset
            } else {
                outRect.left = offset
                outRect.right = divided
            }
            outRect.bottom = offset
        } else {
            outRect.offset(offset, offset)
        }
    }
}