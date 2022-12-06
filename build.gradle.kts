plugins {
    id("kstate.detekt")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}