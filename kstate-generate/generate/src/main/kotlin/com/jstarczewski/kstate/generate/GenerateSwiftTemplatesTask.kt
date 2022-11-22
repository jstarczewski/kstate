package com.jstarczewski.kstate.generate

import com.jstarczewski.kstate.generate.template.*
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class GenerateSwiftTemplatesTask : DefaultTask() {

    private val filesToGenerate: List<FileToGenerate> = listOf(
        ObservableStateHolder,
        ObservedStateHolder,
        StateStateHolder
    )

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @get:Input
    abstract val sharedModuleName: Property<String>

    @TaskAction
    fun execute() {
        filesToGenerate.forEach { fileToGenerate ->
            val targetFile = outputDir.file(fileToGenerate.fileName).get().asFile
            targetFile.writeText(fileToGenerate.generateContent(sharedModuleName.get()))
        }
    }
}