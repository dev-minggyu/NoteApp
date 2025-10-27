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

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.extension)
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.preference)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.google.android.material)

    implementation(libs.test.junit)
    implementation(libs.test.junit.extension)
    implementation(libs.test.esspresso.core)

    debugImplementation(libs.leakcanary.android)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
}