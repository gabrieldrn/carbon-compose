import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

group = "com.gabrieldrn.docparser"
version = "unspecified"

kotlin {
    js {
        nodejs()
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(npm("@carbon/themes", "11.43.0"))
            }
        }
    }
}

tasks.withType<KotlinJsCompile>().configureEach {
    compilerOptions {
        target = "es2015"
    }
}
