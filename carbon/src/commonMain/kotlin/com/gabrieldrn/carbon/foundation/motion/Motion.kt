/*
 * Copyright 2024 Gabriel Derrien
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

package com.gabrieldrn.carbon.foundation.motion

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.runtime.Immutable

/**
 * # Carbon motion
 * (From [Motion guidelines](https://carbondesignsystem.com/guidelines/motion/overview/))
 *
 * Carbon components have motion built in for microinteractions. However, the motion design of the
 * overarching UI — that is, how the components interact with each other and enter and exit the page
 * itself — is up to each product team to implement. Use this guidance to customize, combine,
 * coordinate, and choreograph this aspect of motion in the UI.
 *
 * ## Productive motion
 * Productive motion creates a sense of efficiency and responsiveness, while remaining subtle and
 * out of the way. Productive motion is appropriate for moments when the user needs to focus on
 * completing tasks. Microinteractions in Carbon such as button states, dropdowns, revealing
 * additional information, or rendering data tables and visualizations were all designed with
 * productive motion.
 *
 * ## Expressive motion
 * Expressive motion delivers enthusiastic, vibrant, and highly visible movement. Use expressive
 * motion for significant moments such as opening a new page, clicking the primary action button,
 * or when the movement itself conveys a meaning. System alerts and the appearance of notification
 * boxes are great cases for expressive motion.
 */
@Immutable
public object Motion {

    /**
     * # Duration
     * (From [Motion guidelines](https://carbondesignsystem.com/guidelines/motion/overview/))
     *
     * Motion duration in Carbon is normally calculated based on the style and size of the motion.
     * This calculation is an upcoming feature. Currently, there are six static value tokens for
     * easier implementation.
     */
    @Immutable
    public object Duration {

        /**
         * Micro-interactions such as button and toggle.
         */
        public const val fast01: Int = 70

        /**
         * Micro-interactions such as fade.
         */
        public const val fast02: Int = 110

        /**
         * Micro-interactions, small expansion, short distance movements.
         */
        public const val moderate01: Int = 150

        /**
         * Expansion, system communication, toast.
         */
        public const val moderate02: Int = 240

        /**
         * Large expansion, important system notifications.
         */
        public const val slow01: Int = 400

        /**
         * Background dimming.
         */
        public const val slow02: Int = 700
    }

    /**
     * # Standard easing
     * (From [Motion guidelines](https://carbondesignsystem.com/guidelines/motion/overview/))
     *
     * Use standard easing when an element is visible from the beginning to the end of a motion.
     * Expanding tiles and the sorting of table rows are good examples.
     */
    @Immutable
    public object Standard {

        /**
         * Standard easing for productive motion.
         * @see [Motion]
         * @see [Standard]
         */
        public val productiveEasing: CubicBezierEasing =
            CubicBezierEasing(0.2f, 0f, 0.38f, 0.9f)

        /**
         * Standard easing for expressive motion.
         * @see [Motion]
         * @see [Standard]
         */
        public val expressiveEasing: CubicBezierEasing =
            CubicBezierEasing(0.4f, 0.14f, 0.3f, 1f)
    }

    /**
     * # Entrance easing
     * (From [Motion guidelines](https://carbondesignsystem.com/guidelines/motion/overview/))
     *
     * With this style, an element quickly appears and slows down to a stop. Use entrance ease when
     * adding elements to the view, such as a modal or toaster appearing. Elements moving in
     * response to the user’s input, such as a dropdown opening or toggle switching should also use
     * this style.
     */
    @Immutable
    public object Entrance {

        /**
         * Entrance easing for productive motion.
         * @see [Motion]
         * @see [Entrance]
         */
        public val productiveEasing: CubicBezierEasing =
            CubicBezierEasing(0f, 0f, 0.38f, 0.9f)

        /**
         * Entrance easing for expressive motion.
         * @see [Motion]
         * @see [Entrance]
         */
        public val expressiveEasing: CubicBezierEasing =
            CubicBezierEasing(0f, 0f, 0.3f, 1f)
    }

    /**
     * # Exit easing
     * (From [Motion guidelines](https://carbondesignsystem.com/guidelines/motion/overview/))
     *
     * Use exit easing when removing elements from view, such as closing a modal or toaster. The
     * element speeds up as it exits from view, implying that its departure from the screen is
     * permanent.
     *
     * An exception to exits: if an element leaves the view but stays nearby, ready to reappear upon
     * user action, use standard easing instead. A good example of this is a side panel. The panel
     * leaves the view, but slows down as it exits, implying that it would come to rest just outside
     * the view, and ready to be recalled.
     */
    @Immutable
    public object Exit {

        /**
         * Exit easing for productive motion.
         * @see [Motion]
         * @see [Exit]
         */
        public val productiveEasing: CubicBezierEasing =
            CubicBezierEasing(0.2f, 0f, 1f, 0.9f)

        /**
         * Exit easing for expressive motion.
         * @see [Motion]
         * @see [Exit]
         */
        public val expressiveEasing: CubicBezierEasing =
            CubicBezierEasing(0.4f, 0.14f, 1f, 1f)
    }
}
