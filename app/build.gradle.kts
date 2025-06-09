plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	kotlin("plugin.serialization") version "2.0.20"
}

android {
	namespace = "com.example.chat"
	compileSdk = 35
	defaultConfig {
		applicationId = "com.example.chat"
		minSdk = 24
		targetSdk = 35
		versionCode = 1
		versionName = "1.0"
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}
	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
	buildFeatures {
		compose = true
	}
	testOptions {
		unitTests.all {
			it.testLogging {
				events("passed", "failed", "skipped", "standardOut", "standardError")
				showStandardStreams = true
			}
		}
	}
}

dependencies {
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
	implementation("io.ktor:ktor-client-core:2.3.5")
	implementation("io.ktor:ktor-client-cio:2.3.5")
	implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
	implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
	implementation("io.ktor:ktor-client-auth:2.3.5")
	implementation("androidx.navigation:navigation-compose:2.6.0")
	testImplementation(libs.junit)
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)
}
