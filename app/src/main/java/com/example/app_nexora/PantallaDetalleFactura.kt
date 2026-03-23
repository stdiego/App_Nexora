package com.example.app_nexora

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
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
fun PantallaDetalleFactura(
    controladorNavegacion: NavHostController,
    factura: Factura?
) {
    if (factura == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Factura no encontrada", color = Color.Gray) }
        return
    }

    // Calculamos los impuestos sobre la marcha para que se vea súper profesional
    val iva = factura.total * 0.16
    val granTotal = factura.total + iva

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Factura Generada", fontSize = 20.sp, color = Color(0xFF1E272E)) },
                navigationIcon = {
                    IconButton(onClick = { controladorNavegacion.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color(0xFF1E272E))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValores ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValores)
                .background(Color(0xFFF3F4F6))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 1. BANNER VERDE DE ÉXITO ESTILO
            Surface(
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFE8F8F5), // Fondo ultra claro
                border = BorderStroke(1.dp, Color(0xFF2ECC71)) // Borde Neón
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Éxito", tint = Color(0xFF2ECC71), modifier = Modifier.size(36.dp))
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Text("Venta Completada", color = Color(0xFF27AE60), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Factura #${String.format("%05d", factura.id)} generada", color = Color.DarkGray, fontSize = 13.sp)
                    }
                }
            }

            // 2. EL PAPEL FÍSICO DEL TICKET (TARJETA BLANCA)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp).fillMaxWidth()) {

                    // HEADER DE LA EMPRESA (Cajero)
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(shape = RoundedCornerShape(12.dp), color = Color(0xFF2ECC71), modifier = Modifier.size(48.dp)) {
                            Box(contentAlignment = Alignment.Center) { Text("N", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black) }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Nexora POS", fontSize = 22.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1E272E))
                        Text("RFC: NEX123456789", color = Color.Gray, fontSize = 12.sp)
                        Text("Av. Principal #123, CDMX", color = Color.Gray, fontSize = 12.sp)
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp), color = Color(0xFFF3F4F6))

                    // INFO DEL CLIENTE RECEPTOR
                    Text("FACTURA A:", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text("Cliente General", fontSize = 15.sp, color = Color(0xFF1E272E), modifier = Modifier.padding(top = 4.dp))
                    Text("RFC: XAXX010101000", color = Color.Gray, fontSize = 12.sp)

                    Spacer(modifier = Modifier.height(20.dp))

                    // METADATOS DEL PAGO
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Factura #", color = Color.Gray, fontSize = 13.sp); Text(String.format("%05d", factura.id), color = Color.Black, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Fecha", color = Color.Gray, fontSize = 13.sp); Text(factura.fecha, color = Color.Black, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Método de Pago", color = Color.Gray, fontSize = 13.sp); Text("Efectivo", color = Color.Black, fontSize = 13.sp)
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp), color = Color(0xFFF3F4F6))

                    // DESGLOSE DE PRODUCTOS (El cuerpo principal de ventas)
                    factura.productos.forEach { (producto, cantidad) ->
                        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
                                Text(producto.nombre, color = Color(0xFF1E272E), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                Text("$cantidad x $${"%.2f".format(producto.precio)}", color = Color.Gray, fontSize = 12.sp)
                            }
                            Text("$${"%.2f".format(producto.precio * cantidad)}", color = Color(0xFF1E272E), fontSize = 14.sp)
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFF3F4F6))

                    //  IMPUESTOS Y TOTALES
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Subtotal", color = Color.Gray, fontSize = 13.sp); Text("$${"%.2f".format(factura.total)}", color = Color.Black, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("IVA (16%)", color = Color.Gray, fontSize = 13.sp); Text("$${"%.2f".format(iva)}", color = Color.Black, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("TOTAL", color = Color(0xFF1E272E), fontSize = 18.sp, fontWeight = FontWeight.Black)
                        Text("$${"%.2f".format(granTotal)}", color = Color(0xFF2ECC71), fontSize = 22.sp, fontWeight = FontWeight.Black)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // CÓDIGO QR SIMULADO (pendiente mejorar)
                    Surface(modifier = Modifier.fillMaxWidth().height(100.dp), shape = RoundedCornerShape(12.dp), color = Color(0xFFF9FAFB)) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

                        Text("🔳🔲🔳🔲🔳", fontSize = 20.sp, letterSpacing = (-4).sp)
                            Text("🔲🔳🔲🔳🔲", fontSize = 20.sp, letterSpacing = (-4).sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Código de Verificación SAT", color = Color.Gray, fontSize = 11.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. BOTONES DE ACCIÓN SECUNDARIOS (PDF y Redes)
            Button(
                onClick = { /* Lógica nativa de Descarga PDF futura */ }, modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), border = BorderStroke(1.dp, Color(0xFFE5E7EB)), shape = RoundedCornerShape(12.dp)
            ) { Text("⬇ Descargar PDF", color = Color(0xFF1E272E), fontSize = 14.sp) }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { /* Lógica WhatsApp */ }, modifier = Modifier.weight(1f).height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White), border = BorderStroke(1.dp, Color(0xFFE5E7EB)), shape = RoundedCornerShape(12.dp)
                ) { Text("💬 WhatsApp", color = Color(0xFF1E272E), fontSize = 13.sp) }

                Button(
                    onClick = { /* Lógica Correo */ }, modifier = Modifier.weight(1f).height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White), border = BorderStroke(1.dp, Color(0xFFE5E7EB)), shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Correo", color = Color(0xFF1E272E), fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // TextButton sin fondo para "Volver al principio de la app"
            TextButton(
                onClick = { controladorNavegacion.navigate("inicio") { popUpTo("inicio") { inclusive = true } } }
            ) { Text("Volver al Inicio", color = Color.Gray) }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
