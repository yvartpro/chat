package com.example.chat.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginReq(
	val email :String,
	val password: String
)