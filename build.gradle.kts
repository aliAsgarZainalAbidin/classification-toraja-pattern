// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // UPDATE: Sesuaikan versi KSP agar lebih kompatibel
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
    id("com.android.application") version "8.2.2" apply false
    // UPDATE: Sesuaikan versi Kotlin agar cocok dengan KSP
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    // PASTIKAN: Versi ini akan menjadi satu-satunya sumber kebenaran untuk Hilt
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}