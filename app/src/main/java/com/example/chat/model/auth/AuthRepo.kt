package com.example.chat.model.auth

import com.example.chat.api.ApiClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

object AuthRepo{
	suspend fun login(request: LoginReq) : LoginResp {
		return ApiClient.client.post("https://vovota.bi/rlogin/") {
			contentType(ContentType.Application.Json)
			setBody(request)
		}.body()
	}
}