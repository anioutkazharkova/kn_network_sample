import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
}

version = "1.0"

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        System.getenv("NATIVE_ARCH")?.startsWith("arm") == true -> ::iosSimulatorArm64
        else -> ::iosX64
    }

    iosTarget("ios") {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.github.anioutkazharkova:kn_network_client:1.0.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
                //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${findProperty("version.kotlinx.serialization")}")
            }
        }
        val commonTest by getting
        val androidMain by getting
        val iosMain by getting
        val iosTest by getting
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
}