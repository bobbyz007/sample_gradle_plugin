package com.example.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import java.io.File

class CodeLinesCounterPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("codeLines") {
            val codeLinesExtension = project.extensions.create("codeLinesStat", CodeLinesExtension::class.java)

            doLast() {
                printCodeLinesCount(project, codeLinesExtension)
            }
        }.get().apply {
            group = "stat"
        }
    }
    private fun printCodeLinesCount(project: Project, codeLinesExtension: CodeLinesExtension) {
        val fileFilter = codeLinesExtension.buildFilter()
        var totalCount = 0
        project.extensions.getByType(JavaPluginExtension::class.java)
            .sourceSets.forEach() {
                it.allSource.filter(fileFilter).forEach() { file ->
                    val lines = file.readLines()
                    totalCount += if (codeLinesExtension.sourceFilters.skipBlankLines) {
                        lines.count(CharSequence::isNotBlank)
                    } else {
                        lines.count()
                    }
                }
            }
        println("Total lines: $totalCount")
    }

    /**
     * filter by extension name
     */
    private fun CodeLinesExtension.buildFilter(): (File) -> Boolean =
        if (fileExtensions.isEmpty()) {
            { true }
        } else {
            { fileExtensions.contains(it.extension) }
        }

    open class CodeLinesExtension(
        var sourceFilters: SourceFiltersExtension = SourceFiltersExtension(),
        var fileExtensions: MutableList<String> = mutableListOf()
    ) {
        /**
         * If your plugin requires a more complex configuration,
         * you can provide functions to make the plugin API more user friendly:
         */
        fun sourceFilters(action: Action<in SourceFiltersExtension>) {
            action.execute(sourceFilters)
        }
    }

    open class SourceFiltersExtension(
        var skipBlankLines: Boolean = false
    )
}
