package com.note.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidRetrofitPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                implementation(libs.findLibrary("retrofit").get())
                implementation(libs.findLibrary("retrofit.serialization").get())
                implementation(libs.findLibrary("okhttp.logging.interceptor").get())
            }
        }
    }
} 