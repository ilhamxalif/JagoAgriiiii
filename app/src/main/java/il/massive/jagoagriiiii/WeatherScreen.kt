package il.massive.jagoagriiiii.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import il.massive.jagoagriiiii.repository.WeatherRepository
import kotlinx.coroutines.launch

@Composable
fun WeatherScreen(navController: NavController) {
    var temperature by remember { mutableStateOf("--") }
    var condition by remember { mutableStateOf("--") }
    var windSpeed by remember { mutableStateOf("--") }
    var humidity by remember { mutableStateOf("--") }
    var cityName by remember { mutableStateOf("--") }
    val coroutineScope = rememberCoroutineScope()

    // Fetch cuaca ketika composable diload
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val weatherResponse = WeatherRepository.getWeather("Jakarta")
                cityName = weatherResponse.name
                temperature = "${weatherResponse.main.temp}°C"
                condition = weatherResponse.weather.firstOrNull()?.description ?: "Unknown"
                windSpeed = "${weatherResponse.wind.speed} km/h"
                humidity = "${weatherResponse.main.humidity}%"
            } catch (e: Exception) {
                condition = "Gagal memuat data: ${e.message}" // Tampilkan error
            }
        }
    }


    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Tombol Kembali
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Kembali")
            }
            Text(
                text = "Cuaca Hari Ini",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Current Weather Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF007A49)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = cityName,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Suhu: $temperature",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    text = "Kondisi: $condition",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    text = "Angin: $windSpeed | Hum: $humidity",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
            }
        }
    }
}
