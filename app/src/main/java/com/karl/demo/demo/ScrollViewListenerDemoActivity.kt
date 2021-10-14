package com.karl.demo.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.karl.demo.R
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnScrollChangedListener
import kotlinx.android.synthetic.main.demo_activity_scroll_view_listener.*


class ScrollViewListenerDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_scroll_view_listener)

        nsv.viewTreeObserver.addOnScrollChangedListener(OnScrollChangedListener {
            val view: View = nsv.getChildAt(nsv.childCount - 1) as View
            val diff: Int = view.bottom - (nsv.height + nsv.scrollY)
            if (diff == 0) {
                btn_yes.isEnabled = true
            }
        })
        /**
        scroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
        @Override
        public void onScrollChanged() {
        View view = (View) scroll.getChildAt(scroll.getChildCount() - 1);

        int diff = (view.getBottom() - (scroll.getHeight() + scroll
        .getScrollY()));

        if (diff == 0) {
        getPlaylistFromServer("more");
        }
        }
        });
         *
         *
         *
         */
    }
}