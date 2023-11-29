package com.example.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class CodeLinesCounterPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("codeLines") {
            doLast() {
                println("Hello from CodeLinesCounterPlugin")
            }
        }.get().apply {
            group = "stat"
        }
    }
}