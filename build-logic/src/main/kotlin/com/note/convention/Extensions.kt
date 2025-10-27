package com.note.convention

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.configure

fun Project.android(action: BaseExtension.() -> Unit) {
    extensions.configure(action)
}

fun DependencyHandler.implementation(dependencyNotation: Any) {
    add("implementation", dependencyNotation)
}

fun DependencyHandler.ksp(dependencyNotation: Any) {
    add("ksp", dependencyNotation)
}

fun DependencyHandler.androidTestImplementation(dependencyNotation: Any) {
    add("androidTestImplementation", dependencyNotation)
}

fun DependencyHandler.compileOnly(dependencyNotation: Any) {
    add("compileOnly", dependencyNotation)
}

fun DependencyHandler.coreLibraryDesugaring(dependencyNotation: Any) {
    add("coreLibraryDesugaring", dependencyNotation)
}

fun DependencyHandler.project(
    path: String,
    configuration: String? = null,
): Dependency {
    return project(
        mapOf(
            "path" to path,
            "configuration" to configuration,
        ).filterValues { it != null }
    )
}