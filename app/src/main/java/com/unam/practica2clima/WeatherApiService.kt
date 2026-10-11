package com.unam.practica2clima

import retrofit2.http.GET

interface WeatherApiService {
    @GET("v1/forecast?latitude=19.4342&longitude=-99.1386&current=temperature_2m,wind_speed_10m")
    suspend fun getCurrentWeather(): WeatherResponse
}