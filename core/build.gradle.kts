plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {
    namespace = "com.drus.githubsearch.core"

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
    implementation("androidx.core:core-ktx:1.10.1")

    val daggerVersion = rootProject.extra.get("daggerVersion")
    api("com.google.dagger:dagger:$daggerVersion")
    api("com.google.dagger:dagger-android-support:$daggerVersion")

    api("com.github.terrakok:cicerone:7.1")

    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    api("androidx.fragment:fragment-ktx:1.5.7")
}