package ru.skillbranch.skillarticles.extensions

import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView

fun NestedScrollView.setMarginOptionally(
    left: Int = -1,
    top: Int = -1,
    right: Int = -1,
    bottom: Int = -1
) {
    if (bottom >= 0) (layoutParams as FrameLayout.LayoutParams).bottomMargin = bottom
    if (left >= 0) (layoutParams as FrameLayout.LayoutParams).leftMargin = left
    if (top >= 0) (layoutParams as FrameLayout.LayoutParams).topMargin = top
    if (right >= 0) (layoutParams as FrameLayout.LayoutParams).rightMargin = right

}

fun View.setPaddingOptionally(
    left: Int = paddingLeft,
    right: Int = paddingRight,
    top: Int = paddingTop,
    bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}