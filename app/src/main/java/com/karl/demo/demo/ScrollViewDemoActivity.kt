package com.karl.demo.demo

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.karl.demo.BaseActivity
import com.karl.demo.databinding.ActivityDemoScrollViewBinding
import com.karl.kotlin.extension.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.registerEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar


class ScrollViewDemoActivity :
    BaseActivity<ActivityDemoScrollViewBinding>(ActivityDemoScrollViewBinding::inflate) {

    lateinit var key: Unregistrar

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //initFloatBtn()

        binding.apply {
            btnScroll.setOnClickListener {
                root.smoothScrollTo(0, 1000)
            }

            etSearch.setOnFocusChangeListener { v, hasFocus ->
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

            key = registerEventListener(
                this@ScrollViewDemoActivity,
                KeyboardVisibilityEventListener {
                    if (it) {
                        btn.visibility = View.VISIBLE
                        GlobalScope.launch {
                            delay(50)
                            root.smoothScrollTo(0, 1000)
                        }
                    } else {
                        btn.visibility = View.GONE
                    }
                })
        }


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