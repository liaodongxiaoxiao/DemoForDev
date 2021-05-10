package com.karl.demo.demo.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class WeatherEntity(
    val alert: List<Alert>,
    val area: List<List<String>>,
    val hourly_forecast: List<HourlyForecast>,
    val life: Life,
    val pm25: Pm25,
    val realtime: Realtime,
    val weather: List<WeatherX>
)

data class Alert(
    val alarmPic1: String,
    val alarmPic2: String,
    val alarmTp1: String,
    val alarmTp2: String,
    val content: String,
    val originUrl: String,
    val pubTime: String,
    val type: Int
)

data class HourlyForecast(
    val hour: String,
    val info: String,
    val temperature: String,
    val wind_direct: String,
    val wind_speed: String
)

data class Life(
    val date: String,
    val info: Info
)

data class Pm25(
    val advice: String,
    val aqi: Int,
    val chief: String,
    val co: String,
    val color: String,
    val level: Int,
    val no2: Int,
    val o3: Int,
    val pm10: Int,
    val pm25: Int,
    val quality: String,
    val so2: Int,
    val upDateTime: Long
)

data class Realtime(
    val dataUptime: String,
    val date: String,
    val feelslike_c: String,
    val mslp: String,
    val pressure: String,
    val time: String,
    val weather: Weather,
    val wind: Wind
)

data class WeatherX(
    val aqi: String,
    val date: String,
    val info: InfoX
)

data class Info(
    val chuanyi: List<String>,
    val daisan: List<String>,
    val diaoyu: List<String>,
    val ganmao: List<String>,
    val guomin: List<String>,
    val kongtiao: List<String>,
    val wuran: List<String>,
    val xiche: List<String>,
    val yundong: List<String>,
    val ziwaixian: List<String>
)

data class Weather(
    val humidity: String,
    val img: String,
    val info: String,
    val temperature: String
)

data class Wind(
    val direct: String,
    val power: String,
    val windspeed: String
)

data class InfoX(
    val day: List<String>,
    val night: List<String>
)

@Entity(tableName = "division")
data class DivisionEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "parent_name") val parent_name: String,
    @ColumnInfo(name = "parent_value") val parent_value: String,
    @ColumnInfo(name = "pinyin") val pinyin: String,
    @ColumnInfo(name = "short_pinyin") val short_pinyin: String,
    @ColumnInfo(name = "value") val value: String
)