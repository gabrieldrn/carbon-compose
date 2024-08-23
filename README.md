<img src="https://github.com/user-attachments/assets/cf499dee-c867-4613-96a8-c018bdb3f112" width="150" height="150">

# Carbon | Compose Multiplatform

![Carbon catalog pres](https://github.com/user-attachments/assets/d55e6893-ab18-498f-abec-1e2998dfe293)

[![Kotlin](https://img.shields.io/badge/2.0.0-blue?logo=kotlin&logoColor=white&color=7F52FF)](http://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/1.6.11-green?logo=jetpackcompose&logoColor=white&color=4284F3)](https://www.jetbrains.com/lp/compose-multiplatform)

[![CI | compile & tests](https://github.com/gabrieldrn/carbon-compose/actions/workflows/ci-lib-workflow.yml/badge.svg)](https://github.com/gabrieldrn/carbon-compose/actions/workflows/ci-lib-workflow.yml)

A [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) implementation of the [Carbon Design System](https://github.com/carbon-design-system/carbon).

**Supported platforms:**
- Android
- iOS
- Desktop
- Web via Wasm

> [!CAUTION]
> This library is **still under development** and **unofficial**. Contributions are welcomed!

# 🤳 Catalog App

For testing purposes, this project provides a _catalog_ app for supported targets.

As this is a KMP project, please follow [those steps](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-setup.html) to setup your environment accordingly.

## Android 🤖

You can either run the app from the Android App run configuration in Android Studio, or compile an APK by executing the assemble task in a terminal:
```
./gradlew :catalog:assemble
```

## iOS 

To run the iOS catalog app, open the Xcode project located in `<git repo>/iosCatalog` and run it (⌘R).

## Desktop 🖥️

You can run the desktop version by executing the `run` Gradle task in a terminal:
```
./gradlew :catalog:run
```

## Browser 🌐

You can access the web catalog app at https://gabrieldrn.github.io/carbon-compose/catalog/index.html. 

Or you can run the web version locally by executing the `wasmJsBrowserRun` Gradle task in a terminal:
```
./gradlew :catalog:wasmJsBrowserRun
```

# Documentation

Visit the project's Github Pages https://www.gabrieldrn.github.com/carbon-compose/ to get more information about the
project, some documentation, its API reference and the catalog app. 

# Contributions

The project is open for contributions, have a look at the contribution guidelines and the issues list!
