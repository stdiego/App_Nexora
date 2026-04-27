package com.example.app_nexora

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos")
    fun obtenerTodos(): Flow<List<ProductoEntidad>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(producto: ProductoEntidad)

    @Update
    suspend fun actualizar(producto: ProductoEntidad)

    @Delete
    suspend fun eliminar(producto: ProductoEntidad)
}