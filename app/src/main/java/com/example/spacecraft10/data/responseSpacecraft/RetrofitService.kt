package com.example.spacecraft10.data.responseSpacecraft

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {


    @GET("/2.3.0/spacecraft_configurations/")
    suspend fun listSpacecraft(
        @Query("limit") limit: Int = 10,  // Número de elementos por página
        @Query("offset") offset: Int = 0
    ): SpacecraftResponse // Desplazamiento para la paginación): SpacecraftResponse


    object RetrofitServiceFactory {
        fun makeRetrofitService(): RetrofitService {
            return Retrofit.Builder()
                .baseUrl("https://lldev.thespacedevs.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitService::class.java)
        }
    }
}