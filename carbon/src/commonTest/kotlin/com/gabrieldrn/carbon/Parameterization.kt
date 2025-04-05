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

@file:Suppress("Unused", "NestedBlockDepth")

package com.gabrieldrn.carbon

import co.touchlab.kermit.Logger

/*
These methods are mainly used for compose UI testing to optimize the execution time of the tests.
For Android, using a paramaterization library involves waiting for the activity to be recreated
for each test case, which is really slow. Instead, these methods allow to run all the test cases
in the same activity. This requires to use mutable states at class level to store the parameters,
it's not ideal but it's a trade-off between execution time and code readability.
 */

const val PARAMTRZD_DEPRECATION_MESSAGE =
    "This parameterization method is deprecated, use the common forEachParameter method instead."
const val PARAMTRZD_DEPRECATION_REPLACE = "forEachParameter"

inline fun <reified A : Any, reified B : Any> forEachParameter(
    aValues: Array<A>,
    bValues: Array<B>,
    block: (A, B) -> Unit
) {
    val aTypeName = A::class.simpleName
    val bTypeName = B::class.simpleName

    aValues.forEach { a ->
        bValues.forEach { b ->
            Logger.d("$aTypeName: $a, $bTypeName: $b")
            block(a, b)
        }
    }
}

inline fun <reified A : Any, reified B : Any, reified C : Any> forEachParameter(
    aValues: Array<A>,
    bValues: Array<B>,
    cValues: Array<C>,
    block: (A, B, C) -> Unit
) {
    val aTypeName = A::class.simpleName
    val bTypeName = B::class.simpleName
    val cTypeName = C::class.simpleName
    aValues.forEach { a ->
        bValues.forEach { b ->
            cValues.forEach { c ->
                Logger.d("$aTypeName: $a, $bTypeName: $b, $cTypeName: $c")
                block(a, b, c)
            }
        }
    }
}

inline fun <reified A : Any, reified B : Any, reified C : Any, reified D : Any> forEachParameter(
    aValues: Array<A>,
    bValues: Array<B>,
    cValues: Array<C>,
    dValues: Array<D>,
    block: (A, B, C, D) -> Unit
) {
    val aTypeName = A::class.simpleName
    val bTypeName = B::class.simpleName
    val cTypeName = C::class.simpleName
    val dTypeName = D::class.simpleName

    aValues.forEach { a ->
        bValues.forEach { b ->
            cValues.forEach { c ->
                dValues.forEach { d ->
                    Logger.d("$aTypeName: $a, $bTypeName: $b, $cTypeName: $c, $dTypeName: $d")
                    block(a, b, c, d)
                }
            }
        }
    }
}
