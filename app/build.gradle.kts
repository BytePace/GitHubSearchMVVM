plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
    id("kotlin-kapt")
}

android {
    namespace = "com.drus.githubsearch.mvvm"

    defaultConfig {
        applicationId = "com.drus.githubsearch.mvvm"
        minSdk = 24
        compileSdk = 33
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
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
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation(project(":core"))
    implementation(project(":networking"))
    implementation(project(":search"))
    implementation(project(":navigation"))

    //Dagger
    val daggerVersion = rootProject.extra.get("daggerVersion")

    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
    implementation("com.google.dagger:dagger-android-support:$daggerVersion")
    kapt("com.google.dagger:dagger-android-processor:$daggerVersion")


    // Yatagan
    val yataganVersion = rootProject.extra.get("yataganVersion")

    // Use reflection in debug builds.
    debugApi("com.yandex.yatagan:api-dynamic:${yataganVersion}")

    // Use codegen in releases
    releaseApi("com.yandex.yatagan:api-compiled:${yataganVersion}")
    kaptRelease("com.yandex.yatagan:processor-jap:${yataganVer}")
}