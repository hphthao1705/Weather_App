plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    //Add this
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.20"
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt") //for 'kapt'

    //Serialization
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.example.weather_app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.weather_app"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    //Add this
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0" //Check latest version
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.navigation.compose.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Material Design 3
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)

    //Android Studio Preview support
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    //Integration with activities
    implementation(libs.androidx.activity.compose)

    //Integration with ViewModels
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //Splash API
    implementation(libs.androidx.core.splashscreen)

    //Navigation 3
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.material3.adaptive.navigation)
    implementation(libs.kotlinx.serialization.core)

    //Hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
}