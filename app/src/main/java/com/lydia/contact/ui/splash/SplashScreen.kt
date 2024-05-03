package com.lydia.contact.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.lydia.contact.R
import com.lydia.contact.navigation.Route
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(Unit) {
        delay(3000L)
        val currentRoute = navBackStackEntry?.destination?.route
        val shouldPopUpToStartDestination = currentRoute != Route.Contacts.route

        val navOptions = if (shouldPopUpToStartDestination) {
            navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                }
            }
        } else {
            null
        }
        navController.navigate(Route.Contacts.route, navOptions)
    }

    SplashContent()
}

@Composable
fun SplashContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_lydia),
            contentDescription = null
        )
    }
}