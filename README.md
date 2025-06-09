# Vovota app
This is aimed to show the core part excluding the video editor

# Features

- User login with Ktor client
- Navigation is done with NavHostController
- Jetpack Compose for UI
- Data serialization

# Tech stack

- Kotlin
- Ktor Client
- Coroutine for asynchronous API calls

# Additional plugins

- Kotlin serialization plugin

# Related implementation dependencies

- implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
- implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
- implementation("io.ktor:ktor-client-core:2.3.5")
- implementation("io.ktor:ktor-client-cio:2.3.5")
- implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
- implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
- implementation("io.ktor:ktor-client-auth:2.3.5")
- implementation("androidx.navigation:navigation-compose:2.6.0")

# Test dependencies 

- testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3") //this is optional, just used for functional testing

## Steps to setup the API system

1. Add kotlin serialization plugin in build.gradle(app)
   - kotlin("plugin.serialization") version "2.0.20"
2. Add dependencies to build.gradle(app)
3. Enable permission of internet in AndroidManifest.xml
4. Go to com.example.chat.api and copy the content of ApiClient as it is the configuration of the Ktor client and kotlin serialization plugin
5. Check in com.example.chat.model, there are packages for each logical part ex: home, auth, message, ... 
   and each one contains a request and a response classes which define the format of data to be sent to backend (POST) or
   response for request from backend (GET). It contains also a Repo which holds "suspend methods" that are Coroutine functions to make 
   async call to backend. These use "ApiClient"
6. Next step is to use "Repo" in your Composable to interact with backend. 
7. Use LaunchedEffect(Unit)/  or pass a variable that will trigger reload or rememberCoroutineScope 
8. LaunchedEffect on page load and CoroutineScope on button click.
9. See example:
   val scope = rememberCoroutineScope() //this is used to launch a coroutine
   Button(
   onClick = { if(isLogin) {
   isLoading = true
   resultText = "Logging in..."
   //make request to the server
   scope.launch {
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
10. LaunchedEffect on task to be done when page loads:
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

    then call it in:
    LaunchedEffect(Unit)  {
    loadVideos()
    }
    
    or when a button is clicked: 
    Button(onClick = { scope.launch { loadVideos() } })

11. Setup and installation
    ```bash
    git clone
    git@github.com:yvartpro/chat.git Chat
    cd Chat

## Folder structure
com.example.chat/
---api/
---model/
---nav/
---screens/
---components/

## API URL is written in the ApiClient.kt