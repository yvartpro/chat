package com.example.chat.model.home

import kotlinx.serialization.Serializable

@Serializable
data class VideoResp(
	val products: List<Video>
)

@Serializable
data class Video(
	val id: Int,
	val title: String,
	val description: String,
	val thumbnail: String
)
