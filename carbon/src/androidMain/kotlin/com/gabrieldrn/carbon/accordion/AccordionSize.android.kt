/*
 * Copyright 2025 Gabriel Derrien
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

package com.gabrieldrn.carbon.accordion

import com.gabrieldrn.carbon.foundation.SMALL_TOUCH_TARGET_SIZE_MESSAGE

/**
 * Enum class representing different sizes for an accordion.
 *
 * (From [Accordion documentation](https://carbondesignsystem.com/components/accordion/style/#sizes)
 */
@Suppress("UndocumentedPublicProperty")
public actual enum class AccordionSize {

    @Deprecated(
        message = SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        replaceWith = ReplaceWith("Large")
    )
    Small,

    @Deprecated(
        message = SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        replaceWith = ReplaceWith("Large")
    )
    Medium,

    Large;
}
