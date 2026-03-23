package com.example.app_nexora

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLogin(controladorNavegacion: NavHostController) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mostrarError by remember { mutableStateOf(false) }

    // COLORES EXACTOS DE TU MOCKUP
    val verdeFondoMenu = Color(0xFF27AE60) // Verde de la cabecera
    val fondoGrisOscuro = Color(0xFF4A625A) // Grisáceo del fondo inferior
    val verdeBoton = Color(0xFF00D166) // Verde brillante neón del botón
    val fondoLogo = Color(0xFF131B2A)  // Azul muy oscuro del logo
    val cianLogo = Color(0xFF00FFD1)   // Letra N

    // 1. FONDO CON EFECTO DE GRADIENTE (Degradado)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(verdeFondoMenu, fondoGrisOscuro)
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(72.dp))

            // 2. LOGO FLOTANTE DE NEXORA
            Surface(
                modifier = Modifier.size(110.dp),
                shape = RoundedCornerShape(28.dp),
                color = fondoLogo,
                shadowElevation = 8.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("N", color = cianLogo, fontSize = 72.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. TEXTOS PREMIUM DE PRESENTACIÓN
            Text("Nexora POS", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text("Sistema Inteligente de Punto de Venta", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text("Facturación electrónica en tiempo real", color = Color(0xFFD5D8DC), fontSize = 13.sp)

            Spacer(modifier = Modifier.height(36.dp))

            // 4. TARJETA BLANCA DEL FORMULARIO
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp), // Esquinas bien redondas como en React
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Iniciar Sesión", fontSize = 24.sp, color = Color(0xFF333333))
                    Spacer(modifier = Modifier.height(24.dp))

                    // TextField 1: Correo
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text("Usuario", fontSize = 12.sp, color = Color.DarkGray, fontWeight = FontWeight.Medium, modifier = Modifier.padding(bottom = 6.dp))
                        OutlinedTextField(
                            value = correo,
                            onValueChange = { correo = it; mostrarError = false },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray) },
                            placeholder = { Text("nombre@empresa.com", color = Color.Gray) },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp), // Caja cuadradita
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray,
                                focusedBorderColor = verdeBoton,
                                cursorColor = verdeBoton
                            )
                        )
                        Text("Ingresa tu correo corporativo", fontSize = 11.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // TextField 2: Contraseña
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text("Contraseña", fontSize = 12.sp, color = Color.DarkGray, fontWeight = FontWeight.Medium, modifier = Modifier.padding(bottom = 6.dp))
                        OutlinedTextField(
                            value = contrasena,
                            onValueChange = { contrasena = it; mostrarError = false },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = if (mostrarError) Color.Red else Color.Gray) },
                            placeholder = { Text("••••••••", color = Color.Gray) },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = if (mostrarError) Color.Red else Color.LightGray,
                                focusedBorderColor = if (mostrarError) Color.Red else verdeBoton,
                                errorBorderColor = Color.Red,
                                errorContainerColor = if (mostrarError) Color(0xFFB71C1C) else Color.Transparent, // Fondo de la captura de error
                            ),
                            isError = mostrarError
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // CAJA ROSADA DE ERROR (Idéntica a la Foto 1)
                    if (mostrarError) {
                        Surface(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFFFEBEE), // Fondo rojito claro
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD32F2F))
                        ) {
                            Text(
                                text = "Usuario y contraseña son requeridos",
                                color = Color(0xFFB71C1C), // Letra roja oscura
                                fontSize = 13.sp,
                                modifier = Modifier.padding(vertical = 12.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // 5. BOTÓN MÁGICO NEÓN
                    Button(
                        onClick = {
                            if (correo.isNotBlank() && contrasena.isNotBlank()) {
                                controladorNavegacion.navigate("inicio") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                mostrarError = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(26.dp), // Botón en forma de píldora
                        colors = ButtonDefaults.buttonColors(containerColor = verdeBoton)
                    ) {
                        Text("Iniciar Sesión", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // TEXTO INFERIOR
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        color = verdeBoton,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { /* Acción nula por ahora */ }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Empujamos el texto de abajo hasta el límite de la pantalla

            // 6. FOOTER BLANCO FLOTANTE
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 24.dp)) {
                Text("¿No tienes cuenta? ", color = Color.White, fontSize = 13.sp)
                Text(
                    text = "Solicitar acceso",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline,
                    modifier = Modifier.clickable { }
                )
            }
        }
    }
}
