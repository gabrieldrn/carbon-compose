# `:carbon`

Main module containing the published `io.github.gabrieldrn:carbon` artifact providing the 
`carbon-compose` library.

This module has sub-projects and is structured as follows:

```
(.)carbon <- Main module / published 'carbon-compose' library
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
