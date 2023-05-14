plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {
    namespace = "com.drus.githubsearch.networking"

    defaultConfig {
        minSdk = 24
        compileSdk = 33
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    api(project(":core"))

    implementation("androidx.core:core-ktx:1.10.1")

    val retrofitVersion = rootProject.extra.get("retrofitVersion")
    api("com.squareup.retrofit2:retrofit:$retrofitVersion")
    api("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    api("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    val okhttpVersion = rootProject.extra.get("okhttpVersion")
    api("com.squareup.okhttp3:okhttp:$okhttpVersion")
    api("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")

    // Dagger
    val daggerVersion = rootProject.extra.get("daggerVersion")
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")

    api("com.google.code.gson:gson:2.9.0")

}