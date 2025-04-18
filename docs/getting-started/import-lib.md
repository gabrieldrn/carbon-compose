# Import Carbon library

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
