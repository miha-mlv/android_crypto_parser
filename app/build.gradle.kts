plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.cryptoapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cryptoapp"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    val room_version = "2.7.1"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")

    implementation("com.squareup.picasso:picasso:2.71828")

    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    val lifecycle_version = "2.8.7"
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")

    implementation("androidx.work:work-runtime-ktx:2.10.1")

    implementation("com.google.dagger:dagger:2.56.2")
    kapt("com.google.dagger:dagger-compiler:2.56.2")



}