package com.example.app_nexora

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(controladorNavegacion: NavHostController, listaFacturas: List<Factura>) {

    // INTELIGENCIA DE DATOS EN TIEMPO REAL
    val totalIngresos = listaFacturas.sumOf { it.total }
    val transacciones = listaFacturas.size
    val ventasDelDia = listaFacturas.sumOf { factura -> factura.productos.values.sum() }

    var mostrarMenuLateral by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { mostrarMenuLateral = true }) { Icon(Icons.Default.Menu, contentDescription = "Menú", tint = Color(0xFF1E272E)) }
                    },
                    title = { Text("Nexora POS", fontSize = 20.sp, color = Color(0xFF1E272E)) },
                    actions = {
                        Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFF101828), modifier = Modifier.padding(end = 16.dp).size(32.dp)) {
                            Box(contentAlignment = Alignment.Center) { Text("N", color = Color(0xFF00D166), fontWeight = FontWeight.Bold) }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                    NavigationBarItem(selected = true, onClick = { }, icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") }, label = { Text("Inicio") }, colors = NavigationBarItemDefaults.colors(selectedIconColor = Color(0xFF0F5132), selectedTextColor = Color(0xFF0F5132), indicatorColor = Color(0xFFE8F8F5)))
                    NavigationBarItem(selected = false, onClick = { controladorNavegacion.navigate("vender") }, icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Ventas") }, label = { Text("Ventas") }, colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray))
                    NavigationBarItem(selected = false, onClick = { controladorNavegacion.navigate("inventario") }, icon = { Icon(Icons.Default.List, contentDescription = "Productos") }, label = { Text("Productos") }, colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray))
                    NavigationBarItem(selected = false, onClick = { controladorNavegacion.navigate("reportes") }, icon = { Icon(Icons.Default.Star, contentDescription = "Reportes") }, label = { Text("Reportes") }, colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray))
                }
            }
        ) { paddingValores ->
            Column(modifier = Modifier.fillMaxSize().padding(paddingValores).background(Color(0xFFF3F4F6)).verticalScroll(rememberScrollState())) {

                Spacer(modifier = Modifier.height(8.dp))

                Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF69F0AE))) {
                    Row(modifier = Modifier.padding(20.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("¡Bienvenido de nuevo!", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1E272E))
                            Text("Dashboard Actualizado", fontSize = 13.sp, color = Color(0xFF1E272E).copy(alpha = 0.8f))
                        }
                        Surface(shape = CircleShape, color = Color(0xFF00C853), modifier = Modifier.size(48.dp)) { Box(contentAlignment = Alignment.Center) { Text("⚡", fontSize = 20.sp) } }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1))) {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = CircleShape, color = Color(0xFF455A64), modifier = Modifier.size(36.dp)) { Box(contentAlignment = Alignment.Center) { Text("👑", fontSize = 14.sp) } }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Prueba Premium", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E272E))
                            Text("activa", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E272E))
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("6 días", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E272E))
                            Text("restantes", fontSize = 11.sp, color = Color.Gray)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Ver beneficios >", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00897B))
                    }
                }

                Text("Resumen de Hoy (En vivo)", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 8.dp))

                StatCard(
                    icono = { Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color(0xFF00600F)) },
                    titulo = "Artículos Vendidos", valor = "$ventasDelDia", subtitulo = "Unidades", pillTexto = "Actualizado"
                )

                StatCard(
                    icono = { Text("$", fontSize = 28.sp, color = Color(0xFF00600F), fontWeight = FontWeight.Bold) },
                    titulo = "Total Ingresos", valor = "$${"%.2f".format(totalIngresos)}", subtitulo = "Dinero Bruto", pillTexto = "Actualizado"
                )

                StatCard(
                    icono = { Text("📈", fontSize = 22.sp) }, // Gráfica emojificada
                    titulo = "Transacciones", valor = "$transacciones", subtitulo = "Tickets Impresos", pillTexto = "Actualizado"
                )

                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Ventas Semanales", color = Color.Gray, fontSize = 14.sp)
                    Surface(shape = RoundedCornerShape(16.dp), color = Color(0xFF69F0AE)) { Text("📊 En Vivo", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00600F), modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)) }
                }

                // EL GRÁFICO DINÁMICO DE BARRAS
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(200.dp),
                    shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                ) {
                    GraficoVentasNativo(totalHoy = totalIngresos)
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        if (mostrarMenuLateral) { Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)).clickable { mostrarMenuLateral = false }) }

        // ==========================================
        //  EL MENÚ LATERAL MAGNÍFICO (Drawer)
        // ==========================================
        AnimatedVisibility(visible = mostrarMenuLateral, enter = slideInHorizontally(initialOffsetX = { -it }), exit = slideOutHorizontally(targetOffsetX = { -it }), modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                // Modificador importante: fillMaxHeight en la columna contenedora principal del panel
                Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.85f).background(Color.White).clickable(enabled = false) {}) {

                    // HEADER VERDE
                    Column(modifier = Modifier.fillMaxWidth().background(Color(0xFF69F0AE)).padding(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription="Cerrar", tint=Color.Black, modifier = Modifier.size(28.dp).clickable { mostrarMenuLateral=false })
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(shape = RoundedCornerShape(12.dp), color = Color(0xFF101828), modifier = Modifier.size(48.dp)) { Box(contentAlignment = Alignment.Center) { Text("N", color = Color(0xFF69F0AE), fontWeight = FontWeight.Black, fontSize = 24.sp) } }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column { Text("Nexora POS", fontSize = 20.sp, fontWeight = FontWeight.Black, color = Color(0xFF1E272E)); Text("v1.0.0", fontSize = 13.sp, color = Color.DarkGray) }
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = Color.Black.copy(alpha = 0.2f))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Home, contentDescription=null, tint=Color.Black.copy(alpha=0.6f), modifier=Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column { Text("Negocio", fontSize = 12.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold); Text("Tech Store Pro", fontSize = 15.sp, color = Color(0xFF1E272E)) }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription=null, tint=Color.Black.copy(alpha=0.6f), modifier=Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column { Text("RUC / RFC", fontSize = 12.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold); Text("20123456789-0", fontSize = 15.sp, color = Color(0xFF1E272E)) }
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = Color.Black.copy(alpha = 0.2f))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(shape = CircleShape, color = Color(0xFF00C853), modifier = Modifier.size(44.dp)) { Icon(Icons.Default.Person, contentDescription=null, tint=Color.White, modifier=Modifier.padding(10.dp)) }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column { Text("Admin Usuario", fontSize = 16.sp, color = Color(0xFF1E272E), fontWeight = FontWeight.Bold); Text("admin@nexora.com", fontSize = 13.sp, color = Color.DarkGray) }
                        }
                    }

                    // OPCIONES DE MENÚ CON BOTÓN CERRAR SESIÓN AL FINAL
                    Column(modifier = Modifier.fillMaxHeight().padding(vertical = 16.dp)) {
                        OpcionMenuDelDrawer(Icons.Default.Person, "Mi Perfil") { mostrarMenuLateral = false }
                        OpcionMenuDelDrawer(Icons.Default.Settings, "Configuración") { mostrarMenuLateral = false; controladorNavegacion.navigate("configuracion") }
                        Row(modifier = Modifier.fillMaxWidth().clickable { mostrarMenuLateral = false; controladorNavegacion.navigate("historial") }.padding(horizontal = 24.dp, vertical = 18.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.List, contentDescription=null, tint=Color.Gray, modifier=Modifier.size(26.dp)); Spacer(modifier = Modifier.width(20.dp)); Text("Historial de Facturas", fontSize = 16.sp, color = Color(0xFF1E272E), modifier = Modifier.weight(1f)); Surface(shape = RoundedCornerShape(12.dp), color = Color(0xFFBBE4FF)) { Text("💎", fontSize = 14.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) }
                        }
                        OpcionMenuDelDrawer(Icons.Default.Info, "Soporte") { mostrarMenuLateral = false }

                        // CREAMOS UN ESPACIO FLEXIBLE QUE EMPUJA EL BOTÓN HASTA ABAJO
                        Spacer(modifier = Modifier.weight(1f))

                        HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp), color = Color.LightGray.copy(alpha=0.5f))

                        // BOTÓN DE CERRAR SESIÓN ROJO
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    mostrarMenuLateral = false
                                    controladorNavegacion.navigate("login") { popUpTo(0) } // Borra memoria de navegación
                                }
                                .padding(horizontal = 24.dp, vertical = 18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión", tint = Color(0xFFD32F2F), modifier = Modifier.size(26.dp))
                            Spacer(modifier = Modifier.width(20.dp))
                            Text("Cerrar Sesión", fontSize = 16.sp, color = Color(0xFFD32F2F), fontWeight = FontWeight.Medium)
                        }

                        Spacer(modifier = Modifier.height(16.dp)) // Espacio final
                    }
                }
            }
        }
    }
}

