package com.example.app_nexora

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// NUEVO: Recibimos nuevamente la función "puente" para mandar los datos de regreso
fun PantallaNuevoProducto(controladorNavegacion: NavHostController, onProductoCreado: (Producto) -> Unit) {

    // Variables de estado para los 3 campos a llenar
    var nombre by remember { mutableStateOf("") }
    var precioTexto by remember { mutableStateOf("") }
    var stockTexto by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Producto", color = Color.White) },
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
            modifier = Modifier.fillMaxSize().padding(paddingValores).padding(24.dp)
        ) {

            // Campo 1: Nombre (Texto Normal)
            OutlinedTextField(
                value = nombre, onValueChange = { nombre = it },
                label = { Text("Nombre del Producto") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), singleLine = true
            )

            // Campo 2: Precio (Teclado Numérico Decimal)
            OutlinedTextField(
                value = precioTexto, onValueChange = { precioTexto = it },
                label = { Text("Precio ($)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), singleLine = true
            )

            // Campo 3: Stock (Teclado Numérico Entero)
            OutlinedTextField(
                value = stockTexto, onValueChange = { stockTexto = it },
                label = { Text("Cantidad en Stock") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp), singleLine = true
            )

            Button(
                onClick = {
                    // Convertimos la caja de texto a número si es posible
                    val precioNum = precioTexto.toDoubleOrNull() ?: 0.0
                    val stockNum = stockTexto.toIntOrNull() ?: 0

                    // Si el nombre no está vacío y el precio es mayor a cero...
                    if (nombre.isNotBlank() && precioNum > 0) {

                        // EMPAQUETAMOS todo en nuestro "Molde" y lo enviamos a la ruta
                        val nuevoEmpaque = Producto(nombre, precioNum, stockNum)
                        onProductoCreado(nuevoEmpaque)

                        // Y regresamos a la pantalla de la lista
                        controladorNavegacion.navigateUp()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2ECC71))
            ) {
                Text("Guardar Producto", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
