package com.jstarczewski.kstate.generate

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.slf4j.LoggerFactory
import java.io.File

class KstateGeneratePlugin : Plugin<Project> {

    private val logger = LoggerFactory.getLogger("KstateGeneratePlugin")

    override fun apply(project: Project) {
        val config = project.extensions.create("swiftTemplates", KstateGenerationExtension::class.java)
        project.tasks.register("generateSwiftTemplates", GenerateSwiftTemplatesTask::class.java) {
            require(config.outputDir.isNotBlank()) { "Specified outputDir property cannot be empty or blank" }

            require(config.sharedModuleName.isNotBlank()) {
                "Specified sharedModuleName configuration property cannot be empty or blank"
            }

            if (config.outputDir == "templates" && config.sharedModuleName == "shared") {
                logger.warn(
                    """
                No templates configuration block other than default found.
                
                GENERATING DEFAULT TEMPLATES FOR:
                outputDir = "templates"
                sharedModuleName = "common"
                
                To adjust generated templates, please provide proper generation configuration within build.gradle file
                where plugin is applied using swiftTemplates DSL.
                
                Example: 
               
                swiftTemplates {
                    
                    /* Output dir defined with relative path to project's build gradle file */
                    outputDir = "/ios/ios/templates" 
                    
                    /* Shared module name */
                    sharedModuleName = "common"
                }
                    """.trimIndent()
                )
            } else {
                logger.info(
                    """
                    GENERATING DEFAULT TEMPLATES FOR:
                    outputDir = "templates"
                    sharedModuleName = "common"
                    """.trimIndent()
                )
            }
            it.outputDir.set(File(config.outputDir))
            it.sharedModuleName.set(config.sharedModuleName)
        }
    }
}
