package com.example.app_nexora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.app_nexora.ui.theme.App_NexoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge() // Esto hace que la app ocupe toda la pantalla de arriba a abajo

        setContent {
            // Este es el "Tema" visual basico de la app (colores, textos)
            App_NexoraTheme {

                // Surface es como un lienzo blanco que cubre todo el teléfono
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 1. Creamos el "controlador", que es quien sabe ir de una pantalla a otra
                    val controlador = rememberNavController()

                    // 2. Le pasamos el controlador a tu archivo de Navegacion
                    NavegacionPrincipal(controladorNavegacion = controlador)
                }
            }
        }
    }
}
