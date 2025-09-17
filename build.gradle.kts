plugins {
  kotlin("multiplatform")
  id("com.android.library")
  kotlin("plugin.serialization")
}

kotlin {
  androidTarget {
    // Publish variants so Android apps can consume this library
    publishLibraryVariants("release", "debug")
    compilerOptions {
      jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
  }
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
    debug {}
    release {
      isMinifyEnabled = false
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }
}