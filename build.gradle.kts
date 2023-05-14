buildscript {
    extra.apply {
        set("kotlinVersion", "1.8.20")
        set("daggerVersion", "2.40.5")
        set("retrofitVersion", "2.9.0")
        set("okhttpVersion", "4.9.3")
        set("yataganVersion", "1.2.0")
    }
}

plugins {
    id("com.android.application") version "8.0.1" apply false
    id("com.android.library") version "8.0.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.21" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean") {
    delete(rootProject.buildDir)
}