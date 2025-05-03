# 🤳 Catalog app

Module presenting the implemented components of this project.

As this is a KMP module, please follow [those steps](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-setup.html) 
to setup your environment accordingly.

## Browser 🌐

Access the web catalog app at https://gabrieldrn.github.io/carbon-compose/catalog/index.html.

Or run the web version locally by executing the `wasmJsBrowserRun` Gradle task in a terminal:
```
./gradlew :catalog:wasmJsBrowserRun
```

## Desktop 🖥️

Run the desktop version by executing the `run` Gradle task in a terminal:
```
./gradlew :catalog:run
```

## Android 🤖

You have two ways to get and run the Android catalog app on your device:

- Download the signed APK in the [latest release notes](https://github.com/gabrieldrn/carbon-compose/releases) and install it on your device.
- Run the app from the Android App run configuration in Android Studio, or compile an APK by executing the assemble task in a terminal:
    ```
    ./gradlew :catalog:assemble
    ```

## iOS 

Open the Xcode project located in `/iosCatalog` and run it (⌘R).
