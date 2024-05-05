package com.lydia.contact.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lydia.contact.navigation.Route
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lydia.contact.ui.contactdetail.ContactDetailScreen
import com.lydia.contact.ui.contacts.ContactsScreen
import com.lydia.contact.ui.splash.SplashScreen

import com.lydia.contact.ui.theme.ContactManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactManagerTheme {
                MainActivityContent()
            }
        }
    }
}

@Composable
fun MainActivityContent() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Splash.route,
        modifier = Modifier.fillMaxSize()
    ) {

        composable(
            route = Route.Splash.route
        ) {
            SplashScreen(
                navController = navController
            )
        }

        composable(
            route = Route.Contacts.route
        ) {
            ContactsScreen(
                onNavigateUp = { contactId ->
                    navController.navigate("${Route.ContactDetail.route}/$contactId")
                })
        }

        composable(
            route = "${Route.ContactDetail.route}/{contactId}",
            arguments = listOf(
                navArgument(name = "contactId") {
                    type = NavType.IntType
                }
            )
        ) {
            ContactDetailScreen(navController)
        }
    }
}