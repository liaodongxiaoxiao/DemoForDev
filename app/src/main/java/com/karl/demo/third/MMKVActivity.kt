package com.karl.demo.third

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.ActivityMmkvBinding
import com.karl.demo.extesion.hideSoftKeyboard
import com.karl.kotlin.extension.inflate
import com.karl.kotlin.extension.log
import com.karl.kotlin.extension.toast
import com.tencent.mmkv.MMKV
import java.util.*


class MMKVActivity : BaseActivity<ActivityMmkvBinding>(ActivityMmkvBinding::inflate) {

    private lateinit var keywordsAdapter: KeywordAdapter
    private val key: String = "HISTORY"
    val kv = MMKV.defaultMMKV()
    private val gson = GsonBuilder().create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAndBindEvent()
        getHistory()
    }

    private fun getHistory() {
        val data = queryHistory()
        keywordsAdapter.setData(data)
    }

    private fun queryHistory(): MutableList<HistoryKeywords>? {
        //  var data: MutableList<HistoryKeywords>? = null
        //withContext(Dispatchers.IO)
        kv?.let {
            val historyStr = it.decodeString(key)
            if (TextUtils.isEmpty(historyStr)) {
                "无历史数据".log()
            } else {
                return gson.fromJson(
                    historyStr,
                    object : TypeToken<MutableList<HistoryKeywords>>() {}.type
                )
            }
        }

        return null
    }

    private fun initAndBindEvent() {

        binding.etSearch.setOnEditorActionListener { _, actionId, event ->
            //触发搜索方法
            if ((actionId == 0 || actionId == 3) && event != null) {
                hideSoftKeyboard()
                toSearch(binding.etSearch.text.toString())
                true
            }
            false
        }
        binding.btnRefresh.setOnClickListener {
            getHistory()
        }
        binding.btnClear.setOnClickListener {
            kv?.removeValueForKey(key)
            getHistory()
        }

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START

        keywordsAdapter = KeywordAdapter()
        binding.rvList.apply {
            this.layoutManager = layoutManager
            this.adapter = keywordsAdapter
        }

    }

    private fun toSearch(keyword: String) {
        toast("正在搜索...")
        saveKeyword(keyword)
        getHistory()

    }

    private fun saveKeyword(keyword: String) {
        val data = queryHistory()
        if (data == null || data.isEmpty()) {
            val result: MutableList<HistoryKeywords> = mutableListOf()
            result.add(HistoryKeywords(keyword, Date().time))
            kv?.encode(key, gson.toJson(result))
        } else {
            var has = false
            for (d in data) {
                if (keyword == d.keyword) {
                    d.time = Date().time
                    has = true
                }
            }
            if (has) {
                kv?.encode(key, gson.toJson(data.sortedByDescending { it.time }))
            } else {
                if (data.size >= 10) {
                    val newData = data.subList(0, 9)
                    newData.add(HistoryKeywords(keyword, Date().time))
                    kv?.encode(key, gson.toJson(newData.sortedByDescending { it.time }))
                } else {
                    data.add(HistoryKeywords(keyword, Date().time))
                    kv?.encode(key, gson.toJson(data.sortedByDescending { it.time }))
                }
            }
        }
    }
}

class KeywordAdapter : RecyclerView.Adapter<KeywordAdapter.KeywordViewHolder>() {

    private val _data: MutableList<HistoryKeywords> = mutableListOf()

    fun setData(data: MutableList<HistoryKeywords>?) {
        _data.clear()
        data?.let {
            _data.addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        return KeywordViewHolder(parent.inflate(R.layout.item_3_flexbox))
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        holder.tv.text = _data[position].keyword
        holder.itemView.setOnClickListener {
            //_listener?.onItemClick(data[position])
        }
    }

    override fun getItemCount(): Int = _data.size

    inner class KeywordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tv)
    }
}

data class HistoryKeywords(var keyword: String, var time: Long)