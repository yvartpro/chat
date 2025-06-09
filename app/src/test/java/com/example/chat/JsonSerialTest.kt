package com.example.chat

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test

class JsonSerialTest {
	@Serializable
	data class Message(val msg: String)
	
	@Test
	fun testJsonEncode(){
		val message = Message("Hello, World!")
		val json = Json.encodeToString(message)
		println(json)
	}
	
	@Test
	fun testJsonDecode(){
		val jsonStr = """{"msg": "Hello World !"}"""
		val res = Json.decodeFromString<Message>(jsonStr)
		println(res)
	}
}