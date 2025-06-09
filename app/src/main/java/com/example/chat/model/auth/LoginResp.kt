package com.example.chat.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginResp(
	val access: String,
	val refresh: String
)