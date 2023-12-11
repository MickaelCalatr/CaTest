plugins {
    kotlin("kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.antartic.sudio.catest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.antartic.sudio.catest"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        buildConfig = true
    }
    flavorDimensions += "environment"
    productFlavors {
        create("mock") {
            dimension = "environment"
        }
        create("prod") {
            dimension = "environment"
        }
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
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
    project(":data")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("com.google.android.material:material:1.10.0")

    // Dagger-Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    kapt("com.google.dagger:hilt-compiler:2.48")
    testImplementation("com.google.dagger:hilt-android-testing:2.48")
    kaptTest("com.google.dagger:hilt-android-compiler:2.48")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    testImplementation("junit:junit:4.13.2")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
