plugins {
    id("note.android.library")
    id("note.android.retrofit.plugin")
}

android {
    namespace = "com.note.network"
}

dependencies {
    implementation(project(":domain"))
} 