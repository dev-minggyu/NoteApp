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