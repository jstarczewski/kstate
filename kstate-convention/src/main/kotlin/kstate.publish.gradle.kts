import org.gradle.kotlin.dsl.repositories

plugins {
    `maven-publish`
}

publishing {
    repositories {
        mavenLocal()
    }
}