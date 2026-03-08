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

    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.weather_app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.weather_app"
        minSdk = 30
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

            buildConfigField("String" ,"COUNTRY_URL", "\"https://api.api-ninjas.com/v1\"")

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
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0" //Check latest version
    }

    // Solve: 13 files found with path 'META-INF/INDEX.LIST' from inputs:
    packaging {
        resources {
            excludes += listOf(
                "META-INF/LICENSE*",
                "META-INF/DEPENDENCIES",
                "META-INF/INDEX.LIST",
                "META-INF/io.netty.versions.properties"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.navigation.compose.android)
    implementation(libs.firebase.appdistribution.gradle)
    implementation(libs.androidx.contentpager)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Material Design 3
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)

    // Android Studio Preview support
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    // Integration with activities
    implementation(libs.androidx.activity.compose)

    // Integration with ViewModels
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Splash API
    implementation(libs.androidx.core.splashscreen)

    // Navigation 3
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.material3.adaptive.navigation)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // Hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Retrofit2
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson)

    // Coil
    implementation(libs.io.coil.kt)

    // Gson
    implementation(libs.gson.android)

    // Skeleton shimmer
    implementation(libs.shimmer.android)

    implementation(libs.play.services.auth)
}