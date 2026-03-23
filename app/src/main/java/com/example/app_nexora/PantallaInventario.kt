package com.example.app_nexora

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
fun PantallaInventario(controladorNavegacion: NavHostController, listaProductos: List<Producto>) {

    // Paleta de colores de nuestro sistema
    val colorPrimary = Color(0xFF00D166) // Verde Neón
    val colorFondo = Color(0xFFF3F4F6)   // Gris pálido

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventario General", fontSize = 22.sp, color = Color(0xFF1E272E)) },
                navigationIcon = {
                    IconButton(onClick = { controladorNavegacion.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color(0xFF1E272E))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            // MENÚ INFERIOR OMNIPRESENTE (Idéntico a Inicio y Ventas)
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
                    selected = true, onClick = { }, // ESTAMOS JUSTO AQUÍ
                    icon = { Icon(Icons.Default.List, contentDescription = "Productos") }, label = { Text("Productos") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = Color(0xFF0F5132), selectedTextColor = Color(0xFF0F5132), indicatorColor = Color(0xFFE8F8F5))
                )
                NavigationBarItem(
                    selected = false, onClick = { controladorNavegacion.navigate("reportes") },
                    icon = { Icon(Icons.Default.Star, contentDescription = "Reportes") }, label = { Text("Reportes") },
                    colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray)
                )
            }
        },
        floatingActionButton = {
            // Un botón flotante hiper moderno para agregar productos
            FloatingActionButton(
                onClick = { controladorNavegacion.navigate("nuevo_producto") },
                containerColor = colorPrimary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Producto")
            }
        }
    ) { paddingValores ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValores).background(colorFondo)
        ) {

            if (listaProductos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("📦", fontSize = 64.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Tu inventario está vacío", color = Color.Gray, fontSize = 16.sp)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(listaProductos) { producto ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // 1. CUDRO GRIS CON FOTO (EMOJI) DEL PRODUCTO
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(0xFFF1F5F9),
                                    modifier = Modifier.size(64.dp)
                                ) {
                                }

                                // 2. INFORMACIÓN DEL PRODUCTO
                                Column(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
                                    Text(producto.nombre, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1E272E))
                                    Text("$${"%.2f".format(producto.precio)}", color = colorPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.padding(top = 4.dp))
                                    Text("En almacén: ${producto.cantidadEnStock} uds.", color = Color.Gray, fontSize = 13.sp)
                                }
                            }
                        }
                    }

                    // Doble espaciado aquí abajo para que tu botón flotante NO tape al último producto
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}
