package com.karl.demo.extesion

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideSoftKeyboard(){
    val view: View? = this.currentFocus
    if (view != null) {
        val inputMethodManager: InputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

}