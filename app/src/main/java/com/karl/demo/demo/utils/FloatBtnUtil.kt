package com.karl.demo.demo.utils

import android.app.Activity
import android.graphics.Point
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ScrollView
import com.karl.kotlin.extension.log

/**
 * https://blog.csdn.net/weixin_41392105/article/details/105010624
 */
class FloatBtnUtil(private val mcontext: Activity) {
    private var listener: OnGlobalLayoutListener? = null
    private var root: View? = null
    fun setFloatView(root: View, floatview: View) {
        this.root = root //视图根节点 floatview // 需要显示在键盘上的View组件
        listener = OnGlobalLayoutListener {
            val r = Rect()
            mcontext.window.decorView.getWindowVisibleDisplayFrame(r)
            val windowHeight = r.bottom - r.top
            "windows height:${windowHeight}".log()
            "screen height:${height}".log()

            val heightDifference = height - (r.bottom - r.top)
            val isKeyboardShowing = heightDifference > height / 3
            if (isKeyboardShowing) {
                /*floatview.animate().translationY((-windowHeight/4).toFloat()).setDuration(0)
                    .start()*/
                if (root is ScrollView){
                    (root as ScrollView).scrollY = 100
                }
            } else {
                floatview.animate().translationY(0f).start()
            }
        }
        root.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    fun clearFloatView() {
        if (listener != null && root != null) root!!.viewTreeObserver.removeOnGlobalLayoutListener(
            listener
        )
    }

    companion object {
        private var height = 0
    }

    init {
        if (height == 0) {
            val defaultDisplay = mcontext.windowManager.defaultDisplay
            val point = Point()
            defaultDisplay.getSize(point)
            height = point.y
        }
    }
}