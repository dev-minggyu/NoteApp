plugins {
    id("note.android.library")
}

android {
    namespace = "com.note.alarm"
}

dependencies {
    implementation(project(":domain"))
}