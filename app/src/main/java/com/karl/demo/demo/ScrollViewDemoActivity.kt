package com.karl.demo.demo

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.karl.demo.R
import com.karl.kotlin.extension.log
import kotlinx.android.synthetic.main.activity_demo_scroll_view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.registerEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar


class ScrollViewDemoActivity : AppCompatActivity() {

    lateinit var key: Unregistrar

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_scroll_view)
        //initFloatBtn()

        btn_scroll.setOnClickListener {
            root.smoothScrollTo(0, 1000)
        }

        et_search.setOnFocusChangeListener { v, hasFocus ->
            " et_search hasFocus :$hasFocus".log()
            /*if (hasFocus) {
                "scroll ...".log()
                btn.visibility = View.VISIBLE

                //root.fullScroll(View.FOCUS_DOWN)
            }else{
                btn.visibility = View.GONE
            }*/
        }

        root.setOnScrollChangeListener { _, scrollX, scrollY, oldScrollX, oldScrollY ->
            "scrollX:$scrollX scrollY:$scrollY oldScrollX:$oldScrollX oldScrollY:$oldScrollY".log()
        }

        key =  registerEventListener(
            this,
            KeyboardVisibilityEventListener {
                if (it){
                    btn.visibility = View.VISIBLE
                    GlobalScope.launch {
                        delay(50)
                        root.smoothScrollTo(0, 1000)
                    }
                }else{
                    btn.visibility = View.GONE
                }
            })


    }

    override fun onDestroy() {
        super.onDestroy()
        key.unregister()
    }

    /*private fun initFloatBtn() {
        val floatBtnUtil = FloatBtnUtil(this)
        floatBtnUtil.setFloatView(root, btn)
    }*/
}