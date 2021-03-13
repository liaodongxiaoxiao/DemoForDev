package com.karl.demo.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.widget.PopupWindowCompat
import com.karl.demo.R
import kotlinx.android.synthetic.main.activity_android_pop_window.*

class PopWindowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_pop_window)

        btn_more.setOnClickListener {
            val mLayoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val menuView = mLayoutInflater.inflate(
                R.layout.layout_pop, null, false) as ViewGroup
            val pw = PopupWindow(menuView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true)

            /*menuView.setPadding(0, 0, 0, 0);
            menuView.setBackgroundDrawable(getResources().getDrawable(R.drawable.pop_bg))*/
            pw.isOutsideTouchable = false

            pw.contentView.measure(makeDropDownMeasureSpec(pw.width),
                makeDropDownMeasureSpec(pw.height))

            val offsetY = -(pw.contentView.measuredHeight + btn_more.height)
            PopupWindowCompat.showAsDropDown(pw, btn_more, -10, offsetY, Gravity.START)

            val tv: TextView = menuView.findViewById(R.id.tv)
            tv.setOnClickListener {
                pw.dismiss()
            }
        }

    }

    private fun makeDropDownMeasureSpec(measureSpec: Int): Int {
        val mode: Int = if (measureSpec == ViewGroup.LayoutParams.WRAP_CONTENT) {
            View.MeasureSpec.UNSPECIFIED
        } else {
            View.MeasureSpec.EXACTLY
        }
        return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measureSpec), mode)
    }
}