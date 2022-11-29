plugins {
    id("io.gitlab.arturbosch.detekt")
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    dependencies {
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")
    }

    detekt {
        buildUponDefaultConfig = true
        allRules = false
        autoCorrect = true
        parallel = true
        source = files(
            "$rootDir/kstate-core/src",
            "$rootDir/kstate-generate/src/main"
        )
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}