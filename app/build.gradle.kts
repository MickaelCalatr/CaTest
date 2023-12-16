plugins {
    kotlin("kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}
apply {
    from("../flavor.gradle")
}

android {
    namespace = "com.antartic.sudio.catest"
    compileSdk = 34
    buildToolsVersion = "34.0.0"

    defaultConfig {
        applicationId = "com.antartic.sudio.catest"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":ui-ds"))

    implementation("androidx.core:core-ktx:1.9.0")
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.10")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    // Dagger-Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    kapt("com.google.dagger:hilt-compiler:2.48")
    testImplementation("com.google.dagger:hilt-android-testing:2.48")
    kaptTest("com.google.dagger:hilt-android-compiler:2.48")

    // SlashScreen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Junit
    testImplementation("junit:junit:4.13.2")
    // MokK
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("io.mockk:mockk-agent-jvm:1.13.8")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
