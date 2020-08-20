package ru.skillbranch.skillarticles.extensions

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.widget.NestedScrollView
import androidx.navigation.NavDestination
import com.google.android.material.bottomnavigation.BottomNavigationView

fun View.setMarginOptionally(
    left: Int = marginLeft,
    top: Int = marginTop,
    right: Int = marginRight,
    bottom: Int = marginBottom
) {

    (layoutParams as? ViewGroup.MarginLayoutParams)?.run {
        leftMargin = left
        topMargin= top
        rightMargin = right
        bottomMargin = bottom

    }

}

fun View.setPaddingOptionally(
    left: Int = paddingLeft,
    right: Int = paddingRight,
    top: Int = paddingTop,
    bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}


fun BottomNavigationView.selectDestination(destination: NavDestination) {
    val menuItem = menu.findItem(destination.id)
    menuItem?.isChecked = true
}

