plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "1.9.22"
}

android {
    namespace = "yolov8tflite"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.devalid.yolov8tflite"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.activity:activity:1.10.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    val cameraxVersion = "1.4.2"
    implementation("androidx.camera:camera-camera2:${cameraxVersion}")
    implementation("androidx.camera:camera-lifecycle:${cameraxVersion}")
    implementation("androidx.camera:camera-view:${cameraxVersion}")

    implementation("org.tensorflow:tensorflow-lite:2.16.1")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.4")

    implementation("org.tensorflow:tensorflow-lite-gpu-delegate-plugin:0.4.4")
    implementation("org.tensorflow:tensorflow-lite-gpu-api:2.16.1")
    implementation("org.tensorflow:tensorflow-lite-api:2.16.1")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.16.1")
    implementation("org.tensorflow:tensorflow-lite-select-tf-ops:2.16.1")

    implementation("org.tensorflow:tensorflow-lite-metadata:0.4.4")

    // FIX: Hapus variabel hiltVersion lokal. Versi sekarang diatur di level proyek.
    // val hiltVersion = "2.48.1" // <-- HAPUS INI

    //Dependency Injection
    // Gunakan versi yang sama dengan plugin di level proyek
    implementation("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-android-compiler:2.51.1")
    // FIX: Hapus dependensi ini, tidak diperlukan untuk Android dan bisa menyebabkan konflik.
    // ksp("com.google.dagger:hilt-compiler:$hiltVersion")

    //viewModel Scope
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.1")

    //fragmentx
    implementation("androidx.fragment:fragment-ktx:1.8.8")

    val nav_version = "2.7.7" // Versi nav_version yang lebih stabil

    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Feature module support for Fragments
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    // JSON serialization library, works with the Kotlin serialization plugin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    //coil
    implementation("io.coil-kt.coil3:coil:3.2.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.2.0")
}
