import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import co.touchlab.skie.configuration.SealedInterop

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxSerialization)
    id("co.touchlab.skie") version "0.10.0"
}

group = "io.github.kotlin"
version = "1.0.0"

kotlin {
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        compilations.get("main").compilerOptions.options.freeCompilerArgs.add("-Xexport-kdoc")
    }
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    val xcframeworkName = "KMPLibrary"
    val xcf = XCFramework(xcframeworkName)
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = xcframeworkName
            binaryOption("bundleId", "org.example.${xcframeworkName}")
            xcf.add(this)
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val iosMain by creating {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.ktor.client.mock)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
            }
        }
    }
}

skie {
    features {
        group {
            SealedInterop.Enabled(true)
        }
    }
}

android {
    namespace = "org.jetbrains.kotlinx.multiplatform.library.template"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}