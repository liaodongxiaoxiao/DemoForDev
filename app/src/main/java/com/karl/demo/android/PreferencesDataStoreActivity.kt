package com.karl.demo.android

//import androidx.datastore.createDataStore
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.datastore.core.Serializer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.karl.demo.BaseActivity
import com.karl.demo.KeywordsHistory
import com.karl.demo.R
import com.karl.demo.databinding.ActivityPreferencesDataStoreBinding
import com.karl.demo.extesion.hideSoftKeyboard
import com.karl.kotlin.extension.inflate
import com.karl.kotlin.extension.log
import com.karl.kotlin.extension.toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.OutputStream

class PreferencesDataStoreActivity :
    BaseActivity<ActivityPreferencesDataStoreBinding>(ActivityPreferencesDataStoreBinding::inflate) {

    private lateinit var keywordsAdapter: KeywordsAdapter

    /*private val dataStore: DataStore<KeywordsHistory> = createDataStore(
        fileName = "history_keywords.pd",
        serializer = KeywordsHistorySerializer
    )*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_preferences_data_store)
        initAndBindEvent()
        getHistory()

    }

    private fun getHistory() {
        GlobalScope.launch {
            /*val dataFlow: Flow<KeywordsHistory> = dataStore.data
                .catch { exception ->
                    // dataStore.data throws an IOException when an error is encountered when reading data
                    if (exception is IOException) {
                        Log.e("TAG", "Error reading sort order preferences.", exception)
                        emit(KeywordsHistory.getDefaultInstance())
                    } else {
                        throw exception
                    }
                }*/

            //val data:MutableList<KeywordsHistory> = mutableListOf()

            //val list = dataFlow.toList()
            val list = emptyList<KeywordsHistory>()
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
        //tv_hi.text = dataStore.toString()
        binding.apply {
            etSearch.setOnEditorActionListener { _, actionId, event ->
                //触发搜索方法
                if ((actionId == 0 || actionId == 3) && event != null) {
                    hideSoftKeyboard()
                    toSearch(etSearch.text.toString())
                    true
                }
                false
            }

            val layoutManager = FlexboxLayoutManager(this@PreferencesDataStoreActivity)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.justifyContent = JustifyContent.FLEX_START
            rvList.layoutManager = layoutManager

            keywordsAdapter = KeywordsAdapter()

            rvList.adapter = keywordsAdapter
        }

    }

    private fun toSearch(keyword: String) {
        toast("正在搜索...")
        GlobalScope.launch {
            /*dataStore.updateData { keywords ->
                keywords.toBuilder().setKeywords(keyword).setUpdateTime(Date().time).build()
            }*/
        }

    }

    object KeywordsHistorySerializer : Serializer<KeywordsHistory> {
        override val defaultValue: KeywordsHistory
            get() = KeywordsHistory.getDefaultInstance()

        override suspend fun readFrom(input: InputStream): KeywordsHistory {
            TODO("Not yet implemented")
        }

        override suspend fun writeTo(t: KeywordsHistory, output: OutputStream) {
            TODO("Not yet implemented")
        }

        /*override fun readFrom(input: InputStream): KeywordsHistory {
            try {
                return KeywordsHistory.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

        override fun writeTo(t: KeywordsHistory, output: OutputStream) = t.writeTo(output)
*/
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
