plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.androidLint)
}

kotlin {

    androidLibrary {
        namespace = "com.marossolutions.flightsync"
        compileSdk = 36
        minSdk = 30
    }

    val xcfName = "flightSyncKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)

                // Coroutines
                implementation(libs.kotlinx.coroutines.core)

                // DI
                api(libs.koin.core)

                // Modules
                implementation(project(":core:common"))
                implementation(project(":core:domain"))
            }
        }

        androidMain {
            dependencies {
                // WorkManager
                implementation(libs.androidx.work.runtime.ktx)

                // Koin WorkManager
                implementation(libs.koin.workmanager)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}
