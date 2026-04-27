package com.example.app_nexora

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "facturas")
data class FacturaEntidad(
    @PrimaryKey val id: Int,
    val total: Double,
    val fecha: String
)

@Entity(tableName = "detalle_factura")
data class DetalleFacturaEntidad(
    @PrimaryKey(autoGenerate = true) val detalleId: Int = 0,
    val facturaId: Int,
    val nombreProducto: String,
    val precioProducto: Double,
    val cantidad: Int
)

// Clase que une factura + sus detalles para consultarlos juntos
data class FacturaConDetalle(
    @Embedded val factura: FacturaEntidad,
    @Relation(
        parentColumn = "id",
        entityColumn = "facturaId"
    )
    val detalles: List<DetalleFacturaEntidad>
)

// Conversión a tu modelo Factura existente
fun FacturaConDetalle.toFactura(): Factura {
    val productos = detalles.associate { detalle ->
        Producto(detalle.nombreProducto, detalle.precioProducto, 0) to detalle.cantidad
    }
    return Factura(id = factura.id, total = factura.total, fecha = factura.fecha, productos = productos)
}