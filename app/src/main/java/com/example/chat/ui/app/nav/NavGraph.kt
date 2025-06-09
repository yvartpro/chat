package com.example.chat.ui.app.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chat.ui.app.screens.AuthScreen
import com.example.chat.ui.app.screens.HomeScreen
import com.example.chat.ui.app.screens.ProfileScreen

@Composable
fun AppNavGraph(navController: NavHostController,
								startDestination: String = Routes.Auth.route){
	NavHost(navController = navController,
		startDestination = startDestination) {
		composable(Routes.Auth.route){
			AuthScreen (onLoginSuccess = {
				navController.navigate(Routes.Home.route)
			})
		}
		composable(Routes.Home.route) {
			HomeScreen()
		}
		composable(Routes.Profile.route) Composable@{
				backStackEntry ->
			val userId = backStackEntry.arguments?.getString("userId") ?: return@Composable
			ProfileScreen (userId = userId)
		}
	}
}