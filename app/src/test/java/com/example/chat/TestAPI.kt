package com.example.chat

import kotlinx.serialization.Serializable
import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.coroutines.test.runTest
import org.junit.Test
class TestAPI {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }
    @Test
    fun fetchUsers() = runBlocking {
        val url = "http://127.0.0.1:8000/users/"
        val users: List<User> = client.get(url).body()
        println("Fetched ${users.size} users")
        users.forEach {
            println("${it.id}: ${it.username} (${it.email})")
        }
    }
    @Test
    fun addUsers () = runTest {
        val url = "http://127.0.0.1:8000/users/"
        val username = "marko"
        val email = "mark@gmail.com"
        val password = "12345"

        val user = NewUser(username = username, email = email, password = password)
        val response: User = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
        println(response)
    }
}
@Serializable
data class User(
    val id: Int,
    val username: String,
    val email: String,
)

@Serializable
data class NewUser(
    val username: String,
    val email: String,
    val password: String
)
