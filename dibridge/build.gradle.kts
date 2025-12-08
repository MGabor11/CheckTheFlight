plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
}

kotlin {
    // Android target
    androidLibrary {
        namespace = "com.marossolutions.dibridge"
        compileSdk = 36
        minSdk = 24
    }

    // iOS targets - no framework needed, this is just a DI bridge
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.koin.core)

                // Module dependencies
                implementation(project(path = ":common"))
                implementation(project(path = ":data"))
            }
        }
    }
}
