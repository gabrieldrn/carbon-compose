# `:doc-parser`
This module is a Kotlin/JS project using the `@carbon/themes` package to extract all color tokens 
from the Carbon Design System and generate JSON files with the extracted data. Each JSON file 
corresponds to a theme and are then used by the `:code-gen` module to generate Kotlin 
implementations from those tokens.
