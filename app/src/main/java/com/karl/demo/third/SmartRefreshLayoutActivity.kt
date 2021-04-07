package com.karl.demo.third

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karl.demo.R
import com.karl.kotlin.extension.inflate
import kotlinx.android.synthetic.main.activity_3rd_smart_refresh_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class SmartRefreshLayoutActivity : AppCompatActivity() {

    val adapter = OpacityAdapter()
    var pageNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3rd_smart_refresh_layout)

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        srl.setOnLoadMoreListener {
            pageNumber += 1
            getDataFromNet()
        }

        srl.setOnRefreshListener {
            pageNumber = 1
            getDataFromNet()
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
                    srl.setEnableLoadMore(false)
                } else {
                    srl.setNoMoreData(true)
                }
            }

            srl.finishLoadMore()
            srl.finishRefresh()
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
        return OpacityViewHolder(parent.inflate(R.layout.item_tools_opacity))
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