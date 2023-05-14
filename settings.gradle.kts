pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "GitHubSearchMVVM"
include(":core")
include(":networking")
include(":app")
include(":search")
include(":navigation")
