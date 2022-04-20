package com.karl.demo.demo

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.ActivityDemoWeatherBinding
import com.karl.demo.demo.entity.DivisionEntity
import com.karl.demo.demo.entity.HourlyForecast
import com.karl.demo.demo.entity.WeatherEntity
import com.karl.demo.demo.vm.WeatherViewModel
import com.karl.kotlin.extension.inflate
import com.karl.kotlin.extension.inflateNullRoot
import com.karl.kotlin.extension.log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherDemoActivity :
    BaseActivity<ActivityDemoWeatherBinding>(ActivityDemoWeatherBinding::inflate) {
    @Inject
    lateinit var weatherViewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindEvent()
        observe()
    }

    private fun observe() {
        weatherViewModel.current.observe(this, {
            if (it != null) {
                binding.tvCurrent.text = "当前城市：${it.name}"
                weatherViewModel.getCityWeather(it.value)
            }
        })
        weatherViewModel.divisions.observe(this, {
            "size:${it.size}".log()
            val divisionAdapter = DivisionAdapter(it)
            divisionAdapter.setOnItemClickListener { it ->
                weatherViewModel.saveDivision(it)
            }
            /*rv_list.adapter = divisionAdapter
            rv_list.layoutManager = LinearLayoutManager(this)*/

        })

        weatherViewModel.weatherInfo.observe(this, {
            //tv_info.text = it.toString()
            setWeatherInfo(it)
        })
    }

    private fun setWeatherInfo(weather: WeatherEntity) {
        binding.rvHours.apply {
            this.layoutManager =
                LinearLayoutManager(this@WeatherDemoActivity, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = HoursForecastAdapter(weather.hourly_forecast)
        }

    }

    private fun bindEvent() {
        /* sv_input.setOnQueryTextListener(object :
             SearchView.OnQueryTextListener {
             override fun onQueryTextSubmit(query: String?): Boolean {
                 query?.let {
                     it.log()
                     weatherViewModel.getDivisionByName(it)
                     return true
                 }
                 return false
             }

             override fun onQueryTextChange(newText: String?): Boolean {
                 return false
             }
         })*/
    }
}

class HoursForecastAdapter(private val data: List<HourlyForecast>) :
    RecyclerView.Adapter<HoursForecastAdapter.HoursForecastViewHolder>() {

    class HoursForecastViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tvHours: TextView = item.findViewById(R.id.tv_hours)
        val tvWeather: TextView = item.findViewById(R.id.tv_weather)
        val tvTmp: TextView = item.findViewById(R.id.tv_tmp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursForecastViewHolder {
        return HoursForecastViewHolder(parent.inflateNullRoot(R.layout.item_weather_hours))
    }

    override fun onBindViewHolder(holder: HoursForecastViewHolder, position: Int) {
        val entity = data[position]
        holder.apply {
            tvHours.text = entity.hour.getTime()
            tvWeather.text = entity.info
            tvTmp.text = "${entity.temperature}℃"
        }
    }

    override fun getItemCount(): Int = data.size
}

fun String.getTime(): String {
    val hours = this.toInt()
    var end: String
    var newHours: Int
    if (hours > 12) {
        newHours = hours - 12
        end = "PM"
    } else {
        newHours = hours
        end = "AM"
    }

    return if (newHours < 10) "0$newHours$end" else "$newHours$end"
}

class DivisionAdapter(val data: MutableList<DivisionEntity>) :
    RecyclerView.Adapter<DivisionAdapter.DivisionViewHolder>() {

    private var _click: (DivisionEntity) -> Unit = {}

    fun setOnItemClickListener(click: (DivisionEntity) -> Unit) {
        _click = click
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DivisionViewHolder {
        return DivisionViewHolder(parent.inflate(R.layout.item_tools_opacity))
    }

    override fun onBindViewHolder(holder: DivisionViewHolder, position: Int) {
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E5F9F9"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
        }
        holder.tvKey.text = data[position].name
        holder.tvValue.text = data[position].value
        holder.itemView.setOnClickListener {
            _click.invoke(data[position])
        }
    }

    override fun getItemCount(): Int = data.size

    inner class DivisionViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tvKey: TextView = item.findViewById(R.id.tv_key)
        val tvValue: TextView = item.findViewById(R.id.tv_value)
    }
}