package com.karl.demo.third

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.Activity3rdBannerBinding
import com.karl.kotlin.extension.inflate
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener
import com.youth.banner.transformer.AlphaPageTransformer
import com.youth.banner.transformer.ScaleInTransformer
import com.youth.banner.util.LogUtils

class BannerActivity : BaseActivity<Activity3rdBannerBinding>(Activity3rdBannerBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_3rd_banner)

        binding.banner.addBannerLifecycleObserver(this)//添加生命周期观察者
            .setAdapter(TestBannerAdapter(getData()))
            .setIndicator(CircleIndicator(this))
            .setLoopTime(3100)
            .addPageTransformer(AlphaPageTransformer())
            .addPageTransformer(ScaleInTransformer())
            .addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {

                }

                override fun onPageScrollStateChanged(state: Int) {

                }

            })
        //.addPageTransformer(DepthPageTransformer())


    }

    private fun getData(): List<DataBean> {
        return buildList<DataBean> {
            add(DataBean("https://uploadfile.huiyi8.com/2018/af/b2/4d/89/381783.jpg"))
            add(DataBean("https://uploadfile.huiyi8.com/22/ed/7c/bd/070353df53c446d3e9c36acb90aff89a.jpg"))
            add(DataBean("https://uploadfile.huiyi8.com/22/0d/3c/94/656263efb2e48946c9fa2a43d253edf8.jpg"))
        }
    }

    inner class TestBannerAdapter(data: List<DataBean>) :
        BannerAdapter<DataBean, BannerViewHolder>(data) {
        override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
            return BannerViewHolder(parent.inflate(R.layout.item_3rd_banner))
        }

        override fun onBindView(
            holder: BannerViewHolder?,
            data: DataBean?,
            position: Int,
            size: Int
        ) {
            data?.let {
                holder?.bind(data, position)
            }
        }

    }

    inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val iv: ImageView = view.findViewById(R.id.iv)
        private val pr: ProgressBar = view.findViewById(R.id.pb)

        fun bind(data: DataBean, position: Int) {
            iv.load(data.url)
            pr.setProgress(0)
        }

        fun startProgress() {
            for (i in 0 until 101) {
                LogUtils.e("i:$i")
                pr.setProgress(i)
            }
        }

    }

    inner class DataBean(
        var url: String
    )
}