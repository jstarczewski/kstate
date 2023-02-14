@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradle.publish)
    alias(libs.plugins.kstate.detekt)
    alias(libs.plugins.kstate.dokka)
}

group = "com.jstarczewski.kstate"
version = "0.0.3"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(platform(libs.kotlin.bom))
    implementation(libs.kotlin.stdlib)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest()
        }

        val functionalTest by registering(JvmTestSuite::class) {
            useJUnit()

            dependencies {
                implementation(project())
            }

            targets {
                all {
                    testTask.configure { shouldRunAfter(test) }
                }
            }
        }
    }
}

gradlePlugin {


    plugins {
        create("generate") {
            id = "com.jstarczewski.kstate.generate"
            implementationClass = "com.jstarczewski.kstate.generate.KstateGeneratePlugin"
            displayName = "Kstate Swift wrappers generating plugin"
        }
    }
}

pluginBundle {
    description = "Plugin generates Swift wrappers needed for Kstate state management library to work. The code of the library is available in the same repository as the kstate-generate gradle plugin."
    website = "https://github.com/jstarczewski/kstate"
    vcsUrl = "https://github.com/jstarczewski/kstate"
    tags = setOf("kmm", "swift", "SwiftUI", "kotlin", "multiplatform")
}

gradlePlugin.testSourceSets(sourceSets["functionalTest"])

tasks.named<Task>("check") {

    dependsOn(testing.suites.named("functionalTest"))
}