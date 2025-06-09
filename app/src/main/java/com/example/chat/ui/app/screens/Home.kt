package com.example.chat.ui.app.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chat.model.home.HomeRepo
import com.example.chat.model.home.Video
import com.example.chat.ui.app.components.HeadingText
import com.example.chat.ui.app.components.NormalText
import kotlinx.coroutines.launch


@Composable
fun  HomeScreen (){
	val name by rememberSaveable { mutableStateOf("Vovota") }
	var videos by rememberSaveable { mutableStateOf<List<Video>>(emptyList()) }
	var isLoading by rememberSaveable { mutableStateOf(true) }
	var error by rememberSaveable { mutableStateOf<String?>(null) }
	val scope = rememberCoroutineScope()
	suspend fun loadVideos() {
		isLoading = true
		error = null
		try {
			videos = HomeRepo.getVideos().products
		} catch (e: Exception) {
			error = e.message
		} finally {
			isLoading = false
		}
	}
	
	Column (
		modifier = Modifier.fillMaxWidth()
			.verticalScroll(rememberScrollState())
	){
		HeadingText(value = "Hello $name", modifier = Modifier.padding(top = 24.dp))
		TextButton (
			onClick = { }
		){
			NormalText("Profile")
		}
	}
	LaunchedEffect(Unit)  {
		loadVideos()
	}
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	){
		when {
			isLoading -> {
				CircularProgressIndicator()
			}
			error != null -> {
				Text(error!!)
				TextButton(onClick = {
					scope.launch { //run coroutine
						loadVideos()
					}
				}) {
					NormalText("Retry")
				}
			}
			else -> {
				LazyColumn (
					modifier = Modifier.fillMaxWidth()
						.padding(top = 48.dp)
						.padding(horizontal = 24.dp)
				) {
					items(videos) { video ->
						NormalText(value = video.title)
						Spacer(modifier = Modifier.height(8.dp))
						Text(video.description)
					}
				}
			}
		}
	}
}