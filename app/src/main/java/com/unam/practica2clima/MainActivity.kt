package com.unam.practica2clima

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

import com.unam.practica2clima.ui.theme.Practica2ClimaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practica2ClimaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherScreen()
                }
            }
        }
    }
}

@Composable
fun WeatherScreen() {
    var temperatura by remember { mutableStateOf("Consultando API...") }
    var viento by remember { mutableStateOf("Consultando API...") }
    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    fun fetchWeather() {
        coroutineScope.launch {
            isLoading = true
            try {
                val response = WeatherRetrofitClient.apiService.getCurrentWeather()
                temperatura = "${response.current.temperature_2m} °C"
                viento = "${response.current.wind_speed_10m} km/h"
            } catch (e: Exception) {
                temperatura = "Error de conexión"
                viento = e.message ?: "Desconocido"
            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        fetchWeather()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Práctica API: Open-Meteo",
            fontSize = 22.sp,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Temperatura Actual", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = temperatura, fontSize = 40.sp, color = MaterialTheme.colorScheme.primary)

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Velocidad del Viento", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = viento, fontSize = 24.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { fetchWeather() },
            enabled = !isLoading
        ) {
            Text(text = if (isLoading) "Actualizando..." else "Actualizar Datos")
        }
    }
}