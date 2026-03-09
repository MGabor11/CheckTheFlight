import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {

    // Target declarations - add or remove as needed below. These define
    // which platforms this KMP module supports.
    // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
    androidLibrary {
        namespace = "com.marossolutions.data"
        compileSdk = 36
        minSdk = 30

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    // For iOS targets, this is also where you should
    // configure native binary output. For more information, see:
    // https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

    // A step-by-step guide on how to include this library in an XCode
    // project can be found here:
    // https://developer.android.com/kotlin/multiplatform/migrate
    val xcfName = "dataKit"

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

    // Source set declarations.
    // Declaring a target automatically creates a source set with the same name. By default, the
    // Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
    // common to share sources between related targets.
    // See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)

                // DI
                api(libs.koin.core)

                // Serialization
                implementation(libs.kotlinx.serialization.json)

                // Coroutines
                implementation(libs.kotlinx.coroutines.core)

                // Networking
                implementation(libs.bundles.ktor)

                // KotlinX DateTime
                implementation(libs.kotlinx.datetime)

                // DataStore
                implementation(libs.androidx.datastore.preferences)
                implementation(libs.androidx.datastore)

                implementation(project(path = ":core:common"))
                implementation(project(path = ":core:domain"))
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                // Add Android-specific dependencies here. Note that this source set depends on
                // commonMain by default and will correctly pull the Android artifacts of any KMP
                // dependencies declared in commonMain.

                // DI
                implementation(libs.koin.android)

                // Coroutines
                implementation(libs.kotlinx.coroutines.android)

                // Ktor
                implementation(libs.ktor.client.android)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.testExt.junit)
            }
        }

        iosMain {
            dependencies {
                // Add iOS-specific dependencies here. This a source set created by Kotlin Gradle
                // Plugin (KGP) that each specific iOS target (e.g., iosX64) depends on as
                // part of KMP’s default source set hierarchy. Note that this source set depends
                // on common by default and will correctly pull the iOS artifacts of any
                // KMP dependencies declared in commonMain.

                // Ktor
                implementation(libs.ktor.client.darwin)
            }
        }
    }

}

buildkonfig {
    packageName = "com.marossolutions.checktheflight"

    val localProperties =
        Properties().apply {
            val propsFile = rootProject.file("local.properties")
            if (propsFile.exists()) {
                load(propsFile.inputStream())
            }
        }

    val apiNinjaApiKey = "API_NINJA_API_KEY"
    val airlabsApiKey = "AIRLABS_API_KEY"
    val geoApifyMapsKey = "GEOAPIFY_MAPS_KEY"
    val apiNinjaBaseUrl = "API_NINJA_BASE_URL"
    val airlabsBaseUrl = "AIRLABS_BASE_URL"

    defaultConfigs {
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = apiNinjaApiKey,
            value = localProperties[apiNinjaApiKey]?.toString()
                ?: error("API_NINJA_API_KEY is missing"),
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = airlabsApiKey,
            value = localProperties[airlabsApiKey]?.toString()
                ?: error("AIRLABS_API_KEY is missing"),
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = geoApifyMapsKey,
            value = localProperties[geoApifyMapsKey]?.toString()
                ?: error("GEOAPIFY_MAPS_KEY is missing"),
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = apiNinjaBaseUrl,
            value = localProperties[apiNinjaBaseUrl]?.toString()
                ?: error("API_NINJA_BASE_URL is missing"),
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = airlabsBaseUrl,
            value = localProperties[airlabsBaseUrl]?.toString()
                ?: error("AIRLABS_BASE_URL is missing"),
        )
    }
}
