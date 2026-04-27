package com.example.app_nexora

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ProductoEntidad::class, FacturaEntidad::class, DetalleFacturaEntidad::class],
    version = 1,
    exportSchema = false
)
abstract class NexoraDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun facturaDao(): FacturaDao

    companion object {
        @Volatile private var INSTANCIA: NexoraDatabase? = null

        fun obtenerInstancia(context: Context): NexoraDatabase {
            return INSTANCIA ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    NexoraDatabase::class.java,
                    "nexora_db"
                ).build().also { INSTANCIA = it }
            }
        }
    }
}