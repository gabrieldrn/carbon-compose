import com.gabrieldrn.carbon.Configuration
import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    id("carbon.kmp.library")
    id("carbon.detekt")
}

carbonLibrary {
    artifactId.set("carbon-test")
    artifactGroup.set(Configuration.artifactGroup)
    version.set(Configuration.version)
}

kotlin {
    @OptIn(ExperimentalComposeLibrary::class)
    sourceSets {
        commonMain.dependencies {
            api(project(":carbon:common"))

            implementation(compose.uiTest) {
                exclude(group = "androidx.test.espresso", module = "espresso-core")
            }
        }
    }
}

android {
    namespace = "com.gabrieldrn.carbon.test"
}
