package com.roman.core

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomItemDecorationHorizontal(private val draw: Drawable) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.adapter?.let { adapter ->
            outRect.right = when (parent.getChildAdapterPosition(view)) {
                RecyclerView.NO_POSITION,
                adapter.itemCount - 1 -> 0

                else -> draw.intrinsicWidth
            }
        }
    }
}