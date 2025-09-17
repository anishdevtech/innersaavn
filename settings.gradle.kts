import org.gradle.api.initialization.resolve.RepositoriesMode

pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    // add others globally if needed, e.g.:
    // maven { url = uri("https://jitpack.io") }
  }
}
rootProject.name = "innersaavn"
