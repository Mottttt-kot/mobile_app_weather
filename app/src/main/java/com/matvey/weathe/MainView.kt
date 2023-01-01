package com.matvey.weathe

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import java.util.FormattableFlags

interface MainView: MvpView {


    @AddToEndSingle
    fun displayCurrentTemp(data: WeatherData)
    @AddToEndSingle
    fun displayHourlyData(data:List<HourlyWeatherModel>)
    @AddToEndSingle
    fun displayDaillyData(data :List<DaillyWeatherModel>)
    @AddToEndSingle
    fun displayError(error: Throwable)
    @AddToEndSingle
    fun setLoading(flags: Boolean)
}