plugins {
    id("note.android.library")
}

android {
    namespace = "com.note.feature.notedetail"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(project(":core:navigation"))

    implementation(project(":feature:common"))
}