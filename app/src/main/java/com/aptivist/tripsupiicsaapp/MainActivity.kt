package com.aptivist.tripsupiicsaapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import com.aptivist.tripsupiicsaapp.data.local.navigation.NavigationAction
import com.aptivist.tripsupiicsaapp.domain.contracts.INavigationEmitter
import com.aptivist.tripsupiicsaapp.domain.contracts.INavigationReceiver
import com.aptivist.tripsupiicsaapp.ui.navigation.AppNavigation
import com.aptivist.tripsupiicsaapp.ui.navigation.AppRoutes
import com.aptivist.tripsupiicsaapp.ui.navigation.AppRoutesArgs
import com.aptivist.tripsupiicsaapp.ui.theme.TripsUpiicsaAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationReceiver: INavigationReceiver

    @Inject
    lateinit var navigationEmitter: INavigationEmitter

    private var uriToOpen: String? = null


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        handleIntent(intent)
        setContent {
            TripsUpiicsaAppTheme {
                AppNavigation(
                    navigationReceiver = navigationReceiver
                )
            }

            LaunchedEffect(Unit) {
                delay(500)
                uriToOpen?.let {
                    navigationEmitter.post(
                        NavigationAction.NavigateToWithArgs(
                            route = AppRoutes.IMPORT_TRIPS,
                            args = mapOf(AppRoutesArgs.IMPORT_TRIPS_FILE to Uri.encode(it))
                        )
                    )
                    uriToOpen = null
                }
            }
        }


    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_VIEW) {
            intent.data?.let { uri ->
                uriToOpen = uri.toString()
            }
        }
    }
}