pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "unsplash-test"
include(":app")
include(":data")
include(":domain")
include(":core:ui")
include(":feature:imagelist")
