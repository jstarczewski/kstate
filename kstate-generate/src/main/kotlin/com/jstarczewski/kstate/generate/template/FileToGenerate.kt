package com.jstarczewski.kstate.generate.template

sealed class FileToGenerate {

    abstract val fileName: String

    abstract val fileContent: String

    val projectTokenName: String = "&PROJECT_TOKEN"
}

fun FileToGenerate.generateContent(projectName: String) =
    fileContent.replace(projectTokenName, projectName)
