// settings.gradle.kts (innersaavn standalone)
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
    // maven { url = uri("https://jitpack.io") } // add here if needed, not in build.gradle
  }
}
rootProject.name = "innersaavn"
