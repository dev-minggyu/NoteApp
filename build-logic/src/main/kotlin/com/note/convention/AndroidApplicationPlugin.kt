package com.note.convention

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.note.Versions
import gradle.kotlin.dsl.accessors._1b3a4bfcc6679b4a3e125bbd1caa7ba5.debugImplementation
import gradle.kotlin.dsl.accessors._1b3a4bfcc6679b4a3e125bbd1caa7ba5.implementation
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.configureAndroidApplication() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    with(pluginManager) {
        apply(libs.findPlugin("kotlin.android").get().get().pluginId)
        apply(libs.findPlugin("kotlin.serialization").get().get().pluginId)
        apply(libs.findPlugin("kotlin.parcelize").get().get().pluginId)
        apply(libs.findPlugin("compose.compiler").get().get().pluginId)
    }

    //libs.findPlugin("compose.compiler").get())

    android {
        compileSdkVersion(Versions.COMPILE_SDK)

        defaultConfig {
            versionCode = Versions.VERSION_CODE
            versionName = Versions.VERSION_NAME
            minSdk = Versions.MIN_SDK
            targetSdk = Versions.TARGET_SDK
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
            isCoreLibraryDesugaringEnabled = true
        }

        tasks.withType<KotlinCompile> {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }

        buildFeatures.compose = true

        dependencies {
            coreLibraryDesugaring(libs.findLibrary("android.desugar.jdk").get())

            implementation(libs.findLibrary("kotlin.coroutines.core").get())
            implementation(libs.findLibrary("kotlin.coroutines.android").get())
            implementation(libs.findLibrary("kotlin.serialization").get())

            implementation(platform(libs.findLibrary("compose.bom").get()))
            implementation(libs.findLibrary("compose.foundation").get())
            implementation(libs.findLibrary("compose.material3").get())
            implementation(libs.findLibrary("compose.material.icons.core").get())
            implementation(libs.findLibrary("compose.material.icons.extended").get())
            implementation(libs.findLibrary("compose.navigation").get())

            implementation(libs.findLibrary("androidx.core").get())
            implementation(libs.findLibrary("androidx.appcompat").get())
            implementation(libs.findLibrary("androidx.constraintlayout").get())

            implementation(libs.findLibrary("androidx.activity.ktx").get())
            implementation(libs.findLibrary("androidx.activity.compose").get())
            implementation(libs.findLibrary("androidx.fragment.ktx").get())

            implementation(libs.findLibrary("androidx.lifecycle.viewmodel.ktx").get())
            implementation(libs.findLibrary("androidx.lifecycle.extension").get())

            implementation(libs.findLibrary("androidx.navigation.fragment.ktx").get())
            implementation(libs.findLibrary("androidx.navigation.ui.ktx").get())

            implementation(libs.findLibrary("androidx.splashscreen").get())
            implementation(libs.findLibrary("androidx.preference").get())

            implementation(libs.findLibrary("google.android.material").get())

            debugImplementation(libs.findLibrary("leakcanary.android").get())

            implementation(libs.findLibrary("test.junit").get())
            implementation(libs.findLibrary("test.junit.extension").get())
            implementation(libs.findLibrary("test.esspresso.core").get())
        }
    }
}