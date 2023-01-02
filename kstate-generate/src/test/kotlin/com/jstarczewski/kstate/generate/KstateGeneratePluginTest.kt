package com.jstarczewski.kstate.generate

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class KstateGeneratePluginTest {
    @Test
    fun `plugin on default registers task`() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("com.jstarczewski.kstate.generate")
        assertNotNull(project.tasks.findByName("generateSwiftTemplates"))
    }

    @Test
    fun `plugin on default registers only one task`() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("com.jstarczewski.kstate.generate")
        assertEquals(1, project.tasks.withType(GenerateSwiftTemplatesTask::class.java).size)
    }

    @Test
    fun `plugin on default registers task with default outputDir`() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("com.jstarczewski.kstate.generate")
        project.tasks.withType(GenerateSwiftTemplatesTask::class.java).first().apply {
            assertTrue(outputDir.get().toString().endsWith("templates"))
        }
    }

    @Test
    fun `plugin on default registers task with default shared module name`() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("com.jstarczewski.kstate.generate")
        project.tasks.withType(GenerateSwiftTemplatesTask::class.java).first().apply {
            assertEquals("shared", sharedModuleName.get())
        }
    }
}
