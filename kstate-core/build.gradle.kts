@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kstate.product)
    alias(libs.plugins.vanitech.junit)
    jacoco
}

group = "com.jstarczewski.kstate"
version = "0.0.3"

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        tvosX64(),
        tvosArm64(),
        tvosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "kstate"
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.compose.runtime)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(libs.kotlin.test.junit)
                implementation(libs.junit)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val tvosX64Main by getting
        val tvosArm64Main by getting
        val tvosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            tvosX64Main.dependsOn(this)
            tvosArm64Main.dependsOn(this)
            tvosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val tvosX64Test by getting
        val tvosArm64Test by getting
        val tvosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
            tvosX64Test.dependsOn(this)
            tvosArm64Test.dependsOn(this)
            tvosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.jstarczewski.kstate"
    compileSdk = 33
    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
}
