# `:carbon`

Main module from where is created the published `io.github.gabrieldrn:carbon` artifact.

It contains Carbon's foundation elements such as colors, themes, typography, etc. and the
implementations of all the components.

---

This module has sub-projects and is structured as follows:

```
(.)carbon <- Main module / published 'carbon' library
├── src
├── ...
├── build.gradle.kts
│
├── common <- Common code shared between :carbon and :carbon:test / published 'carbon-common' library
│   ├── src
│   ├── ...
│   └── build.gradle.kts
│
└── test <- Test utilities module / published 'carbon-test' library
    ├── src
    ├── ...
    └── build.gradle.kts
```
