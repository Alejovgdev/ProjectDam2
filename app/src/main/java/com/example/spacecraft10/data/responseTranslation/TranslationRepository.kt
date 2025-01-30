package com.example.spacecraft10.data.responseTranslation

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TranslationRepository {

    private val apikey = ""

    fun translateText(text: String, targetLang: String, callback: (String) -> Unit) {
        val request = TranslationRequest(q = text, target = targetLang)

        RetrofitClient.instance.translateText(apikey, request)
            .enqueue(object : Callback<TranslationResponse> {
                override fun onResponse(call: Call<TranslationResponse>, response: Response<TranslationResponse>) {
                    if (response.isSuccessful) {
                        val translatedText = response.body()?.data?.translations?.get(0)?.translatedText ?: "Error en la traducción"
                        callback(translatedText)
                    } else {
                        callback("Error: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<TranslationResponse>, t: Throwable) {
                    callback("Error de conexión")
                }
            })
    }
}
