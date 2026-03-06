import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization) // TODO
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

     listOf(
         iosX64(),
         iosArm64(),
         iosSimulatorArm64()
     ).forEach { iosTarget ->
         iosTarget.binaries.framework {
             baseName = "ComposeApp"
             isStatic = true
         }
     }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)

            // DI
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            // WorkManager
            implementation(libs.androidx.work.runtime.ktx)
            implementation(libs.koin.workmanager)
        }
        commonMain.dependencies {
            // Modules
            implementation(project(path = ":core:common"))
            implementation(project(":core:domain"))
            implementation(project(path = ":core:data"))
            implementation(project(":core:navigation"))
            implementation(project(":core:designsystem"))

            implementation(project(path = ":feature:welcome"))
            implementation(project(":feature:home"))
            implementation(project(":feature:airport"))
            implementation(project(path = ":feature:airline"))

            implementation(libs.bundles.compose)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // DI
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Navigation
            implementation(libs.androidx.navigation3.runtime)
            implementation(libs.androidx.navigation3.ui)
        }
        iosMain.dependencies {

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.marossolutions.checktheflight"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.marossolutions.checktheflight"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}
