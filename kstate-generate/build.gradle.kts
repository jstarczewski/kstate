plugins {
    `java-gradle-plugin`
    id("org.jetbrains.kotlin.jvm")
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest()
        }

        val functionalTest by registering(JvmTestSuite::class) {
            useKotlinTest()

            dependencies {
                // functionalTest test suite depends on the production code in tests
                implementation(project)
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

    val greeting by plugins.creating {
        id = "com.jstarczewski.kstate.generate"
        implementationClass = "com.jstarczewski.kstate.generate.KstateGeneratePlugin"
    }
}

gradlePlugin.testSourceSets(sourceSets["functionalTest"])

tasks.named<Task>("check") {
    dependsOn(testing.suites.named("functionalTest"))
}

group = "com.jstarczewski.kstate"
version = "0.1.1"

publishing {
    repositories {
        mavenLocal()
    }
}