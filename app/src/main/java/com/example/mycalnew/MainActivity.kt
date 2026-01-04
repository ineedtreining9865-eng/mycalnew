package com.example.mycalnew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.mycalnew.ui.theme.MycalnewTheme
import com.example.mycalnew.ui.navigation.NavGraph

/**
 * Entry point of the app. Sets the Compose content and launches the navigation graph.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MycalnewTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    NavGraph()
                }
            }
        }
    }
}
