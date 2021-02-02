package com.karl.demo.android

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.createDataStore
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.protobuf.InvalidProtocolBufferException
import com.karl.demo.KeywordsHistory
import com.karl.demo.R
import com.karl.demo.extesion.hideSoftKeyboard
import com.karl.kotlin.extension.inflate
import com.karl.kotlin.extension.log
import com.karl.kotlin.extension.toast
import kotlinx.android.synthetic.main.activity_preferences_data_store.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class PreferencesDataStoreActivity : AppCompatActivity() {

    private lateinit var keywordsAdapter: KeywordsAdapter

    private val dataStore: DataStore<KeywordsHistory> = createDataStore(
        fileName = "history_keywords.pd",
        serializer = KeywordsHistorySerializer
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences_data_store)
        initAndBindEvent()
        getHistory()

    }

    private fun getHistory() {
        GlobalScope.launch {
            val dataFlow: Flow<KeywordsHistory> = dataStore.data
                .catch { exception ->
                    // dataStore.data throws an IOException when an error is encountered when reading data
                    if (exception is IOException) {
                        Log.e("TAG", "Error reading sort order preferences.", exception)
                        emit(KeywordsHistory.getDefaultInstance())
                    } else {
                        throw exception
                    }
                }

            //val data:MutableList<KeywordsHistory> = mutableListOf()

            val list = dataFlow.toList()
            list.log()
            keywordsAdapter.setData(list)
            /* userPreferencesFlow.collect { it ->
                 it.log()
                 data.add(it)
             }*/

            //keywordsAdapter.setData(data)
        }
    }

    private fun initAndBindEvent() {
        tv_hi.text = dataStore.toString()

        et_search.setOnEditorActionListener { _, actionId, event ->
            //触发搜索方法
            if ((actionId == 0 || actionId == 3) && event != null) {
                hideSoftKeyboard()
                toSearch(et_search.text.toString())
                true
            }
            false
        }

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        rv_list.layoutManager = layoutManager

        keywordsAdapter = KeywordsAdapter()

        rv_list.adapter = keywordsAdapter
    }

    private fun toSearch(keyword: String) {
        toast("正在搜索...")
        GlobalScope.launch {
            dataStore.updateData { keywords ->
                keywords.toBuilder().setKeywords(keyword).setUpdateTime(Date().time).build()
            }
        }

    }

    object KeywordsHistorySerializer : Serializer<KeywordsHistory> {
        override val defaultValue: KeywordsHistory
            get() = KeywordsHistory.getDefaultInstance()

        override fun readFrom(input: InputStream): KeywordsHistory {
            try {
                return KeywordsHistory.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

        override fun writeTo(t: KeywordsHistory, output: OutputStream) = t.writeTo(output)

    }

}

class KeywordsAdapter : RecyclerView.Adapter<KeywordsAdapter.KeywordsViewHolder>() {

    private val _data: MutableList<KeywordsHistory> = mutableListOf()

    fun setData(data: List<KeywordsHistory>?) {
        _data.clear()
        data?.let {
            _data.addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordsViewHolder {
        return KeywordsViewHolder(parent.inflate(R.layout.item_3_flexbox))
    }

    override fun onBindViewHolder(holder: KeywordsViewHolder, position: Int) {
        holder.tv.text = _data[position].keywords
        holder.itemView.setOnClickListener {
            //_listener?.onItemClick(data[position])
        }
    }

    override fun getItemCount(): Int = _data.size

    inner class KeywordsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tv)
    }
}
