plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

group = "com.gabrieldrn.codegen"
version = "unspecified"

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.compose.ui:ui-graphics:${libs.versions.compose.get()}")
    implementation(libs.compose.runtime)
    implementation(libs.kotlinPoet)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

val generatedColorTokensDir = project(":carbon").layout.buildDirectory.dir("generated/colorTokens/commonMain")

tasks.register<JavaExec>("execute") {
    group = "code generation"
    description = "Generates Carbon's color theme sources into :carbon's build dir."
    mainClass.set("com.gabrieldrn.codegen.MainKt")
    classpath = sourceSets.main.get().runtimeClasspath
    args(generatedColorTokensDir.get().asFile.absolutePath)

    inputs.files(sourceSets.main.get().allSource)
    inputs.files(sourceSets.main.get().resources)
    outputs.dir(generatedColorTokensDir)
}
