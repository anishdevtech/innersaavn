plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.serialization") version "1.9.24"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    // Ktor client + serialization
    implementation("io.ktor:ktor-client-core:2.3.12")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.12")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.12")
    implementation("io.ktor:ktor-client-okhttp:2.3.12")
    // kotlinx.serialization JSON
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    // test
    testImplementation(kotlin("test"))
}
