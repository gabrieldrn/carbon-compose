plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

group = "com.gabrieldrn.codegen"
version = "unspecified"

dependencies {
    implementation(project(":code-gen:themes-model"))
    implementation(libs.kotlinx.serialization.json)
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.compose.ui:ui-graphics:${libs.versions.compose.get()}")
    implementation(compose.runtime)
    implementation(libs.kotlinPoet)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
