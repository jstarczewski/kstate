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
}

dependencyResolutionManagement {
    versionCatalogs {
        val libs by creating {
            from(files("../gradle/libs.versions.toml"))
        }
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "kstate-convention"