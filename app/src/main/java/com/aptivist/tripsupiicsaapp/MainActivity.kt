package com.aptivist.tripsupiicsaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aptivist.tripsupiicsaapp.ui.navigation.AppNavigation
import com.aptivist.tripsupiicsaapp.ui.theme.TripsUpiicsaAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            TripsUpiicsaAppTheme {
                AppNavigation()
            }
        }
    }

}