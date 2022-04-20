package com.karl.demo.third

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.Activity3rdSmartRefreshLayoutBinding
import com.karl.kotlin.extension.inflate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class SmartRefreshLayoutActivity :
    BaseActivity<Activity3rdSmartRefreshLayoutBinding>(Activity3rdSmartRefreshLayoutBinding::inflate) {

    val adapter = OpacityAdapter()
    var pageNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnReset.setOnClickListener {

            //srl.refreshFooter?.view?.visibility = View.GONE
            binding.srl.apply {
                setEnableLoadMore(true)
                setNoMoreData(false)
                //srl.autoRefresh()
                closeHeaderOrFooter()
            }

            pageNumber = 1
            getDataFromNet()
        }



        binding.rv.apply {
            this.layoutManager = LinearLayoutManager(this@SmartRefreshLayoutActivity)
            this.adapter = adapter
        }
        binding.srl.apply {
            setOnLoadMoreListener {
                //srl.refreshFooter?.view?.visibility = View.VISIBLE
                pageNumber += 1
                getDataFromNet()
            }

            setOnRefreshListener {
                this.setNoMoreData(false)
                pageNumber = 1
                getDataFromNet()
            }
        }


        pageNumber = 1
        getDataFromNet()
    }

    private fun getDataFromNet() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            val data = makeData(pageNumber)
            if (pageNumber == 1) {
                adapter.setData(data.data)
            } else {
                adapter.addData(data.data)
            }

            adapter.notifyDataSetChanged()

            if (!data.hasNext) {
                if (pageNumber == 1) {
                    binding.srl.setEnableLoadMore(false)
                } else {
                    binding.srl.setNoMoreData(true)
                }
            }

            binding.srl.finishLoadMore()
            binding.srl.finishRefresh()
        }
    }

    private fun makeData(pageNumber: Int): PageData {
        val data: MutableList<Pair<Int, Int>> = mutableListOf()

        for (i in 1..10) {
            data.add(Pair(Random(System.nanoTime()).nextInt(), Random(System.nanoTime()).nextInt()))
        }

        return PageData(pageNumber != 3, data)
    }

}

data class PageData(val hasNext: Boolean, val data: MutableList<Pair<Int, Int>>)

class OpacityAdapter : RecyclerView.Adapter<OpacityAdapter.OpacityViewHolder>() {
    inner class OpacityViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tvKey: TextView = item.findViewById(R.id.tv_key)
        val tvValue: TextView = item.findViewById(R.id.tv_value)
    }

    val _data: MutableList<Pair<Int, Int>> = mutableListOf()

    fun setData(data: MutableList<Pair<Int, Int>>) {
        _data.clear()
        addData(data)
    }

    fun addData(data: MutableList<Pair<Int, Int>>) {
        _data.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpacityViewHolder {
        return OpacityViewHolder(parent.inflate(R.layout.item_srl_opacity))
    }

    override fun onBindViewHolder(holder: OpacityViewHolder, position: Int) {
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E5F9F9"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
        }
        holder.tvKey.text = _data[position].first.toString()
        holder.tvValue.text = _data[position].second.toString()
    }

    override fun getItemCount(): Int = _data.size
}

fun RecyclerView.smoothSnapToPosition(
    position: Int,
    snapMode: Int = LinearSmoothScroller.SNAP_TO_START
) {
    val smoothScroller = object : LinearSmoothScroller(this.context) {
        override fun getVerticalSnapPreference(): Int = snapMode
        override fun getHorizontalSnapPreference(): Int = snapMode
    }
    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}