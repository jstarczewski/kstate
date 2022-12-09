enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven {
            setUrl("https://oss.sonatype.org/content/repositories/snapshots")
        }
    }
    includeBuild("kstate-convention")
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

