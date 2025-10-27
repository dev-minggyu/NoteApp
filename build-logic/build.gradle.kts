plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidCompose") {
            id = "note.android.compose.plugin"
            implementationClass = "com.note.convention.AndroidComposePlugin"
        }
        register("androidHilt") {
            id = "note.android.hilt.plugin"
            implementationClass = "com.note.convention.AndroidHiltPlugin"
        }
        register("androidKoin") {
            id = "note.android.koin.plugin"
            implementationClass = "com.note.convention.AndroidKoinPlugin"
        }
        register("androidRoom") {
            id = "note.android.room.plugin"
            implementationClass = "com.note.convention.AndroidRoomPlugin"
        }
        register("androidRetrofit") {
            id = "note.android.retrofit.plugin"
            implementationClass = "com.note.convention.AndroidRetrofitPlugin"
        }
    }
}