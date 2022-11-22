/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.jstarczewski.kstate.generate

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class KstateGeneratePlugin : Plugin<Project> {
    override fun apply(project: Project) {

        val objects = project.objects

        val config = objects.domainObjectContainer(GenerationSpecification::class.java) { name ->
            objects.newInstance(GenerationSpecification::class.java, name)
        }

        project.extensions.add("generationConfig", config)

        config.all { spec ->
            project.tasks.register("generateSwiftTemplates", GenerateSwiftTemplatesTask::class.java) {
                it.outputDir.set(File(spec.outputDir.get()))
                it.sharedModuleName.set(spec.sharedModuleName.get())
            }
        }
    }
}
