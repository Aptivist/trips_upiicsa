package com.aptivist.tripsupiicsaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aptivist.tripsupiicsaapp.domain.contracts.INavigationEmitter
import com.aptivist.tripsupiicsaapp.domain.contracts.INavigationReceiver
import com.aptivist.tripsupiicsaapp.ui.navigation.AppNavigation
import com.aptivist.tripsupiicsaapp.ui.theme.TripsUpiicsaAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationReceiver: INavigationReceiver

    @Inject
    lateinit var navigationEmitter: INavigationEmitter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            TripsUpiicsaAppTheme {
                AppNavigation(
                    navigationReceiver = navigationReceiver
                )
            }
        }
    }

}