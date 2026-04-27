package com.example.app_nexora

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun PantallaEditarProducto(
    controladorNavegacion: NavHostController,
    producto: Producto?,                              // El producto actual a mostrar/editar
    onProductoEditado: (Producto) -> Unit             // Callback para guardar los cambios
) {

    // Si no se encontró el producto, mostramos pantalla de error
    if (producto == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Producto no encontrado", color = Color.Gray)
        }
        return
    }

    // --- ESTADOS: Pre-llenamos los campos con los datos actuales del producto ---
    var nombre     by remember { mutableStateOf(producto.nombre) }
    var precioTexto by remember { mutableStateOf(producto.precio.toString()) }
    var stockTexto  by remember { mutableStateOf(producto.cantidadEnStock.toString()) }

    // Para mostrar errores de validación
    var errorNombre by remember { mutableStateOf(false) }
    var errorPrecio by remember { mutableStateOf(false) }
    var errorStock  by remember { mutableStateOf(false) }

    val colorVerde  = Color(0xFF00D166)
    val colorFondo  = Color(0xFFF3F4F6)
    val colorOscuro = Color(0xFF1E272E)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Producto", color = Color.White) },
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
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValores)
                .background(colorFondo)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // --- TARJETA: Info actual del producto (solo lectura, visual) ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Estado actual", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Nombre
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Nombre", color = Color.Gray, fontSize = 11.sp)
                            Text(producto.nombre, color = colorOscuro, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        }
                        // Precio
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Precio", color = Color.Gray, fontSize = 11.sp)
                            Text("$${"%.2f".format(producto.precio)}", color = colorVerde, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                        // Stock
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Stock", color = Color.Gray, fontSize = 11.sp)
                            Text("${producto.cantidadEnStock} uds.", color = colorOscuro, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text("MODIFICAR DATOS", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)

            // --- CAMPO 1: Nombre ---
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it; errorNombre = false },
                label = { Text("Nombre del Producto") },
                isError = errorNombre,
                supportingText = {
                    if (errorNombre) Text("El nombre no puede estar vacío", color = Color.Red, fontSize = 11.sp)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorVerde,
                    cursorColor = colorVerde
                )
            )

            // --- CAMPO 2: Precio ---
            OutlinedTextField(
                value = precioTexto,
                onValueChange = { precioTexto = it; errorPrecio = false },
                label = { Text("Precio ($)") },
                isError = errorPrecio,
                supportingText = {
                    if (errorPrecio) Text("Ingresa un precio válido mayor a 0", color = Color.Red, fontSize = 11.sp)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorVerde,
                    cursorColor = colorVerde
                )
            )

            // --- CAMPO 3: Stock ---
            OutlinedTextField(
                value = stockTexto,
                onValueChange = { stockTexto = it; errorStock = false },
                label = { Text("Cantidad en Stock") },
                isError = errorStock,
                supportingText = {
                    if (errorStock) Text("El stock no puede ser negativo", color = Color.Red, fontSize = 11.sp)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorVerde,
                    cursorColor = colorVerde
                )
            )

            Spacer(modifier = Modifier.weight(1f)) // Empuja el botón al fondo

            // --- BOTÓN GUARDAR ---
            Button(
                onClick = {
                    // Validaciones
                    val precioNum = precioTexto.toDoubleOrNull()
                    val stockNum  = stockTexto.toIntOrNull()

                    errorNombre = nombre.isBlank()
                    errorPrecio = precioNum == null || precioNum <= 0
                    errorStock  = stockNum == null || stockNum < 0
                    errorStock  = stockNum == null || stockNum < 0

                    // Si todo está bien, armamos el producto actualizado y lo enviamos
                    if (!errorNombre && !errorPrecio && !errorStock) {
                        val productoActualizado = Producto(
                            nombre = nombre.trim(),
                            precio = precioNum!!,
                            cantidadEnStock = stockNum!!
                        )
                        onProductoEditado(productoActualizado)
                        controladorNavegacion.navigateUp()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorVerde)
            ) {
                Text("Guardar Cambios", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
