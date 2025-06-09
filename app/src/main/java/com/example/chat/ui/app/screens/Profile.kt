package com.example.chat.ui.app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(userId: String){
	Column (modifier = Modifier) {
		Text(text = "Viewing profile $userId")
	}
}