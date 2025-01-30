package com.example.spacecraft10.data.responseSpacecraft

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SpacecraftRepository(private val retrofitService: RetrofitService) {

    suspend fun getAllSpacecrafts(): List<Results> { // Devuelve una lista de Result
        val allResults = mutableListOf<Results>()
        var offset = 0
        val limit = 10 // Cantidad de resultados por página
        var hasNextPage = true

        withContext(Dispatchers.IO) {
            while (hasNextPage) {
                try {
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
