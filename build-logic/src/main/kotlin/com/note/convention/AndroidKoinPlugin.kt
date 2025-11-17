package com.note.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidKoin() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        implementation(libs.findLibrary("koin.core").get())
        implementation(libs.findLibrary("koin.android").get())
        implementation(libs.findLibrary("koin.androidx.compose").get())
    }
}