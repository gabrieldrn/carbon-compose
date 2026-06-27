# carbon-compose — Claude Context

This is a **Kotlin Multiplatform (KMP) Compose library** implementing [IBM's Carbon Design System](https://carbondesignsystem.com/).

**Carbon is a standalone design system. Never use Material3 or any other design system alongside it.**

---

## Module structure

| Module | Purpose |
|---|---|
| `carbon` | All UI components (main library, published) |
| `carbon-common` | Shared semantic utilities (published) |
| `carbon-test` | Shared test helpers (published) |
| `catalog` / `catalog-android` | Demo apps (all platforms) |
| `code-gen` / `doc-parser` | Build tooling — token code generation |

**Targets:** Android, iOS (arm64 + simulatorArm64), Desktop (JVM), WasmJS.

Build convention plugins: `id("carbon.kmp.library")` and `id("carbon.detekt")`.

---

## Never do

- **No Material3 or any other design system** — Carbon is standalone; never import `androidx.compose.material3.*` or equivalent
- **No hardcoded `dp` values** for spacing — always use `SpacingScale.*`
- **No hardcoded colors** — always derive from `Carbon.theme` tokens
- **No `com.gabrieldrn.carbon.foundation.text.Text`** internal wrapper — use `BasicText` from `androidx.compose.foundation.text`
- **No auto-generated resources** — add fonts/drawables/strings manually to the resources directory

---

## Component implementation workflow

1. **Find reference**: locate the most structurally similar existing component in `carbon/src/commonMain/kotlin/com/gabrieldrn/carbon/<component>/` and read it before writing any code
2. **Consult spec**: `https://carbondesignsystem.com/components/<name>/usage/`
3. **Implement files** per anatomy below
4. **Always implement the catalog demo screen** in the same task (see Catalog workflow)

---

## Component anatomy

Components live in:
```
carbon/src/commonMain/kotlin/com/gabrieldrn/carbon/<component>/
```

File breakdown is **flexible** — apply Separation of Concerns based on complexity:

| File | Visibility | Purpose |
|---|---|---|
| `ComponentName.kt` | `public` | Main composable(s) + KDoc + `@Preview` region |
| `ComponentNameColors.kt` | `internal` | Color logic derived from `Theme` |
| `ComponentNameSize.kt` | `public expect` | `expect enum class`, actual per platform |
| `ComponentNameTestTags.kt` | `internal` | `object` with `const val` tag strings |
| `ComponentNameImpl.kt` | `internal` | Layout/drawing implementation, separated from public API |

Simpler components may need fewer files; complex ones may split further (e.g., `domain/`, `base/` subpackages).

---

## Catalog workflow

When implementing a new component, always add a catalog demo in the same task:

1. Create `XxxDemoScreen.kt` in `catalog/src/commonMain/kotlin/com/gabrieldrn/carbon/catalog/xxx/`
2. Add entry to `Destination.kt` enum with `route` and `content` lambda
3. For `illustration`: attempt to extract from `https://carbondesignsystem.com/components/overview/components/`; if unavailable, set `illustration = null` and leave a `// TODO: add tile illustration` comment
4. Register in `CatalogNavGraph.kt` if additional navigation wiring is needed

---

## Build & run commands

```bash
# Run catalog — desktop is fastest for UI iteration
./gradlew :catalog:run
# Or use the IDE run config "🔥catalog [desktop]"

# Run all tests — mandatory for all platforms
./gradlew :carbon:allTests
./gradlew :carbon-common:allTests
./gradlew :carbon-test:allTests

# Static analysis
./gradlew detekt

# API verification
./gradlew apiCheck

# Update API dump — run when public API changes, then stage the .api files
./gradlew :carbon:apiDump
./gradlew :carbon-common:apiDump
```

---

## Pre-commit checklist (mandatory, in order)

1. `./gradlew :carbon:allTests` — all platforms, no exceptions
2. `./gradlew detekt` — zero issues
3. `./gradlew apiCheck` — binary compatibility
4. If public API changed: `./gradlew :carbon:apiDump` then stage updated `.api` files

---

## Theming system

- Wrap all content with `CarbonDesignSystem { }` — provides theme, layer, typography, and adaptation via `CompositionLocal`
- Access via `Carbon.theme`, `Carbon.layer`, `Carbon.typography`, `Carbon.adaptation`
- **Four themes:** `WhiteTheme`, `Gray10Theme`, `Gray90Theme`, `Gray100Theme`
- **Four layers:** `Layer00`–`Layer03`. Use `CarbonLayer { }` to step one level up
- Colors are **always** derived from `Carbon.theme` tokens — never hardcoded
- `Modifier.layerBackground()` applies the contextual background for the current layer
- Contextual token helpers live in `ContextualTokens.kt` (e.g., `Theme.layerColor(layer)`)
- `Theme` is **generated code** — do not modify it directly

---

## Foundation tokens

- **Spacing:** `SpacingScale.spacing01` (2dp) → `spacing13` (160dp). Always use these, never raw dp values for Carbon-defined spacing
- **Typography:** `Carbon.typography.bodyCompact01`, `label01`, `body01`, etc. (IBM Plex fonts)
- **Motion:** `Motion.Duration.fast01`, `Motion.Duration.fast02`, `Motion.Entrance.productiveEasing`

---

## Text

Always use **`BasicText`** from `androidx.compose.foundation.text` inside components.
Never use the internal `com.gabrieldrn.carbon.foundation.text.Text` wrapper.
For dynamic colors (hover/press states), use the `color: () -> Color` lambda overload of `BasicText`.

---

## Color classes

```kotlin
@Immutable
internal class ComponentColors private constructor(theme: Theme) {
    val someColor: Color = theme.someToken

    override fun equals(other: Any?): Boolean { ... }
    override fun hashCode(): Int { ... }

    companion object {
        @Composable
        fun colors(): ComponentColors = ComponentColors(Carbon.theme)
    }
}
```

- Always `@Immutable`, always `internal`
- For selectable components (checkbox, radio button, toggle), extend `SelectableColors(theme)`

---

## Selectable interactive state

Selectable components use the `SelectableInteractiveState` sealed class:
- `Default` — enabled, interactable
- `Disabled` — not interactable
- `ReadOnly` — focusable but not interactable
- `Error(errorMessage: String)` — shows error content below
- `Warning(warningMessage: String)` — shows warning content below

`ErrorContent` and `WarningContent` composables from `common.selectable` handle the auxiliary display.

---

## `expect`/`actual` for size enums

Size enums use `expect enum class` in `commonMain` and `actual enum class` in each platform source set (`androidMain`, `desktopMain`, `nativeMain`, `wasmJsMain`).

Enum entries that fall **below a platform's accessibility touch target guidelines** must be marked `@Deprecated` in that platform's `actual` declaration. Assess per-platform, per-component.

---

## API visibility

| Scope | What |
|---|---|
| `public` | Composable API, enums, state classes the consumer needs |
| `internal` | Colors, layout helpers, Impl files, TestTags, draw logic |

- `@ExperimentalCarbonApi` marks unstable APIs (requires opt-in)
- All `public` declarations must have **KDoc** including a link to the Carbon DS documentation page

---

## Icons

Icons are `internal val xyzIcon: ImageVector` properties in `carbon/.../icons/`.
Use them via `rememberVectorPainter(xyzIcon)`.
Available: CheckmarkFilled, Calendar, View, ViewOff, WarningFilled, WarningAlt, ErrorFilled, InformationFilled, Close, ChevronDown/Up/Left/Right.

---

## Resources

Resources (fonts, strings, drawables) are **integrated manually**. Do not auto-generate or assume resource entries exist — they must be added by hand to the resources directory and referenced explicitly.

---

## Testing

- All tests in `commonTest`, using `runComposeUiTest { }`
- Colors tests extend `BaseColorsTest` — use `forAllLayers` and `forAllLayersAndStates` helpers
- Use `forEachParameter()` for parameterized loops (avoids slow Activity recreation on Android)
- Test tags defined in `ComponentTestTags` internal objects, applied via `Modifier.testTag()`

---

## Previews

Each component file ends with a preview region:
```kotlin
// region Previews

@Preview
@Composable
private fun ComponentPreview() {
    CarbonDesignSystem {
        ComponentName(...)
    }
}

// endregion
```

Use `@PreviewParameter` with a provider class for parameterized previews covering all variants/states. Previews are always `private`.

---

## Code style & copyright

- **Every file** starts with the Apache 2.0 copyright header (year = current year, author = Gabriel Derrien):
    ```kotlin
    /*
     * Copyright 2026 Gabriel Derrien
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *     http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */
    ```
- Static analysis via **Detekt** (`config/detekt/detekt.yml`), zero issues allowed
- Use `@Suppress("RuleName")` only when a bypass is genuinely necessary
- Format code with the IDEA code style formatter (⌥⌘L on macOS)
