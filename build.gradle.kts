plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
}

kotlin {
    // Align toolchain for all targets
    jvmToolchain(17)

    androidTarget {
        // Publish variants so Android consumers can depend on this KMP library
        publishLibraryVariants("release", "debug")
        compilerOptions {
            // Keep JVM 17 for Android target unless the project is fully on Java 21 with AGP support
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    // Optional additional target for pure JVM usage outside Android
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // stdlib is added by the Kotlin plugin; no need to add kotlin("stdlib")
                implementation("io.ktor:ktor-client-core:2.3.12")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.12")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.12")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
                implementation("io.ktor:ktor-client-logging:2.3.12")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:2.3.12")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:2.3.12")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.ktor:ktor-client-mock:2.3.12")
            }
       val androidUnitTest by getting {
      dependencies {
        implementation("io.ktor:ktor-client-cio:2.3.12") // for HttpClient(CIO)
        // or, if your test uses OkHttp instead:
        // implementation("io.ktor:ktor-client-okhttp:2.3.12")
      }
    }
        }
    }
}

android {
    namespace = "dev.anishsharma.kreate.extentions.innersaavn"
    compileSdk = 35 // Prefer 35 with AGP 8.5.x; move to 36 when AGP/toolchain supports it in your stack
    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        debug { }
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        // Keep 17 here to match common AGP baselines unless you are on AGP with Java 21 support
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
