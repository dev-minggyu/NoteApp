plugins {
    id("note.android.library")
    id("note.android.room.plugin")
}

android {
    namespace = "com.note.database"
}

dependencies {
    implementation(project(":domain"))
} 