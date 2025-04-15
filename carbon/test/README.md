# `:carbon:test`

This module aims to provide testings methods specific to the API of Carbon. For example, you can 
find convenience functions to test the read-only state of a component, as it's not a default state
provided by the Compose framework.

Using this module as a dependency of production code (`commonMain`) is an error. This should only be
used in test source sets.
