plugins {
    kotlin("kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.antartic.sudio.catest"
    compileSdk = 34
    buildToolsVersion = "34.0.0"

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
            buildConfigField("boolean", "IS_MOCK", "true")
        }
        create("prod") {
            dimension = "environment"
            buildConfigField("boolean", "IS_MOCK", "false")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
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

    // MokK
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("io.mockk:mockk-agent-jvm:1.13.8")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
