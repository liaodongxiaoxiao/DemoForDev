package com.karl.demo.demo.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.karl.demo.demo.app.ResultWrapper
import com.karl.demo.demo.app.request
import com.karl.demo.demo.entity.DivisionEntity
import com.karl.demo.demo.entity.WeatherEntity
import com.karl.demo.demo.model.DivisionDAO
import com.karl.demo.demo.model.DivisionModel
import com.karl.demo.demo.model.WeatherModel
import com.karl.kotlin.extension.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val divisionModel: DivisionModel,
    private val divisionDAO: DivisionDAO,
    private val weatherModel: WeatherModel
) : ViewModel() {

    val divisions = MutableLiveData<MutableList<DivisionEntity>>()
    val current = liveData<DivisionEntity> {
        emit(divisionDAO.getCurrentDivision()[0])
    }
    val weatherInfo = MutableLiveData<WeatherEntity>()

    fun getDivisionByName(name: String) {
        "getDivisionByName:$name".log()

        GlobalScope.launch(Dispatchers.Main) {
            val result = request { divisionModel.getDivisionByName(name) }
            when (result) {
                is ResultWrapper.Success<MutableList<DivisionEntity>> -> {
                    divisions.value = result.value
                }
                else -> {
                }
            }
            //result.log()
        }
    }

    fun saveDivision(divisionEntity: DivisionEntity) {
        divisionDAO.deleteAll()
        divisionDAO.insertDivision(divisionEntity)
    }

    fun getCityWeather(value: String) {
        //weatherModel.getCityWeatherByCityCode(value)
        GlobalScope.launch(Dispatchers.Main) {
            val result = request { weatherModel.getCityWeatherByCityCode(value) }
            when (result) {
                is ResultWrapper.Success<WeatherEntity> -> {
                    weatherInfo.value = result.value
                }
                else -> {
                }
            }
            result.log()
        }
    }
}
