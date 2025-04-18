# Test utilities

Carbon Compose is built to let you create Carbon-based custom components by providing as much of the
foundation elements of the design system, plus a set of APIs and utilities to work with them.

In this case, Carbon Compose also provides `carbon-test`, a separate dependency containing helper 
methods to help testing specific aspects of the design system.

For instance, the _read-only_ state doesn't exist in Compose, and had to be implemented in the
library. To test this state on components, this dependency offers a semantics matcher to let you
make assertions on the component's state.

!!! warning
    Please note that this dependency is meant for testing purposes only. It uses `compose-uitest`
    and should only be used in tests source sets.

## Import

=== "Version catalog"

    ```toml
    [versions]
    carbon=<version>

    [libraries]
    carbon-compose-test = { module = "io.github.gabrieldrn:carbon-test", version.ref = "carbon" }
    ```

    ```kotlin
    dependencies {
        testImplementation(libs.carbon.compose.test)
    }
    ```

    **KMP:**

    ```kotlin
    kotlin {
        sourceSets {
            ...
            commonTest.dependencies {
                implementation(libs.carbon.compose.test)
            }
            ...
        }
    }
    ```

=== "KTS"

    ```kotlin
    dependencies {
        testImplementation("io.github.gabrieldrn:carbon-test:$version")
    }
    ```

=== "Groovy"

    ```Groovy
    dependencies {
        testImplementation "io.github.gabrieldrn:carbon-test:$version"
    }
    ```

## Usage

See the [API reference](https://gabrieldrn.github.io/carbon-compose/api/carbon/test/index.html) to
learn more about the API.
