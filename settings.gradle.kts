pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    id("com.android.library").version("7.2.0").apply(false)
    kotlin("multiplatform").version("1.7.20").apply(false)
    id("org.jetbrains.dokka").version("1.7.20").apply(false)
    id("io.gitlab.arturbosch.detekt").version("1.22.0").apply(false)
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "kstate"
include("kstate-core")
include("kstate-generate")

