package com.example.app_nexora

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FacturaDao {
    @Transaction
    @Query("SELECT * FROM facturas")
    fun obtenerTodas(): Flow<List<FacturaConDetalle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarFactura(factura: FacturaEntidad)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarDetalles(detalles: List<DetalleFacturaEntidad>)
}