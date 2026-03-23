package com.example.app_nexora

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

data class Factura(
    val id: Int,                     // El número de ticket (ej: Ticket #1)
    val total: Double,               // Dinero total cobrado
    val fecha: String,               // Día y hora exactas
    val productos: Map<Producto, Int> // La lista de cosas que te compraron
)
// 1. EL MOLDE DEL PRODUCTO
data class Producto(val nombre: String, val precio: Double, val cantidadEnStock: Int)

@Composable
fun NavegacionPrincipal(controladorNavegacion: NavHostController) {

    // --- ESTADOS GLOBALES DE LA APP ---
    var ingresosTotales by remember { mutableDoubleStateOf(0.0) } // El dinero ganado
    val inventario = remember { mutableStateListOf<Producto>() }  // La lista de inventario (empieza vacía)
    val historialFacturas = remember { mutableStateListOf<Factura>() }

    NavHost(
        navController = controladorNavegacion,
        startDestination = "bienvenida"
    ) {
        // --- RUTA 0: Pantalla de Bienvenida ---
        composable("bienvenida") {
            PantallaBienvenida(controladorNavegacion)
        }

        // --- RUTA 1: Pantalla de Login ---
        composable("login") {
            PantallaLogin(controladorNavegacion)
        }

        // --- RUTA 2: Pantalla de Inicio ---
        composable("inicio") {
            PantallaInicio(controladorNavegacion, listaFacturas = historialFacturas)
        }

        // --- RUTA 3: Vender ---
        composable("vender") {
            PantallaVender(
                controladorNavegacion = controladorNavegacion,
                listaProductos = inventario,
                // NUEVO: Ahora atrapamos el "Ticket" completo, no solo el dinero
                onVentaRealizada = { ticketFactura ->
                    // 1. A los ingresos totales les sumamos el dinero del ticket
                    ingresosTotales += ticketFactura.total

                    // 2. Guardamos el ticket en nuestro historial histórico
                    historialFacturas.add(ticketFactura)
                }
            )
        }



        // --- RUTA 4: Inventario ---
        composable("inventario") {
            // Le pasamos la lista de inventario real
            PantallaInventario(controladorNavegacion, listaProductos = inventario)
        }

        // --- RUTA 5: Agregar Nuevo Producto ---
        composable("nuevo_producto") {
            // Esta pantalla atrapa el producto y lo mete a la lista central
            PantallaNuevoProducto(
                controladorNavegacion = controladorNavegacion,
                onProductoCreado = { productoNuevo ->
                    inventario.add(productoNuevo)
                }
            )
        }
        // --- RUTA 6: Historial de Facturas ---
        composable("historial") {
            PantallaHistorial(controladorNavegacion, listaFacturas = historialFacturas)
        }
        // --- RUTA 7: El Detalle de la Factura ---
        // Ponemos {idFactura} entre llaves para que Android sepa que ese pedazo de ruta variará (no es texto fijo)
        composable("detalle/{idFactura}") { entradaNavegacion ->

            // 1. Extraemos el folio (texto) que venga en la URL y lo volvemos número
            val textoFolio = entradaNavegacion.arguments?.getString("idFactura")
            val idBuscado = textoFolio?.toIntOrNull() ?: 0

            // 2. Le pedimos a tu lista del historial que busque al ticket que contenga ese folio exacto
            val ticketEncontrado = historialFacturas.find { facturaActual -> facturaActual.id == idBuscado }

            // 3. Le damos ese ticket a la pantalla para que lo desglose
            PantallaDetalleFactura(controladorNavegacion, factura = ticketEncontrado)
        }
        // --- RUTA 8: Configuración ---
        composable("configuracion") {
            PantallaConfiguracion(controladorNavegacion)
        }
        // --- RUTA 9: Reportes ---
        composable("reportes") {
            PantallaReportes(
                controladorNavegacion = controladorNavegacion,
                listaFacturas = historialFacturas,
                ingresosTotales = ingresosTotales
            )
        }



    }
}
