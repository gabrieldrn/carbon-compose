# Import Carbon library

Add the dependency according to your dependencies declaration method:

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