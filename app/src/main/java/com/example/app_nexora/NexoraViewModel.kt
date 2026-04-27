package com.example.app_nexora

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class NexoraViewModel : ViewModel() {

    var ingresosTotales by mutableDoubleStateOf(0.0)
    val inventario = mutableStateListOf<Producto>()
    val historialFacturas = mutableStateListOf<Factura>()

    fun agregarProducto(producto: Producto) {
        inventario.add(producto)
    }

    fun editarProducto(indice: Int, producto: Producto) {
        if (indice >= 0 && indice < inventario.size) {
            inventario[indice] = producto
        }
    }

    fun registrarVenta(factura: Factura) {
        ingresosTotales += factura.total
        historialFacturas.add(factura)
    }
}