---
hide:
  - navigation
  - toc
---

# Overview

Carbon Compose is an implementation of the [Carbon Design System](https://github.com/carbon-design-system/carbon) with 
[KMP](https://kotlinlang.org/docs/multiplatform.html) and 
[Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/).
It's available for the following targets:

- Android
- iOS
- Desktop (Windows, Linux, macOS)
- Web (Wasm)

## Motivations

After reflecting on the availability of open-source design systems for mobile apps, it turns out that there are few 
options - Material for Android, Cupertino for iOS. Providing a bit of diversity in the developer community would be
great.

So, taking on the implementation of Carbon was decided. As this is a design system appreciated for its simplicity
and consistency, this is a great tool for less _visual_ user interfaces focused on very functional or technical things, 
such as dashboards, admin panels, [HMI](https://inductiveautomation.com/resources/article/what-is-hmi)s, etc. and 
Material, for instance, may be too _visual_ for such applications.

The implementation started with Jetpack Compose first, and as the project kept growing, a Compose Multiplatform seemed
like a good idea. As a result, the project evolved, and the project became open-source.
