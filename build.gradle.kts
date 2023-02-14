@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kstate.product) apply false
    alias(libs.plugins.kstate.detekt) apply false
    alias(libs.plugins.kstate.dokka) apply false
    alias(libs.plugins.vanitech.junit) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}