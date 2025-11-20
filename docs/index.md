---
hide:
  - navigation
  - toc
---

# Overview

Carbon Compose is an implementation of the [Carbon Design System](https://github.com/carbon-design-system/carbon) built 
using [Kotlin Multiplatform (KMP)](https://kotlinlang.org/docs/multiplatform.html) and 
[Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/).
This versatile framework is available for the following platforms:

- Android
- iOS
- Desktop (Windows, Linux, macOS)
- Web (WasmJs)

## Motivations

In evaluating the landscape of open-source design systems for mobile applications, it's evident that there are limited 
options — primarily Material Design for Android and Cupertino for iOS. Introducing more diversity into the developer 
community can foster creativity and innovation.

This led to the decision to implement the Carbon Design System. Renowned for its simplicity and consistency, Carbon is 
an excellent choice for user interfaces that prioritize functionality over aesthetics, such as dashboards, admin panels,
and [Human-Machine Interfaces (HMI)](https://inductiveautomation.com/resources/article/what-is-hmi). In contrast, 
Material Design may be overly focused on visual elements for such applications.

The journey began with Jetpack Compose, and as the project expanded, adopting Compose Multiplatform became a natural 
progression. This evolution has culminated in the project becoming open-source, allowing the wider community to benefit
from and contribute to its development.

## Project status

This project is currently a work in progress, with new components and features being implemented as development 
continues and new versions are released. The project is currently in its major version `0`. Once it includes at least as 
many components as those documented on the official Carbon Design System website, version `1.0.0` will be released. As the
Carbon Design System continues to evolve under the guidance of IBM's design teams, this Compose Multiplatform adaptation
will be updated accordingly over time.

## Component Availability Matrix

List of the currently supported components:

✅ : Implemented

🟡 : Partially implemented

*empty* : Not implemented

| Components         | Android  🤖 | iOS   | Desktop  🖥️ | wasmJs 🌐 |
|--------------------|:-----------:|:------:|:------------:|:---------:|
| Accordion          |      ✅      |   ✅    |      ✅       |     ✅     |
| AI label           |             |        |              |           |
| Breadcrumb         |      ✅      |   ✅    |      ✅       |     ✅     |
| Button             |      ✅      |   ✅    |      ✅       |     ✅     |
| Checkbox           |      ✅      |   ✅    |      ✅       |     ✅     |
| Code snippet       |             |        |              |           |
| Contained list     |             |        |              |           |
| Content switcher   |      ✅      |   ✅    |      ✅       |     ✅     |
| Data table         |             |        |              |           |
| Date picker        |             |        |              |           |
| Dropdown           |     🟡      |   🟡   |      🟡      |    🟡     |
| File uploader      |             |        |              |           |
| Form               |             |        |              |           |
| Inline loading     |             |        |              |           |
| Link               |             |        |              |           |
| List               |             |        |              |           |
| Loading            |      ✅      |   ✅    |      ✅       |     ✅     |
| Menu               |             |        |              |           |
| Menu buttons       |             |        |              |           |
| Modal              |             |        |              |           |
| Notification       |      ✅      |   ✅    |      ✅       |     ✅     |
| Number input       |             |        |              |           |
| Pagination         |             |        |              |           |
| Popover            |      ✅      |   ✅    |      ✅       |     ✅     |
| Progress bar       |      ✅      |   ✅    |      ✅       |     ✅     |
| Progress indicator |             |        |              |           |
| Radio button       |      ✅      |   ✅    |      ✅       |     ✅     |
| Search             |             |        |              |           |
| Select             |             |        |              |           |
| Slider             |     🟡      |   🟡   |      🟡      |    🟡     |
| Structured list    |             |        |              |           |
| Tabs               |     🟡      |   🟡   |      🟡      |    🟡     |
| Tags               |     🟡      |   🟡   |      🟡      |    🟡     |
| Text input         |     🟡      |   🟡   |      🟡      |    🟡     |
| Tile               |             |        |              |           |
| Toggle             |      ✅      |   ✅    |      ✅       |     ✅     |
| Toggletip          |             |        |              |           |
| Tooltip            |      ✅      |   ✅    |      ✅       |     ✅     |
| Treeview           |             |        |              |           |
| UI shell           |             |        |              |           |
