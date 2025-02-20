package com.example.spacecraft10.data.responseSpacecraft

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SpacecraftRepository(private val retrofitService: RetrofitService) {


    // Función suspendida que obtiene todas las naves espaciales de la API
    suspend fun getAllSpacecrafts(): List<Results> {
        val allResults = mutableListOf<Results>()
        var offset = 0
        val limit = 10 // Cantidad de resultados por página
        var hasNextPage = true


        // Realiza la solicitud de manera asincrónica en un hilo de IO
        withContext(Dispatchers.IO) {
            while (hasNextPage) {
                try {

                    // Realiza una solicitud a la API para obtener las naves espaciales
                    val response = retrofitService.listSpacecraft(limit = limit, offset = offset)
                    allResults.addAll(response.results) // Agrega los resultados de la respuesta
                    offset += limit
                    hasNextPage = response.next != null
                } catch (e: Exception) {
                    println("Error al obtener datos: ${e.message}")
                    break
                }
            }
        }

        return allResults
    }
}
