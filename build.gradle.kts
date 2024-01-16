buildscript {
    dependencies {
//        added for firebase buildscript{dependencies{firebase}}
        classpath("com.google.gms:google-services:4.4.0")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    //    Hilt
    id("com.google.dagger.hilt.android") version "2.48" apply false
//    Kapt to Ksp migration
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}