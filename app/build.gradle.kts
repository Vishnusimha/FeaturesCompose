plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //    hilt
    id("com.google.dagger.hilt.android")
//    TODO since added ksp check if kapt plugin can be removed
    id("kotlin-kapt")
//    Kapt to Ksp migration
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.vishnu.featurescompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vishnu.featurescompose"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

//    coil for image loading (alternative for glide)
    implementation("io.coil-kt:coil-compose:2.5.0")

//    Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
//    moshi
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")

// Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    kapt("androidx.hilt:hilt-compiler:1.1.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

// Compose
    implementation("androidx.compose.foundation:foundation:1.5.4")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui-tooling:1.5.4")

// Material Design for Card
    implementation("androidx.compose.material:material:1.5.4")

// CardView and Navigation Compose
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.navigation:navigation-compose:2.7.6")

// OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

// Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

// View Model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

// Gson
    implementation("com.google.code.gson:gson:2.10")
// Paging
    implementation ("androidx.paging:paging-runtime-ktx:3.2.1")
    implementation ("androidx.paging:paging-compose:3.2.1")
    // Room
    implementation ("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
//    ksp("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-paging:2.6.1")
}

// TODO check if its really needed for hilt
// Added to support hilt dependencies
kapt {
    correctErrorTypes = true
}