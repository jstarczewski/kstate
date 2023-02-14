package com.jstarczewski.kstate.generate

@Suppress("UnnecessaryAbstractClass")
abstract class KstateGenerationExtension {

    var outputDir: String = "templates"

    var sharedModuleName: String = "shared"

    var coreLibraryExported: Boolean = false
}
