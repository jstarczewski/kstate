package com.jstarczewski.kstate.generate

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertNotNull

class KstateGeneratePluginTest {
    @Test
    fun `plugin registers task`() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("com.jstarczewski.kstate.generate")
        assertNotNull(project.tasks.findByName("generateSwiftTemplates"))
    }
}
