package com.example.app_nexora

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaVender(
    controladorNavegacion: NavHostController,
    listaProductos: List<Producto>,
    onVentaRealizada: (Factura) -> Unit
) {
    val carrito = remember { mutableStateMapOf<Producto, Int>() }

    // Cálculos de dinero hiper-precisos
    val subtotal = carrito.entries.sumOf { (producto, cantidad) -> producto.precio * cantidad }
    val iva = subtotal * 0.16
    val granTotal = subtotal + iva

    // AHORA TENEMOS 3 ESTADOS: "CATALOGO", "CARRITO", o "PAGO"
    var pantallaActual by remember { mutableStateOf("CATALOGO") }

    var busqueda by remember { mutableStateOf("") }
    var metodoPagoSeleccionado by remember { mutableStateOf("Efectivo") }
    var montoRecibido by remember { mutableStateOf("") }

    val productosFiltrados = listaProductos.filter { it.nombre.contains(busqueda, ignoreCase = true) }


    // ====================================================================
    // ESTADO 3: PROCESAR PAGO (El paso final de tarjetas/efectivo)
    // ====================================================================
    if (pantallaActual == "PAGO") {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Procesar Pago", fontSize = 22.sp, color = Color(0xFF1E272E)) },
                    navigationIcon = {
                        // Atrás te regresa a revisar el carrito
                        IconButton(onClick = { pantallaActual = "CARRITO" }) { Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color(0xFF1E272E)) }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding).fillMaxSize().background(Color.White).padding(20.dp)) {
                Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = Color(0xFFF1F5F9)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
                        Text("Total a Pagar", color = Color.Gray, fontSize = 15.sp, modifier = Modifier.padding(bottom = 8.dp))
                        Text("$${"%.2f".format(granTotal)}", color = Color(0xFF2ECC71), fontSize = 42.sp, fontWeight = FontWeight.Bold) // Ahora cobramos Gran Total
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text("Método de Pago", fontSize = 22.sp, color = Color(0xFF1E272E), fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Surface(modifier = Modifier.weight(1f).height(100.dp).clickable { metodoPagoSeleccionado = "Efectivo" }, shape = RoundedCornerShape(16.dp), color = if (metodoPagoSeleccionado == "Efectivo") Color(0xFFF0FDF4) else Color.White, border = BorderStroke(2.dp, if (metodoPagoSeleccionado == "Efectivo") Color(0xFF2ECC71) else Color(0xFFE5E7EB))) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) { Text("💵", fontSize = 28.sp); Spacer(modifier=Modifier.height(8.dp)); Text("Efectivo", color = if (metodoPagoSeleccionado == "Efectivo") Color(0xFF2ECC71) else Color.Gray, fontWeight = FontWeight.Medium) }
                    }
                    Surface(modifier = Modifier.weight(1f).height(100.dp).clickable { metodoPagoSeleccionado = "Tarjeta" }, shape = RoundedCornerShape(16.dp), color = if (metodoPagoSeleccionado == "Tarjeta") Color(0xFFF0FDF4) else Color.White, border = BorderStroke(2.dp, if (metodoPagoSeleccionado == "Tarjeta") Color(0xFF2ECC71) else Color(0xFFE5E7EB))) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) { Text("💳", fontSize = 28.sp); Spacer(modifier=Modifier.height(8.dp)); Text("Tarjeta", color = if (metodoPagoSeleccionado == "Tarjeta") Color(0xFF2ECC71) else Color.Gray, fontWeight = FontWeight.Medium) }
                    }
                    Surface(modifier = Modifier.weight(1f).height(100.dp).clickable { metodoPagoSeleccionado = "Transfer" }, shape = RoundedCornerShape(16.dp), color = if (metodoPagoSeleccionado == "Transfer") Color(0xFFF0FDF4) else Color.White, border = BorderStroke(2.dp, if (metodoPagoSeleccionado == "Transfer") Color(0xFF2ECC71) else Color(0xFFE5E7EB))) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) { Text("🏦", fontSize = 28.sp); Spacer(modifier=Modifier.height(8.dp)); Text("Transfer", color = if (metodoPagoSeleccionado == "Transfer") Color(0xFF2ECC71) else Color.Gray, fontWeight = FontWeight.Medium) }
                    }
                }
                if (metodoPagoSeleccionado == "Efectivo") {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Monto Recibido", color = Color.Gray, fontSize = 13.sp, modifier = Modifier.padding(bottom = 8.dp))
                    OutlinedTextField(value = montoRecibido, onValueChange = { montoRecibido = it }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), shape = RoundedCornerShape(12.dp), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF2ECC71), unfocusedBorderColor = Color(0xFFE5E7EB)))
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        val fechaStr = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm"))
                        val idGenerado = (1000..9999).random()
                        val factura = Factura(id = idGenerado, total = subtotal, fecha = fechaStr, productos = carrito.toMap()) // Guardamos el subtotal, la factura calcula el IVA en su vista
                        onVentaRealizada(factura)
                        controladorNavegacion.navigate("detalle/$idGenerado") { popUpTo("vender") { inclusive = true } }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.White), elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                ) { Text("Confirmar Pago", fontSize = 16.sp, color = Color(0xFF1E272E), fontWeight = FontWeight.Bold) }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
    // ====================================================================
    // ESTADO 2: REVISIÓN DE CARRITO (El nuevo estado de la foto)
    // ====================================================================
    else if (pantallaActual == "CARRITO") {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Carrito", fontSize = 22.sp, color = Color(0xFF1E272E)) },
                    navigationIcon = {
                        IconButton(onClick = { pantallaActual = "CATALOGO" }) { Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color(0xFF1E272E)) }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            },
            bottomBar = {
                // PANEL INFERIOR GRIS DE RESUMEN
                Surface(color = Color(0xFFE5E7EB).copy(alpha = 0.5f), shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Subtotal", color = Color(0xFF1E272E), fontSize = 15.sp)
                            Text("$${"%.2f".format(subtotal)}", color = Color(0xFF1E272E), fontSize = 15.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("IVA (16%)", color = Color(0xFF1E272E), fontSize = 15.sp)
                            Text("$${"%.2f".format(iva)}", color = Color(0xFF1E272E), fontSize = 15.sp)
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = Color.Gray.copy(alpha=0.3f))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("TOTAL", color = Color(0xFF1E272E), fontSize = 18.sp, fontWeight = FontWeight.Black)
                            Column(horizontalAlignment = Alignment.End) {
                                Text("$${"%.2f".format(granTotal)}", color = Color(0xFF00D166), fontSize = 32.sp, fontWeight = FontWeight.Black)
                                Text("Impuestos incluidos", color = Color.Gray, fontSize = 12.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { pantallaActual = "PAGO" }, // Pasamos a COBRAR
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D166)),
                            shape = RoundedCornerShape(16.dp)
                        ) { Text("Proceder al Pago", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White) }
                    }
                }
            }
        ) { paddingValores ->
            Column(modifier = Modifier.fillMaxSize().padding(paddingValores).background(Color(0xFFF3F4F6)).padding(16.dp)) {
                if (carrito.isEmpty()) {
                    Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) { Text("El carrito está vacío", color = Color.Gray) }
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        // AQUÍ DIBUJAMOS LOS ARTÍCULOS DETALLADOS DEL CARRITO
                        items(carrito.entries.toList()) { (producto, cantidadEnCarrito) ->
                            Card(
                                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Color(0xFFE5E7EB)), elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                            ) {
                                Column(modifier = Modifier.padding(20.dp)) {
                                    // Fila Foto y nombre
                                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                                        Surface(shape = RoundedCornerShape(12.dp), color = Color(0xFFF3F4F6), modifier = Modifier.size(64.dp)) { Box(contentAlignment = Alignment.Center) { } }
                                        Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                                            Text(producto.nombre, fontSize = 16.sp, color = Color(0xFF1E272E), fontWeight = FontWeight.Medium)
                                            Text("$${"%.2f".format(producto.precio)}", fontSize = 15.sp, color = Color(0xFF00D166), fontWeight = FontWeight.Bold, modifier = Modifier.padding(top=4.dp))
                                        }
                                        // Botón Rojo de Eliminar
                                        IconButton(onClick = { carrito.remove(producto) }) { Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color(0xFFE74C3C)) }
                                    }

                                    Spacer(modifier = Modifier.height(20.dp))

                                    // Fila de Contadores [-] 1 [+]
                                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Surface(shape = RoundedCornerShape(10.dp), border = BorderStroke(1.dp, Color.LightGray), color = Color.White, modifier = Modifier.size(40.dp).clickable { if (cantidadEnCarrito > 1) { carrito[producto] = cantidadEnCarrito - 1 } else { carrito.remove(producto) } }) { Box(contentAlignment = Alignment.Center) { Text("-", fontSize = 24.sp, color = Color.Gray) } }
                                            Text(cantidadEnCarrito.toString(), fontSize = 18.sp, modifier = Modifier.padding(horizontal = 20.dp), color = Color(0xFF1E272E), fontWeight = FontWeight.Medium)
                                            Surface(shape = RoundedCornerShape(10.dp), border = BorderStroke(1.dp, Color.LightGray), color = Color.White, modifier = Modifier.size(40.dp).clickable { if (cantidadEnCarrito < producto.cantidadEnStock) { carrito[producto] = cantidadEnCarrito + 1 } }) { Box(contentAlignment = Alignment.Center) { Text("+", fontSize = 20.sp, color = Color.Gray) } }
                                        }
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text("|", color = Color.LightGray, fontSize = 24.sp, modifier = Modifier.padding(horizontal = 16.dp))
                                        Text("$${"%.2f".format(producto.precio * cantidadEnCarrito)}", fontSize = 16.sp, color = Color(0xFF1E272E), fontWeight = FontWeight.Medium)
                                    }
                                }
                            }
                        }
                        item { Spacer(modifier = Modifier.height(24.dp)) }
                    }
                }
            }
        }
    }
    // ====================================================================
    // ESTADO 1: CATÁLOGO PRINCIPAL (La pantalla inicial de artículos)
    // ====================================================================
    else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Nueva Venta", fontSize = 22.sp, color = Color(0xFF1E272E)) },
                    navigationIcon = { IconButton(onClick = { controladorNavegacion.navigateUp() }) { Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color(0xFF1E272E)) } },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            },
            bottomBar = {
                Column {
                    Box(modifier = Modifier.fillMaxWidth().background(Color.Transparent).padding(horizontal = 16.dp, vertical = 8.dp)) {
                        Button(
                            onClick = { if (subtotal > 0) { pantallaActual = "CARRITO" } }, // VAMOS AL CARRITO
                            modifier = Modifier.fillMaxWidth().height(64.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D166)), shape = RoundedCornerShape(24.dp)
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (subtotal > 0) "Ir al Carrito ($${"%.2f".format(subtotal)})" else "El carrito está vacío", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                        val totalArticulos = carrito.values.sum()
                        if (totalArticulos > 0) {
                            Surface(modifier = Modifier.align(Alignment.TopEnd).offset(x = 8.dp, y = (-8).dp).size(28.dp), shape = CircleShape, color = Color(0xFFFF8577), border = BorderStroke(2.dp, Color.White)) {
                                Box(contentAlignment = Alignment.Center) { Text(totalArticulos.toString(), color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold) }
                            }
                        }
                    }
                    NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                        NavigationBarItem(selected = false, onClick = { controladorNavegacion.navigate("inicio") }, icon = { Icon(Icons.Default.Home, contentDescription="Inicio") }, label = { Text("Inicio") }, colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray))
                        NavigationBarItem(selected = true, onClick = { }, icon = { Icon(Icons.Default.ShoppingCart, contentDescription="Ventas") }, label = { Text("Ventas") }, colors = NavigationBarItemDefaults.colors(selectedIconColor = Color(0xFF0F5132), selectedTextColor = Color(0xFF0F5132), indicatorColor = Color(0xFFE8F8F5)))
                        NavigationBarItem(selected = false, onClick = { controladorNavegacion.navigate("inventario") }, icon = { Icon(Icons.Default.List, contentDescription="Productos") }, label = { Text("Productos") }, colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray))
                        NavigationBarItem(selected = false, onClick = { controladorNavegacion.navigate("reportes") }, icon = { Icon(Icons.Default.Star, contentDescription="Reportes") }, label = { Text("Reportes") }, colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray))
                    }
                }
            }
        ) { paddingValores ->
            Column(modifier = Modifier.fillMaxSize().padding(paddingValores).background(Color(0xFFF3F4F6))) {
                Surface(modifier = Modifier.fillMaxWidth().background(Color.White).padding(horizontal = 16.dp, vertical = 12.dp)) {
                    OutlinedTextField(value = busqueda, onValueChange = { busqueda = it }, placeholder = { Text("Buscar productos...", color = Color.Gray) }, leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.Gray) }, modifier = Modifier.fillMaxWidth(), singleLine = true, shape = RoundedCornerShape(12.dp), colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, focusedBorderColor = Color(0xFF00D166), unfocusedContainerColor = Color.White, focusedContainerColor = Color.White))
                }

                if (listaProductos.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("No hay productos en inventario.", color = Color.Gray) }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(productosFiltrados) { producto ->
                            val cantidadEnCarrito = carrito[producto] ?: 0
                            Card(
                                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    if (producto.precio > 25) {
                                        Surface(modifier = Modifier.align(Alignment.TopEnd).padding(end = 16.dp, top = 12.dp), shape = RoundedCornerShape(12.dp), color = Color(0xFFFF8577)) {
                                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) { Icon(Icons.Default.Star, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp)); Text(" Trending", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                                        }
                                    }
                                    Row(modifier = Modifier.padding(20.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                        Surface(shape = RoundedCornerShape(16.dp), color = Color(0xFFF1F5F9), modifier = Modifier.size(72.dp)) { Box(contentAlignment = Alignment.Center) {  } }
                                        Column(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
                                            Text(producto.nombre, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1E272E))
                                            Text("$${"%.2f".format(producto.precio)}", color = Color(0xFF2ECC71), fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 4.dp))
                                            Text("Stock: ${producto.cantidadEnStock}", color = Color(0xFFE67E22), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                        }
                                        Surface(shape = RoundedCornerShape(16.dp), color = Color(0xFF27AE60), modifier = Modifier.size(56.dp).clickable { if (cantidadEnCarrito < producto.cantidadEnStock) { carrito[producto] = cantidadEnCarrito + 1 } }) { Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White, modifier = Modifier.size(28.dp)) } }
                                    }
                                }
                            }
                        }
                        item { Spacer(modifier = Modifier.height(100.dp)) }
                    }
                }
            }
        }
    }
}
