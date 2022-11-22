package com.jstarczewski.kstate.generate

import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class GenerationSpecification @Inject constructor(
    val name: String
) {

    abstract val outputDir: Property<String>

    abstract val sharedModuleName: Property<String>
}