// modules/innersaavn/build.gradle.kts

plugins {
    // Use root-managed versions (no explicit versions here)
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
}

kotlin {
    // Android target publishes variants the app can consume
    androidTarget {
        publishLibraryVariants("release", "debug")
        compilerOptions {
            // Keep in sync with root toolchain
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }

    // JVM target for desktop usage
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("io.ktor:ktor-client-core:2.3.12")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.12")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.12")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
                implementation("io.ktor:ktor-client-logging:2.3.12")
            }
        }
        val androidMain by getting {
            dependencies {
                // OkHttp engine for Android
                implementation("io.ktor:ktor-client-okhttp:2.3.12")
            }
        }
        val jvmMain by getting {
            dependencies {
                // OkHttp engine also works on plain JVM
                implementation("io.ktor:ktor-client-okhttp:2.3.12")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.ktor:ktor-client-mock:2.3.12")
            }
        }
    }
}

android {
    namespace = "dev.anishsharma.kreate.extentions.innersaavn"
    compileSdk = 36
    defaultConfig {
        minSdk = 21
    }
    buildTypes {
        debug { /* defaults */ }
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

repositories {
    google()
    mavenCentral()
}
