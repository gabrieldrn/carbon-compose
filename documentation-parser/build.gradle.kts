plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization")
}

group = "com.gabrieldrn.docparser"
version = "unspecified"

dependencies {
    implementation(compose.runtime)
    implementation("org.jetbrains.compose.ui:ui-graphics:${libs.versions.compose.get()}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
