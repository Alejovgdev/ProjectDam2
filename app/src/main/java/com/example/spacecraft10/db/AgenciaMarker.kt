package com.example.spacecraft10.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agencias")
data class AgenciaMarker(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val latitud: Double,
    val longitud: Double
)





