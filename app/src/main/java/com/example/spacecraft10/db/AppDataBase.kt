package com.example.spacecraft10.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AgenciaMarker::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun agenciaDao(): AgenciaMarkerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "agencias_markers_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
