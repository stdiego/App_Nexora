package com.example.app_nexora

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NexoraViewModel(application: Application) : AndroidViewModel(application) {

    private val db = NexoraDatabase.obtenerInstancia(application)
    private val productoDao = db.productoDao()
    private val facturaDao = db.facturaDao()

    val inventario = mutableStateListOf<Producto>()
    val historialFacturas = mutableStateListOf<Factura>()
    var ingresosTotales by mutableDoubleStateOf(0.0)

    // Guardamos el id de la entidad para poder editar/eliminar
    private val _entidadesProducto = mutableListOf<ProductoEntidad>()

    init {
        // Observar productos desde Room
        viewModelScope.launch {
            productoDao.obtenerTodos().collectLatest { entidades ->
                _entidadesProducto.clear()
                _entidadesProducto.addAll(entidades)
                inventario.clear()
                inventario.addAll(entidades.map { it.toProducto() })
            }
        }
        // Observar facturas desde Room
        viewModelScope.launch {
            facturaDao.obtenerTodas().collectLatest { lista ->
                historialFacturas.clear()
                historialFacturas.addAll(lista.map { it.toFactura() })
                ingresosTotales = historialFacturas.sumOf { it.total }
            }
        }
    }

    fun agregarProducto(producto: Producto) {
        viewModelScope.launch {
            productoDao.insertar(producto.toEntidad())
        }
    }

    fun editarProducto(indice: Int, producto: Producto) {
        viewModelScope.launch {
            val entidad = _entidadesProducto.getOrNull(indice)
            if (entidad != null) {
                productoDao.actualizar(
                    entidad.copy(
                        nombre = producto.nombre,
                        precio = producto.precio,
                        cantidadEnStock = producto.cantidadEnStock
                    )
                )
            }
        }
    }

    fun registrarVenta(factura: Factura) {
        viewModelScope.launch {
            val facturaEntidad = FacturaEntidad(
                id = factura.id,
                total = factura.total,
                fecha = factura.fecha
            )
            val detalles = factura.productos.map { (producto, cantidad) ->
                DetalleFacturaEntidad(
                    facturaId = factura.id,
                    nombreProducto = producto.nombre,
                    precioProducto = producto.precio,
                    cantidad = cantidad
                )
            }
            facturaDao.insertarFactura(facturaEntidad)
            facturaDao.insertarDetalles(detalles)
        }
    }
}