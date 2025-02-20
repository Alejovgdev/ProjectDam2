package com.example.spacecraft10.data.responseTranslation

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GoogleTranslateApi {
    @POST("language/translate/v2")
    fun translateText(
        @Query("key") apiKey: String,
        @Body request: TranslationRequest
    ): Call<TranslationResponse>
}


object RetrofitClient {
    private const val BASE_URL = "https://translation.googleapis.com/"

    val instance: GoogleTranslateApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleTranslateApi::class.java)
    }
}
