plugins {
    id("note.android.library")
}

android {
    namespace = "com.note.core.test"
}

dependencies {
    implementation(project(":feature:common"))
    
    implementation(libs.test.junit)
    implementation(libs.test.mockk)
    implementation(libs.test.turbine)
    implementation(libs.kotlin.coroutines.test)
}