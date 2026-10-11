package com.unam.practica2clima

data class WeatherResponse(
    val current: CurrentWeather
)

data class CurrentWeather(
    val time: String,
    val temperature_2m: Double,
    val wind_speed_10m: Double
)