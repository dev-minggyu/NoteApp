plugins {
    id("note.android.application")
}

android {
    namespace = "com.note.app"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(project(":core:repository"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":core:alarm"))

    implementation(project(":feature:common"))
    implementation(project(":feature:splash"))
    implementation(project(":feature:main"))
    implementation(project(":feature:notedetail"))
}