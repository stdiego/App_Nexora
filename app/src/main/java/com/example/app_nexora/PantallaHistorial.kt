package com.example.app_nexora

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
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
//  Recibe toda la lista del historial de ventas que se ha estado guardando
fun PantallaHistorial(controladorNavegacion: NavHostController, listaFacturas: List<Factura>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de Ventas", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { controladorNavegacion.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0D1B2A))
            )
        }
    ) { paddingValores ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValores).background(Color(0xFFF5F5F5))
        ) {

            // Si nadie ha comprado nada, mostramos un mensaje vacío
            if (listaFacturas.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Aún no tienes ventas registradas.", color = Color.Gray)
                }
            } else {

                // Si sí hay compras, dibujamos la lista
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // .reversed() voltea la lista para ver la factura más nuevecita primero
                    items(listaFacturas.reversed()) { factura ->
                        Card(
                            onClick = {
                                // ¡Llamamos a la nueva ruta y le agregamos nuestro folio!
                                controladorNavegacion.navigate("detalle/${factura.id}")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Lado Izquierdo de la tarjeta (Icono, Folio, Hora)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.DateRange, // Icono de calendario
                                        contentDescription = "Factura",
                                        tint = Color(0xFF3498DB),
                                        modifier = Modifier.size(32.dp).padding(end = 8.dp)
                                    )
                                    Column {
                                        Text("Ticket #${factura.id}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D1B2A))
                                        Text(factura.fecha, color = Color.Gray, fontSize = 12.sp) // Hora en la que lo cobraste
                                    }
                                }

                                // Lado Derecho de la tarjeta (Dinero Verde)
                                Text(
                                    text = "$${"%.2f".format(factura.total)}",
                                    fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2ECC71)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
