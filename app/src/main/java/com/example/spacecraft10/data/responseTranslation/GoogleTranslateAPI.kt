package com.example.spacecraft10.data.responseTranslation

import retrofit2.Call
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