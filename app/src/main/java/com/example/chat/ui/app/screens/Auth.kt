package com.example.chat.ui.app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat.R
import com.example.chat.model.auth.AuthRepo
import com.example.chat.model.auth.LoginReq
import com.example.chat.ui.theme.blueVt
import com.example.chat.ui.app.components.*
import kotlinx.coroutines.launch
import android.util.Log


@Composable
fun AuthScreen(onLoginSuccess :() -> Unit) {
	var isLogin by rememberSaveable { mutableStateOf(true) }
	var nickname by rememberSaveable { mutableStateOf("") }
	var email by rememberSaveable { mutableStateOf("") }
	var password by rememberSaveable { mutableStateOf("") }
	var isLoading by rememberSaveable { mutableStateOf(false) }
	var resultText by rememberSaveable { mutableStateOf("")}
	val coroutineScope = rememberCoroutineScope()
	var access by remember { mutableStateOf("") }
	var refresh by remember { mutableStateOf("") }
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(0.dp)
			.background(Color.White)
			.verticalScroll(rememberScrollState()),
		verticalArrangement = Arrangement.Top,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Spacer(modifier = Modifier.height(48.dp))
		Image(
			painter = painterResource(id = R.drawable.logo),
			contentDescription = "DUN",
			modifier = Modifier.size(80.dp),
			contentScale = ContentScale.Fit
		)
		Spacer(modifier = Modifier.height(32.dp))
		Text(
			text = if (isLogin) "Login" else "Register",
			style = TextStyle(
				fontSize = 24.sp,
				fontWeight = FontWeight.Bold,
				color = blueVt
			)
		)
		Spacer(modifier = Modifier.height(16.dp))
		if (isLoading){
			SmallText(text = resultText, color = if(isLoading) blueVt else Color.Red)
		}
		Spacer(modifier = Modifier.height(16.dp))
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 16.dp, horizontal = 24.dp),
			verticalArrangement = Arrangement.spacedBy(16.dp)
		){
			if(!isLogin) {
				InputField(
					value = nickname,
					onValueChange = { nickname = it },
					labelText = "Nickname",
					keyboardType = KeyboardType.Text,
					modifier = Modifier.fillMaxWidth()
				)
			}
			InputField(
				value = email,
				onValueChange = { email = it },
				labelText = "Email address",
				keyboardType = KeyboardType.Email,
				modifier = Modifier.fillMaxWidth()
			)
			PasswordField(
				value = password,
				onValueChange = { password = it },
				labelText = "Password",
				keyboardType = KeyboardType.Password,
				modifier = Modifier.fillMaxWidth()
			)
		}
		if(!isLogin){
			Row(
				modifier = Modifier.fillMaxWidth()
					.padding(vertical = 0.dp),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.End
			){
				TextButton(onClick = {}){
					Text(text = "Forgot password ?")
				}
			}
			Spacer(modifier = Modifier.height(8.dp))
			PrivacyText()//privacy text
		}
		Spacer(modifier = Modifier.height(16.dp))
		Button(
			onClick = { if(isLogin) {
				isLoading = true
				resultText = "Logging in..."
				//make request to the server
				coroutineScope.launch {
					try{
						val resp = AuthRepo.login(LoginReq(email,password))
						resultText = "Access ${resp.access } Refresh ${resp.refresh}"
						onLoginSuccess()
					}catch(e: Exception){
						resultText = "Login failed: ${e.message}"
						Log.e("AUTH", e.message.toString())
					}finally{
						isLoading = false
					}
				}
			} else {isLogin = true}},
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 24.dp),
			shape = RoundedCornerShape(2.dp)
		){
			Text(text = if(isLogin) "Login" else "Register")
		}
		Spacer(modifier = Modifier.height(8.dp))
		Row(
			modifier = Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.Center
		){
			Text(text = if (isLogin) "You are new here ? " else "Already have an account ? ")
			TextButton(onClick = { isLogin = !isLogin}){
				Text(text = if(isLogin) "Register now" else "Login here")
			}
		}
	}
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PrivacyText() {
	val uriHandler = LocalUriHandler.current
	val url = "https://vovota.bi/privacy"
	FlowRow(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 24.dp),
		horizontalArrangement = Arrangement.Start,
		verticalArrangement = Arrangement.Center
	) {
		SmallText("Note: By registering, you accept our ")
		
		SmallText(
			text = "privacy policy",
			color = blueVt,
			fontWeight = FontWeight.Bold,
			textDecoration = TextDecoration.Underline,
			modifier = Modifier
				.clickable {
					try {
						uriHandler.openUri(url)
					} catch (e: Exception) {
						println("Could not open URI: ${e.message}")
					}
				}
				.padding(start = 2.dp)
		)
	}
}
