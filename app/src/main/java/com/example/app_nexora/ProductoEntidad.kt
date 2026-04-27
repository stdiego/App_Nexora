package com.example.app_nexora

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class ProductoEntidad(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val precio: Double,
    val cantidadEnStock: Int
)

// Funciones de conversión entre tu modelo actual y la entidad Room
fun ProductoEntidad.toProducto() = Producto(nombre, precio, cantidadEnStock)
fun Producto.toEntidad() = ProductoEntidad(nombre = nombre, precio = precio, cantidadEnStock = cantidadEnStock)