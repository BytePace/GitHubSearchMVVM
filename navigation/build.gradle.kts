plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {
    namespace = "com.drus.githubsearch.navigation"

    defaultConfig {
        minSdk = 24
        compileSdk = 33
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation(project(":core"))

    implementation("androidx.fragment:fragment-ktx:1.5.7")
    implementation("com.github.terrakok:cicerone:7.1")

    val daggerVersion = rootProject.extra.get("daggerVersion")
    kapt("com.google.dagger:dagger:2.40.5")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
}