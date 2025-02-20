package com.example.spacecraft10.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// Aplicación personalizada que gestiona la base de datos de agencias espaciales
class AgenciaMarkerApplication : Application() {

    // Clase de base de datos utilizando Room para gestionar las agencias espaciales
    @Database(entities = [AgenciaMarker::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun agenciaDao(): AgenciaMarkerDao

    }

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        // Callback para inicializar la base de datos cuando se crea
        val callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                CoroutineScope(Dispatchers.IO).launch {
                    val agenciasList = listOf(
                        AgenciaMarker(1, "NASA", 38.877, -77.036),
                        AgenciaMarker(2, "ESA", 48.833, 2.333),
                        AgenciaMarker(3, "Roscosmos", 55.751244, 37.618423),
                        AgenciaMarker(4, "ISRO", 13.0827, 80.2707),
                        AgenciaMarker(5, "CNSA", 39.9042, 116.4074),
                        AgenciaMarker(6, "PLD Space", 38.2922805, -0.6207973)
                    )
                    database.agenciaDao().insertAgencias(agenciasList)
                }
            }
        }

        // Construye la base de datos utilizando Room y agrega el callback
        database = Room.databaseBuilder(this, AppDatabase::class.java, "AgenciasDatabase")
            .addCallback(callback)
            .build()
    }
}
