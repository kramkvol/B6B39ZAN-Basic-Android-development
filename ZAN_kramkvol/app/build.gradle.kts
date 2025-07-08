plugins {
    alias(libs.plugins.android.application) // Android application plugin
    alias(libs.plugins.kotlin.android)      // Kotlin plugin for Android
    alias(libs.plugins.kotlin.compose)      // Jetpack Compose support
    id("kotlin-kapt")                       // Kotlin annotation processing (used by Room, etc.)
}

android {
    namespace = "cz.cvut.fel.zan_kramkvol"
    compileSdk = 35

    defaultConfig {
        applicationId = "cz.cvut.fel.zan_kramkvol"
        minSdk = 24                         // app supports devices running API 24 (Android 7.0) and above
        targetSdk = 35                      // app is fully tested and optimized for Android 14
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Disables code shrinking and obfuscation for release builds
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

    buildFeatures {
        compose = true // Enables Jetpack Compose
    }
}

dependencies {
    // Core Android and Jetpack Compose dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Optional: useful for Compose Preview in Android Studio
    debugImplementation(libs.androidx.ui.tooling)

    // Room database
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // Material icons
    implementation("androidx.compose.material:material-icons-extended")

    // Jetpack Navigation for Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Retrofit for REST API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // DataStore for settings
    implementation("androidx.datastore:datastore-preferences:1.0.0")
}
