plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.android.library") version "8.1.1" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
}
allprojects {
    ext {
        set("compileSdk", 34)
        set("minSdk", 24)
        set("targetSdk", 34)
        set("versionCode", 1)
        set("versionName", "1.0.0")
        set("buildToolsVersion", "34.0.0")
    }
}
