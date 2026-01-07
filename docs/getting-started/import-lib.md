# Import Carbon library

## Prerequisites

### Android

Carbon uses [`kotlinx.datetime`](https://github.com/Kotlin/kotlinx-datetime) for components working 
with time and dates. Therefore, **if the project you are using Carbon with targets Android, you need 
to enable [core library desugaring](https://developer.android.com/studio/write/java8-support#library-desugaring)**

## Add Carbon to your dependencies

To start using Carbon in your Compose app, add the dependency according to your dependencies declaration method:

=== "Version catalog"

    ```toml
    [versions]
    carbon=<version>

    [libraries]
    carbon-compose = { module = "io.github.gabrieldrn:carbon", version.ref = "carbon" }
    ```

    ```kotlin
    dependencies {
        implementation(libs.carbon.compose)
    }
    ```

    **KMP:**

    ```kotlin
    kotlin {
        sourceSets {
            ...
            commonMain.dependencies {
                implementation(libs.carbon.compose)
            }
            ...
        }
    }
    ```

=== "KTS"

    ```kotlin
    dependencies {
        implementation("io.github.gabrieldrn:carbon:$version")
    }
    ```

=== "Groovy"

    ```Groovy
    dependencies {
        implementation "io.github.gabrieldrn:carbon:$version"
    }
    ```

You can find the latest version in the [releases page](https://github.com/gabrieldrn/carbon-compose/releases).
