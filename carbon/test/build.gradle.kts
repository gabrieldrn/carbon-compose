import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    id("carbon.kmp.library")
    id("carbon.detekt")
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
    namespace = "com.gabrieldrn.carbon.common"
}
