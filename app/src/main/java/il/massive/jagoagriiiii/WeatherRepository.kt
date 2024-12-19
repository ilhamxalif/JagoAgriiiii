package il.massive.jagoagriiiii.repository

import il.massive.jagoagriiiii.data.WeatherResponse
import il.massive.jagoagriiiii.network.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherRepository {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private const val API_KEY = "708ce77320970fecb01efeef67daece4" // API Key Anda

    private val api: WeatherApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApiService::class.java)

    suspend fun getWeather(city: String): WeatherResponse {
        return api.getCurrentWeather(city, apiKey = API_KEY)
    }
}
