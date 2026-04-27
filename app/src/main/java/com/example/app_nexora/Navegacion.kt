package com.example.app_nexora

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

data class Factura(
    val id: Int,
    val total: Double,
    val fecha: String,
    val productos: Map<Producto, Int>
)

data class Producto(val nombre: String, val precio: Double, val cantidadEnStock: Int)

@Composable
fun NavegacionPrincipal(controladorNavegacion: NavHostController) {

    val viewModel: NexoraViewModel = viewModel()

    NavHost(
        navController = controladorNavegacion,
        startDestination = "bienvenida"
    ) {
        // RUTA 0: Bienvenida
        composable("bienvenida") {
            PantallaBienvenida(controladorNavegacion)
        }

        // RUTA 1: Login
        composable("login") {
            PantallaLogin(controladorNavegacion)
        }

        // RUTA 2: Inicio
        composable("inicio") {
            PantallaInicio(controladorNavegacion, listaFacturas = viewModel.historialFacturas)
        }

        // RUTA 3: Vender
        composable("vender") {
            PantallaVender(
                controladorNavegacion = controladorNavegacion,
                listaProductos = viewModel.inventario,
                onVentaRealizada = { factura ->
                    viewModel.registrarVenta(factura)
                }
            )
        }

        // RUTA 4: Inventario
        composable("inventario") {
            PantallaInventario(controladorNavegacion, listaProductos = viewModel.inventario)
        }


        // RUTA 5: Agregar Nuevo Producto
        composable("nuevo_producto") {
            PantallaNuevoProducto(
                controladorNavegacion = controladorNavegacion,
                onProductoCreado = { producto ->
                    viewModel.agregarProducto(producto)
                }
            )
        }

// RUTA 6: Historial de Facturas
        composable("historial") {
            PantallaHistorial(controladorNavegacion, listaFacturas = viewModel.historialFacturas)
        }

// RUTA 7: Detalle de Factura
        composable("detalle/{idFactura}") { entradaNavegacion ->
            val textoFolio = entradaNavegacion.arguments?.getString("idFactura")
            val idBuscado  = textoFolio?.toIntOrNull() ?: 0
            val ticketEncontrado = viewModel.historialFacturas.find { it.id == idBuscado }
            PantallaDetalleFactura(controladorNavegacion, factura = ticketEncontrado)
        }

        // RUTA 8: Configuración
        composable("configuracion") {
            PantallaConfiguracion(controladorNavegacion)
        }

        // RUTA 9: Reportes
        composable("reportes") {
            PantallaReportes(
                controladorNavegacion = controladorNavegacion,
                listaFacturas = viewModel.historialFacturas,
                ingresosTotales = viewModel.ingresosTotales
            )
        }

        // RUTA 10: Editar Producto (NUEVA)
        // {indice} es la posición del producto en la lista del inventario
        composable("editar_producto/{indice}") { entradaNavegacion ->
            val indice = entradaNavegacion.arguments?.getString("indice")?.toIntOrNull() ?: -1
            val productoAEditar = viewModel.inventario.getOrNull(indice)
            PantallaEditarProducto(
                controladorNavegacion = controladorNavegacion,
                producto = productoAEditar,
                onProductoEditado = { productoActualizado ->
                    viewModel.editarProducto(indice, productoActualizado)
                }
            )
        }
    }
}
