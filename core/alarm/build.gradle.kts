plugins {
    id("note.android.library")
}

android {
    namespace = "com.note.core.alarm"
}

dependencies {
    implementation(project(":domain"))

    implementation(project(":core:navigation"))
}