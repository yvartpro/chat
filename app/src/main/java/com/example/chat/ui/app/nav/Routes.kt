package com.example.chat.ui.app.nav

sealed class Routes(val route: String){
	data object Auth: Routes("auth")
	data object Home: Routes("home")
	data object Profile: Routes("profile/{userId}"){
		fun createRoute(userId: String) = "profile/$userId"
	}
}