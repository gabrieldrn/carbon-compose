plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

group = "com.gabrieldrn.docparser"
version = "unspecified"

dependencies {
    implementation(compose.runtime)
    implementation("org.jetbrains.compose.ui:ui-graphics:${libs.versions.compose.get()}")
    implementation(libs.kotlinx.serialization.json)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
