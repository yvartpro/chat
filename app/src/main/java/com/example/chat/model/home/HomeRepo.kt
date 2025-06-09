package com.example.chat.model.home

import com.example.chat.api.ApiClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

object HomeRepo {
	suspend fun getVideos() : VideoResp {
		return ApiClient.client.get("https://dummyjson.com/products").body()
	}
}