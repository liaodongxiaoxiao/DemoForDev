package com.karl.demo.tools

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.ActivityToolsOpacityBinding
import com.karl.kotlin.extension.inflate

/**
 * 不透明度
 */
class OpacityActivity :
    BaseActivity<ActivityToolsOpacityBinding>(ActivityToolsOpacityBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.rv.apply {
            layoutManager = LinearLayoutManager(this@OpacityActivity)
            adapter = OpacityAdapter()
        }
    }

}

class OpacityAdapter : RecyclerView.Adapter<OpacityAdapter.OpacityViewHolder>() {
    inner class OpacityViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tvKey: TextView = item.findViewById(R.id.tv_key)
        val tvValue: TextView = item.findViewById(R.id.tv_value)
    }

    val data = arrayOf<Pair<String, String>>(
        "100%" to " FF",
        "99%" to " FC",
        "98%" to " FA",
        "97%" to " F7",
        "96%" to " F5",
        "95%" to " F2",
        "94%" to " F0",
        "93%" to " ED",
        "92%" to " EB",
        "91%" to " E8",
        "90%" to " E6",
        "89%" to " E3",
        "88%" to " E0",
        "87%" to " DE",
        "86%" to " DB",
        "85%" to " D9",
        "84%" to " D6",
        "83%" to " D4",
        "82%" to " D1",
        "81%" to " CF",
        "80%" to " CC",
        "79%" to " C9",
        "78%" to " C7",
        "77%" to " C4",
        "76%" to " C2",
        "75%" to " BF",
        "74%" to " BD",
        "73%" to " BA",
        "72%" to " B8",
        "71%" to " B5",
        "70%" to " B3",
        "69%" to " B0",
        "68%" to " AD",
        "67%" to " AB",
        "66%" to " A8",
        "65%" to " A6",
        "64%" to " A3",
        "63%" to " A1",
        "62%" to " 9E",
        "61%" to " 9C",
        "60%" to " 99",
        "59%" to " 96",
        "58%" to " 94",
        "57%" to " 91",
        "56%" to " 8F",
        "55%" to " 8C",
        "54%" to " 8A",
        "53%" to " 87",
        "52%" to " 85",
        "51%" to " 82",
        "50%" to " 80",
        "49%" to " 7D",
        "48%" to " 7A",
        "47%" to " 78",
        "46%" to " 75",
        "45%" to " 73",
        "44%" to " 70",
        "43%" to " 6E",
        "42%" to " 6B",
        "41%" to " 69",
        "40%" to " 66",
        "39%" to " 63",
        "38%" to " 61",
        "37%" to " 5E",
        "36%" to " 5C",
        "35%" to " 59",
        "34%" to " 57",
        "33%" to " 54",
        "32%" to " 52",
        "31%" to " 4F",
        "30%" to " 4D",
        "29%" to " 4A",
        "28%" to " 47",
        "27%" to " 45",
        "26%" to " 42",
        "25%" to " 40",
        "24%" to " 3D",
        "23%" to " 3B",
        "22%" to " 38",
        "21%" to " 36",
        "20%" to " 33",
        "19%" to " 30",
        "18%" to " 2E",
        "17%" to " 2B",
        "16%" to " 29",
        "15%" to " 26",
        "14%" to " 24",
        "13%" to " 21",
        "12%" to " 1F",
        "11%" to " 1C",
        "10%" to " 1A",
        "9%" to " 17",
        "8%" to " 14",
        "7%" to " 12",
        "6%" to " 0F",
        "5%" to " 0D",
        "4%" to " 0A",
        "3%" to " 08",
        "2%" to " 05",
        "1%" to " 03",
        "0%" to " 00"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpacityViewHolder {
        return OpacityViewHolder(parent.inflate(R.layout.item_tools_opacity))
    }

    override fun onBindViewHolder(holder: OpacityViewHolder, position: Int) {
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E5F9F9"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
        }
        holder.tvKey.text = data[position].first
        holder.tvValue.text = data[position].second
    }

    override fun getItemCount(): Int = data.size
}