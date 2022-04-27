package com.karl.demo.demo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.ActivityDemoPixelBinding

class DrawPixelActivity :
    BaseActivity<ActivityDemoPixelBinding>(ActivityDemoPixelBinding::inflate) {

    private lateinit var pixelAdapter: PixelItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        var bgColor:String
        val items = buildList<PixelItemBean> {
            for (i in 0..255) {
                val line = i / 16
                if (line % 2 == 0) {
                    if (i % 2 == 1) {
                        bgColor=("#CCCCCC")
                    } else {
                        bgColor=("#FFFFFF")
                    }
                } else {
                    if (i % 2 == 1) {
                        bgColor=("#FFFFFF")
                    } else {
                        bgColor=("#CCCCCC")
                    }
                }
                add(PixelItemBean(i,  ColorBean(255, 255, 255, "#FFFFFF", bgColor), false))
            }
        }
        Log.e("TAG", "item size:${items.size}")
        pixelAdapter = PixelItemAdapter(items.toMutableList())
        binding.gv.adapter = pixelAdapter
        pixelAdapter.notifyDataSetChanged()


    }
}

class PixelItemAdapter(private val data: MutableList<PixelItemBean>) : BaseAdapter() {
    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Any = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val backView: View
        val holder: PixelItemViewHolder
        if (convertView == null && parent != null) {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_pixel,
                parent,
                false
            )
            backView = view
            holder = PixelItemViewHolder(view)
            backView.tag = holder
        } else {
            backView = convertView!!
            holder = convertView.tag as PixelItemViewHolder
        }
        val entity = data[position]

        holder.item.setBackgroundColor(Color.parseColor(entity.color.bgColor))

        return backView
    }

    inner class PixelItemViewHolder(view: View) {
        val root: View = view.findViewById(R.id.root)
        val item: View = view.findViewById(R.id.v_item)
    }

}

data class ColorBean(
    val r: Long,
    val g: Long,
    val b: Long,
    val hexColor: String,
    var bgColor: String
)

data class PixelItemBean(val index: Int, val color: ColorBean, val selected: Boolean)
