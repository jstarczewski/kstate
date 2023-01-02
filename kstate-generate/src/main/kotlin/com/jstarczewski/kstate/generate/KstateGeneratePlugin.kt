package com.jstarczewski.kstate.generate

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class KstateGeneratePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val config = project.extensions.create("swiftTemplates", KstateGenerationExtension::class.java)
        project.tasks.register("generateSwiftTemplates", GenerateSwiftTemplatesTask::class.java) {
            it.outputDir.set(File(config.outputDir))
            it.sharedModuleName.set(config.sharedModuleName)
        }
    }
}
