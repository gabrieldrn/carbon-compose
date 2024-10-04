plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "com.gabrieldrn.docparser"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("it.skrape:skrapeit:1.1.5")
    implementation("it.skrape:skrapeit-browser-fetcher:1.1.5")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
