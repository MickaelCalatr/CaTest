plugins {
    kotlin("kapt")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.antartic.sudio.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        buildConfig = true
    }
    flavorDimensions += "environment"
    productFlavors {
        create("mock") {
            dimension = "environment"
            buildConfigField("String", "BANK_BASE_URL", "\"https://cdf-test-mobile-default-rtdb.europe-west1.firebasedatabase.app\"")
        }
        create("prod") {
            dimension = "environment"
            buildConfigField("String", "BANK_BASE_URL", "\"https://cdf-test-mobile-default-rtdb.europe-west1.firebasedatabase.app\"")
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildToolsVersion = "34.0.0"
}

dependencies {
    //Retrofit
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    //Network
    api("com.squareup.okhttp3:okhttp:4.12.0")
    // Gson
    api("com.google.code.gson:gson:2.10.1")
    // Dagger-Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
