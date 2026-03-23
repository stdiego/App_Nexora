package com.example.app_nexora

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// 1. crear un "molde" sencillo para la info de cada pantalla
data class InformacionPantalla(
    val titulo: String,
    val descripcion: String,
    val icono: ImageVector,
    val colorIcono: Color
)

@Composable
fun PantallaBienvenida(controladorNavegacion: NavHostController) {
    // 2. Aquí están lsa 3 pantallas reales
    val pantallas = listOf(
        InformacionPantalla(
            "Vende y factura fácilmente",
            "Cumple con la facturación electrónica DIAN desde tu celular de forma rápida y segura.",
            Icons.Default.Check, Color(0xFF2ECC71) // Verde
        ),
        InformacionPantalla(
            "Organiza tu negocio en un solo lugar",
            "Gestiona tus productos, registra ventas y visualiza reportes claros en segundos.",
            Icons.Default.List, Color(0xFF3498DB) // Azul
        ),
        InformacionPantalla(
            "Comienza con 7 días de Premium",
            "Disfruta todas las herramientas avanzadas sin costo durante tus primeros 7 días.",
            Icons.Default.Star, Color(0xFFF39C12) // Naranja
        )
    )

    // 3. ESTADO: Esto le dice a la app en qué paso va (0, 1 o 2)
    var pasoActual by remember { mutableIntStateOf(0) }

    // se obtiene la información de la pantalla en la que estamos
    val pantallaActual = pantallas[pasoActual]

    Column(
        modifier = Modifier
            .fillMaxSize().background(Color(0xFF0D1B2A))
            .systemBarsPadding().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // --- PARTE SUPERIOR ---
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = { controladorNavegacion.navigate("login") }) {
                Text(text = "Saltar", color = Color.Gray)
            }
        }

        // --- PARTE CENTRAL DINÁMICA ---
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // El icono lee el color y la imagen de "pantallaActual"
            Icon(
                imageVector = pantallaActual.icono,
                contentDescription = "Icono",
                tint = pantallaActual.colorIcono,
                modifier = Modifier.size(100.dp).padding(bottom = 24.dp)
            )

            Text(
                text = pantallaActual.titulo,
                color = Color.White, fontSize = 26.sp,
                fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = pantallaActual.descripcion,
                color = Color.LightGray, fontSize = 16.sp,
                textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // BOTÓN INFERIOR INTELIGENTE
        Button(
            onClick = {
                // Si ya estamos en la última (la 2), vamos al Login. Si no, avanzamos a la siguiente (+1)
                if (pasoActual == pantallas.size - 1) {
                    controladorNavegacion.navigate("login")
                } else {
                    pasoActual += 1
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2ECC71)),
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            // El texto del botón también cambia si es la última pantalla
            if (pasoActual == pantallas.size - 1) {
                Text("Comenzar", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
            } else {
                Text("Siguiente", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
