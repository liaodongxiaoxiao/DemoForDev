package com.karl.demo.demo

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.tabs.TabLayoutMediator
import com.karl.demo.R
import com.karl.kotlin.extension.inflateNullRoot
import com.karl.kotlin.extension.log
import kotlinx.android.synthetic.main.activity_demo_lottery.*
import kotlinx.android.synthetic.main.fragment_demo_lottery_red.*

class LotteryDemoActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_lottery)
        vp_page.adapter = PagerAdapter(this)
        TabLayoutMediator(tl_title, vp_page) { tab, position ->
            if (position==0){
                tab.text="红球"
            }else{
                tab.text="蓝球"
            }
        }.attach()

    }


}

class RedFragment : Fragment(R.layout.fragment_demo_lottery_red) {
    private val selectedRedAdapter by lazy { SelectedBallAdapter(isRed = true) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_red.layoutManager = getLayoutManager()
        val redAdapter = BallAdapter(true)
        redAdapter.setOnBallSelectedListener { _, selected ->
            setRedBallSelectedInfo(selected)
        }
        rv_red.adapter = redAdapter

        rv_red_selected.layoutManager = getLayoutManager()
        rv_red_selected.adapter = selectedRedAdapter
    }


    private fun getLayoutManager(): FlexboxLayoutManager {
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        return layoutManager
    }

    private fun setRedBallSelectedInfo(selected: MutableList<Ball>) {
        selected.log()
        val data: MutableList<String> = mutableListOf()
        var qu1 = 0
        var qu2 = 0
        var qu3 = 0
        var dan = 0
        var shuang = 0
        var l0 = 0
        var l1 = 0
        var l2 = 0



        for (ball in selected) {
            data.add(ball.num)
            val ballInt = ball.num.toInt()
            when {
                ballInt < 12 -> {
                    qu1 += 1
                }
                ballInt in 12..22 -> {
                    qu2 += 1
                }
                else -> {
                    qu3 += 1
                }
            }

            if (ballInt % 2 == 0) {
                shuang += 1
            } else {
                dan += 1
            }

            when {
                ballInt % 3 == 0 -> {
                    l0 += 1
                }
                ballInt % 3 == 1 -> {
                    l1 += 1
                }
                else -> {
                    l2 += 1
                }
            }
        }

        data.sortBy { it }
        selectedRedAdapter.setData(data)
        selectedRedAdapter.notifyDataSetChanged()

        tv_has_red.text =
            when {
                selected.isEmpty() -> {
                    "请选择6个红色号码"
                }
                selected.size < 6 -> {
                    "您已经选择${selected.size}个红色号码，还可以选择${6 - selected.size}"
                }
                else -> {
                    """
您选择的红色号码分析如下：
奇偶比为：$dan:$shuang
三区比为：$qu1:$qu2:$qu3
三路比为：$l0:$l1:$l2
                    """.trimIndent()
                }
            }
    }

}

class BlueFragment : Fragment(R.layout.fragment_demo_lottery_blue) {

}


/**
 * 适配器
 */
class PagerAdapter(context: FragmentActivity) : FragmentStateAdapter(context) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
      return  if (position==0){
            RedFragment()
        }else{
            BlueFragment()
        }
    }
}


class BallAdapter(private val isRed: Boolean = false) :
    RecyclerView.Adapter<BallViewHolder>() {
    private val data: MutableList<Ball> = mutableListOf()
    private val ballSelected: MutableList<Ball> = mutableListOf()

    fun interface OnBallSelectedListener {
        fun onBallSelected(isRed: Boolean, selected: MutableList<Ball>)
    }

    private var _listener: OnBallSelectedListener? = null
    fun setOnBallSelectedListener(listener: OnBallSelectedListener) {
        _listener = listener
    }

    init {
        val max = if (isRed) 33 else 16
        for (i in 1..max) {
            data.add(Ball(i.toBall()))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BallViewHolder {
        return BallViewHolder(parent.inflateNullRoot(R.layout.item_demo_ball))
    }

    override fun onBindViewHolder(holder: BallViewHolder, position: Int) {
        val ball = data[position]
        holder.ball.text = ball.num
        holder.itemView.setOnClickListener {
            if (ball.canChecked) {
                ball.isChecked = !ball.isChecked
                notifyItemChanged(position)
                if (ball.isChecked) {
                    ballSelected.add(ball)
                } else {
                    ballSelected.remove(ball)
                }
                _listener?.onBallSelected(isRed, ballSelected)
            }
        }
        if (isRed) {
            if (ball.canChecked) {
                if (ball.isChecked) {
                    holder.ball.setBackgroundResource(R.drawable.ic_icon_circle_red)
                    holder.ball.setTextColor(Color.WHITE)
                } else {
                    holder.ball.setBackgroundResource(R.drawable.ic_icon_circle_red_line)
                    holder.ball.setTextColor(Color.parseColor("#C1272D"))
                }
            } else {
                //
                holder.ball.setBackgroundResource(R.drawable.ic_icon_circle_red_line_disable)
                holder.ball.setTextColor(Color.parseColor("#D7766A"))
            }
        } else {
            if (ball.canChecked) {
                if (ball.isChecked) {
                    holder.ball.setBackgroundResource(R.drawable.ic_icon_circle_blue)
                    holder.ball.setTextColor(Color.WHITE)
                } else {
                    holder.ball.setBackgroundResource(R.drawable.ic_icon_circle_blue_line)
                    holder.ball.setTextColor(Color.parseColor("#2eb2ef"))
                }
            } else {
                //
                holder.ball.setBackgroundResource(R.drawable.ic_icon_circle_blue_line_disable)
                holder.ball.setTextColor(Color.parseColor("#74CBF4"))
            }
        }
    }

    override fun getItemCount(): Int = data.size

}

class SelectedBallAdapter(private val isRed: Boolean = false) :
    RecyclerView.Adapter<BallViewHolder>() {
    private val _data: MutableList<String> = mutableListOf()

    fun setData(data: MutableList<String>) {
        _data.clear()
        _data.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BallViewHolder {
        return BallViewHolder(parent.inflateNullRoot(R.layout.item_demo_ball))
    }

    override fun onBindViewHolder(holder: BallViewHolder, position: Int) {
        val ball = _data[position]
        holder.ball.text = ball

        if (isRed) {
            holder.ball.setBackgroundResource(R.drawable.ic_icon_circle_red)
            holder.ball.setTextColor(Color.WHITE)
        } else {
            holder.ball.setBackgroundResource(R.drawable.ic_icon_circle_blue)
            holder.ball.setTextColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int = _data.size

}

class BallViewHolder(root: View) : RecyclerView.ViewHolder(root) {
    val ball: TextView = root.findViewById(R.id.tv_ball)
}

/**
 * entity
 */
data class Ball(val num: String, var isChecked: Boolean = false, var canChecked: Boolean = true)

/**
 * 扩展方法
 */
fun Int.toBall(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}