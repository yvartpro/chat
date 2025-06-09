package com.example.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.chat.ui.theme.ChatTheme
import com.example.chat.ui.app.nav.AppNavGraph

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ChatTheme {
				val navController = rememberNavController()
				AppNavGraph(navController = navController)
			}
		}
	}
}



