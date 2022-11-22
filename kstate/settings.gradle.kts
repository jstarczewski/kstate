pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.library").version("7.2.0").apply(false)
    kotlin("multiplatform").version("1.7.10").apply(false)
}

rootProject.name = "kstate"
include(":common")

