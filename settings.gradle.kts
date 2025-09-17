// settings.gradle.kts (root)
pluginManagement {
  repositories { google(); mavenCentral(); gradlePluginPortal() }
  // Option A: Catalog aliases (preferred if you already use libs.versions.toml)
  // Option B: Direct versions:
  plugins {
    id("com.android.application") version "8.7.3"
    id("com.android.library") version "8.7.3"
    kotlin("multiplatform") version "2.2.10"
    kotlin("android") version "2.2.10"
    kotlin("plugin.serialization") version "2.2.10"
  }
}
