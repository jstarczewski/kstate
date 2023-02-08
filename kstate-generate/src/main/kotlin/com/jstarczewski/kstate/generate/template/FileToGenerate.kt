package com.jstarczewski.kstate.generate.template

sealed class FileToGenerate {

    abstract val fileName: String

    abstract val fileContent: String

    val projectTokenName: String = "&PROJECT_TOKEN"

    val stateHolderPrefixTokenName: String = "&STATE_HOLDER_PREFIX_TOKEN"
}

fun FileToGenerate.generateContent(projectName: String, isCoreLibraryExported: Boolean) = fileContent
    .replace(projectTokenName, projectName)
    .replace(stateHolderPrefixTokenName, libraryNamePrefix(isCoreLibraryExported))

private fun libraryNamePrefix(isCoreLibraryExported: Boolean) = if (isCoreLibraryExported) "" else "Kstate_core"
