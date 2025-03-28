<p align="left">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="https://github.com/user-attachments/assets/a0a20d5a-9f67-46ea-a68e-fe836b3a3d81">
    <source media="(prefers-color-scheme: light)" srcset="https://github.com/user-attachments/assets/cf499dee-c867-4613-96a8-c018bdb3f112">
    <img alt="Logo" src="https://github.com/user-attachments/assets/cf499dee-c867-4613-96a8-c018bdb3f112" width="150">
  </picture>
</p>

# Carbon | Compose Multiplatform

[![Kotlin](https://img.shields.io/badge/2.1.10-blue?logo=kotlin&logoColor=white&color=7F52FF)](http://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/1.7.3-green?logo=jetpackcompose&logoColor=white&color=4284F3)](https://github.com/JetBrains/compose-multiplatform)

[![CI | compile & tests](https://github.com/gabrieldrn/carbon-compose/actions/workflows/ci-lib-workflow.yml/badge.svg)](https://github.com/gabrieldrn/carbon-compose/actions/workflows/ci-lib-workflow.yml)

A [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) implementation of IBM's [Carbon Design System](https://github.com/carbon-design-system/carbon).

![](https://github.com/user-attachments/assets/82d8c108-7e6b-4ef8-8e6c-cf1ecf7026d1) | ![](https://github.com/user-attachments/assets/5b80e6a9-7b5c-41b0-9bac-169e2f051954) | ![](https://github.com/user-attachments/assets/a1ba04d5-c728-4791-848f-9e26df38c346)
:--:|:--:|:--:

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

Visit the project's Github Pages https://gabrieldrn.github.io/carbon-compose/ to get more information about the
project, some documentation, its API reference and the catalog app. 

# Contributions

The project is open for contributions, have a look at the contribution guidelines and the issues list!

# Support the project 🫶 

Maintaining a free and open-source project takes time! If you like it and would like to encourage me, you can:
- Star and share the project,
- Offer me a latte ☕ (my daily fuel) with the GitHub sponsoring button on this repository main page.
  
Thank you very much for your support!

---

Content featuring carbon-compose:

<a href="https://jetc.dev/issues/230.html"><img src="https://img.shields.io/badge/As_Seen_In-jetc.dev_Newsletter_Issue_%23230-blue?logo=Jetpack+Compose&amp;logoColor=white" alt="As Seen In - jetc.dev Newsletter Issue #230"></a>
