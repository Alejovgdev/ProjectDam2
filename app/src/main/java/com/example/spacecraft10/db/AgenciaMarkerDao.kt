package com.example.spacecraft10.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AgenciaMarkerDao {
    @Query("SELECT * FROM agencias")
    suspend fun getAllAgencias(): List<AgenciaMarker>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAgencias(agencias: List<AgenciaMarker>)
}





