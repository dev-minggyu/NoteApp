package com.note.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidHilt() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    with(pluginManager) {
        apply(libs.findPlugin("kotlin.ksp").get().get().pluginId)
        apply(libs.findPlugin("hilt").get().get().pluginId)
    }

    dependencies {
        implementation(libs.findLibrary("hilt.core").get())
        ksp(libs.findLibrary("hilt.compiler").get())

        implementation(libs.findLibrary("hilt.android").get())
        ksp(libs.findLibrary("hilt.android.compiler").get())
    }
}

internal class AndroidHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureAndroidHilt()
        }
    }
}