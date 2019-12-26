package ru.skillbranch.skillarticles.ui.custom.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.marginEnd
import androidx.core.view.marginRight

class ArticleSubmenu<V : View>(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<V>(context, attrs) {

    private var dependedViewTranslationY: Float = 0f
    private var dependedViewHeight: Float = 0f

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: V, directTargetChild: View, target: View, axes: Int, type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout, child: V, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        if( dependedViewTranslationY != 0f)
        child.translationX = dependedViewTranslationY*child.width/dependedViewHeight+child.marginEnd

    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: V,
        dependency: View
    ): Boolean {
        super.onDependentViewChanged(parent, child, dependency)
        if(dependency.translationY != dependedViewTranslationY){
            dependedViewTranslationY = dependency.translationY
            dependedViewHeight = dependency.height.toFloat()


        }
        val n = 0
        return false
    }


}