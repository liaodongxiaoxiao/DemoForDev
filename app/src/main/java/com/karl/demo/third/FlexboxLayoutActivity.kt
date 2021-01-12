package com.karl.demo.third

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.karl.demo.R
import com.karl.kotlin.extension.inflate
import com.karl.kotlin.extension.toast
import kotlinx.android.synthetic.main.activity_3_flexbox_layout.*


/**
 * Google Flexbox layout库
 * https://github.com/google/flexbox-layout
 *
 */
class FlexboxLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3_flexbox_layout)

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        rv_demo.layoutManager = layoutManager

        val flexBoxAdapter = FlexBoxAdapter()
        flexBoxAdapter.setOnItemClickListener(object : FlexBoxAdapter.OnItemClickListener {
            override fun onItemClick(str: String) {
                toast(str)
            }
        })
        rv_demo.adapter = flexBoxAdapter
    }
}

class FlexBoxAdapter : RecyclerView.Adapter<FlexBoxAdapter.FlexBoxViewHolder>() {
    private val data: MutableList<String> = mutableListOf()
    private var _listener: OnItemClickListener? = null

    init {
        data.add("新冠疫情")
        data.add("2021春运")
        data.add("宜宾公交自燃")
        data.add("世界杯")
        data.add("石家庄疫情")
        data.add("特朗普社交平台账号被禁")
        data.add("CBA北京队欲罢赛")
    }

    inner class FlexBoxViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlexBoxViewHolder {
        return FlexBoxViewHolder(parent.inflate(R.layout.item_3_flexbox))
    }

    override fun onBindViewHolder(holder: FlexBoxViewHolder, position: Int) {
        holder.tv.text = data[position]
        holder.itemView.setOnClickListener {
            _listener?.onItemClick(data[position])
        }
    }

    override fun getItemCount(): Int = data.size

    fun setOnItemClickListener(listener: OnItemClickListener) {
        _listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(str: String)
    }
}