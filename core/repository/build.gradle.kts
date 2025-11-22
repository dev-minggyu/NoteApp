plugins {
    id("note.android.library")
}

android {
    namespace = "com.note.core.repository"
}

dependencies {
    implementation(project(":domain"))

    implementation(project(":core:database"))
    implementation(project(":core:network"))
} 