// === COMPONENTE DEL GRÁFICO CREADO DESDE CERO EN COMPOSE ===
@Composable
fun GraficoVentasNativo(totalHoy: Double) {
    val ventasSemana = listOf(
        Pair("Lun", 800.0), Pair("Mar", 1200.0), Pair("Mié", 950.0),
        Pair("Jue", 1500.0), Pair("Vie", 2300.0), Pair("Sáb", 1800.0),
        Pair("Hoy", if (totalHoy > 0) totalHoy else 400.0)
    )

    val maxVenta = ventasSemana.maxOf { it.second }.coerceAtLeast(1.0)

    Row(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
        ventasSemana.forEach { (dia, monto) ->
            val proporcion = (monto / maxVenta).toFloat()
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxHeight()) {
                Surface(modifier = Modifier.width(28.dp).fillMaxHeight(fraction = if (proporcion > 0f) proporcion else 0.05f), shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp), color = if (dia == "Hoy") Color(0xFF00D166) else Color(0xFFE5E7EB)) {}
                Spacer(modifier = Modifier.height(8.dp))
                Text(dia, color = if (dia == "Hoy") Color(0xFF1E272E) else Color.Gray, fontSize = 12.sp, fontWeight = if (dia == "Hoy") FontWeight.Bold else FontWeight.Normal)
            }
        }
    }
}

@Composable
fun StatCard(icono: @Composable () -> Unit, titulo: String, valor: String, subtitulo: String, pillTexto: String) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), border = BorderStroke(1.dp, Color(0xFFE5E7EB))) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = RoundedCornerShape(12.dp), color = Color(0xFF69F0AE).copy(alpha = 0.3f), modifier = Modifier.size(56.dp)) { Box(contentAlignment = Alignment.Center) { icono() } }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) { Text(titulo, color = Color.Gray, fontSize = 13.sp); Text(valor, fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color(0xFF1E272E)); Text(subtitulo, color = Color.Gray, fontSize = 12.sp) }
            Surface(shape = RoundedCornerShape(16.dp), color = Color(0xFFE1F5FE)) { Text(pillTexto, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0277BD), modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) }
        }
    }
}

@Composable
fun OpcionMenuDelDrawer(icono: ImageVector, titulo: String, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(horizontal = 24.dp, vertical = 18.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icono, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(26.dp)); Spacer(modifier = Modifier.width(20.dp)); Text(titulo, fontSize = 16.sp, color = Color(0xFF1E272E))
    }
}
