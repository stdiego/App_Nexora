package com.example.app_nexora

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaConfiguracion(controladorNavegacion: NavHostController) {
    // Memorias simples de encendido (true) o apagado (false)
    var notificacionesActivas by remember { mutableStateOf(true) }
    var modoOscuroActivo by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración", color = Color.White) },
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

            // 1. Tarjeta superior del Perfil
            Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Círculo simulando la foto de perfil en azul
                    Surface(modifier = Modifier.size(64.dp), shape = CircleShape, color = Color(0xFF3498DB)) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.White, modifier = Modifier.padding(16.dp))
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text("Juan Pérez", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D1B2A))
                        Text("Dueño del Negocio", color = Color.Gray, fontSize = 14.sp)
                    }
                }
            }

            // 2. Lista de configuraciones interactivas
            Text("PREFERENCIAS", fontWeight = FontWeight.Bold, color = Color.Gray, modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp))

            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    // Switch 1: Notificaciones
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.Gray)
                            Text("Notificaciones Push", modifier = Modifier.padding(start = 16.dp), fontSize = 16.sp)
                        }
                        // Botón encendible de Android
                        Switch(
                            checked = notificacionesActivas,
                            onCheckedChange = { nuevoEstado -> notificacionesActivas = nuevoEstado },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF2ECC71), checkedTrackColor = Color(0xFF2ECC71).copy(alpha = 0.5f))
                        )
                    }

                    HorizontalDivider() // Línea separadora gris

                    // Switch 2: Modo Oscuro
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Gray)
                            Text("Modo Oscuro (Beta)", modifier = Modifier.padding(start = 16.dp), fontSize = 16.sp)
                        }
                        Switch(checked = modoOscuroActivo, onCheckedChange = { modoOscuroActivo = it })
                    }
                }
            }

            // Spacer toma TODO el espacio sobrante que haya. ¡Empuja el botón de abajo al fondo de la pantalla!
            Spacer(modifier = Modifier.weight(1f))

            // 3. Botón destructivo de Cerrar Sesión
            Button(
                onClick = {
                    // popUpTo = Le ordenamos a la app que elimine al Dashboard entero del caché y vuelva limpio a la raíz del Login
                    controladorNavegacion.navigate("login") {
                        popUpTo("inicio") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE74C3C)) // Rojo Alerta
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Salir", tint = Color.White, modifier = Modifier.padding(end = 8.dp))
                Text("Cerrar Sesión", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
