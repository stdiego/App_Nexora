package com.example.app_nexora

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaReportes(controladorNavegacion: NavHostController, listaFacturas: List<Factura>, ingresosTotales: Double) {

    // --- MAGIA DE KOTLIN: Minería de Datos Básica ---
    // 1. ¿Cuántos tickets se imprimieron históricamente?
    val totalVentas = listaFacturas.size

    // 2. Extraemos todos los productos vendidos
    val todosLosProductosVendidos = listaFacturas.flatMap { it.productos.entries }

    // 3. Agrupamos y sumamos cuántos se vendieron de cada uno
    val sumatoriaPorProducto = todosLosProductosVendidos
        .groupBy({ it.key }, { it.value })
        .mapValues { it.value.sum() }

    // 4. Encontramos el nombre y LA FOTO del más vendido
    val productoEstrella = sumatoriaPorProducto.maxByOrNull { it.value }?.key?.nombre ?: "Sin datos de venta"
    // ------------------------------------------------

    val colorFondo = Color(0xFFF3F4F6)   // Gris pálido

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reportes y Analíticas", fontSize = 22.sp, color = Color(0xFF1E272E)) },
                navigationIcon = {
                    IconButton(onClick = { controladorNavegacion.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color(0xFF1E272E))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            // MENÚ INFERIOR OMNIPRESENTE (Idéntico a todas las demás)
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                NavigationBarItem(
                    selected = false, onClick = { controladorNavegacion.navigate("inicio") },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") }, label = { Text("Inicio") },
                    colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray)
                )
                NavigationBarItem(
                    selected = false, onClick = { controladorNavegacion.navigate("vender") },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Ventas") }, label = { Text("Ventas") },
                    colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray)
                )
                NavigationBarItem(
                    selected = false, onClick = { controladorNavegacion.navigate("inventario") },
                    icon = { Icon(Icons.Default.List, contentDescription = "Productos") }, label = { Text("Productos") },
                    colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray)
                )
                NavigationBarItem(
                    selected = true, onClick = { }, // ESTAMOS JUSTO AQUÍ EN LA PESTAÑA FINAL
                    icon = { Icon(Icons.Default.Star, contentDescription = "Reportes") }, label = { Text("Reportes") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = Color(0xFF0F5132), selectedTextColor = Color(0xFF0F5132), indicatorColor = Color(0xFFE8F8F5))
                )
            }
        }
    ) { paddingValores ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValores)
                .background(colorFondo)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("RESUMEN GENERAL", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 14.sp, modifier = Modifier.padding(start = 4.dp))

            // Tarjeta 1: Total Generado
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(modifier = Modifier.size(56.dp), shape = RoundedCornerShape(16.dp), color = Color(0xFF2ECC71).copy(alpha = 0.15f)) {
                        Box(contentAlignment = Alignment.Center) { Text("$", color = Color(0xFF27AE60), fontSize = 28.sp, fontWeight = FontWeight.Bold) }
                    }
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text("Ingresos Brutos", color = Color.Gray, fontSize = 14.sp)
                        Text("$${"%.2f".format(ingresosTotales)}", fontSize = 28.sp, fontWeight = FontWeight.Black, color = Color(0xFF1E272E))
                    }
                }
            }

            // Tarjeta 2: Cantidad de Ventas (Tickets Impresos)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(modifier = Modifier.size(56.dp), shape = RoundedCornerShape(16.dp), color = Color(0xFF3498DB).copy(alpha = 0.15f)) {
                        Box(contentAlignment = Alignment.Center) { Text("#", color = Color(0xFF2980B9), fontSize = 28.sp, fontWeight = FontWeight.Bold) }
                    }
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text("Tickets Generados", color = Color.Gray, fontSize = 14.sp)
                        Text("$totalVentas transacciones", fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color(0xFF1E272E))
                    }
                }
            }

            Text("MÉTRICAS DESTACADAS", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 14.sp, modifier = Modifier.padding(start = 4.dp, top = 8.dp))

            // Tarjeta 3: Produto Estrella (Con la foto emoji importada del map de Kotlin)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(modifier = Modifier.size(56.dp), shape = RoundedCornerShape(16.dp), color = Color(0xFFF1F5F9)) {
                    }
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text("Tu Producto Estrella", color = Color.Gray, fontSize = 14.sp)
                        Text(productoEstrella, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E272E))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
