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

package com.gabrieldrn.docparser

import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape

private const val host = "https://carbondesignsystem.com"
private const val colorTokensPath = "/elements/color/tokens/"

private const val colorTokenScriptName = "component---src-pages-elements-color-tokens-mdx"

fun main() {
    val webpackRuntimeScriptPath = skrape(HttpFetcher) {
        request {
            url = host + colorTokensPath
        }
        response {
            htmlDocument {
                val scriptHtmlElement = html
                    .split("\n")
                    .first { it.contains("webpack-runtime") }
                    .trim()

                val jsExtPosition = scriptHtmlElement.indexOf(".js")

                scriptHtmlElement.subSequence(
                    scriptHtmlElement.indexOf('/'),
                    jsExtPosition + 3
                )
                    .also(::println)
            }
        }
    }

    // The color token script path has a suffix identified by and id in the webpack runtime script
    val colorTokenScriptPath = skrape(HttpFetcher) {
        request {
            url = host + webpackRuntimeScriptPath
        }
        response {
            htmlDocument {
                val fileId = html
                    .substringBefore(":\"$colorTokenScriptName")
                    .substringAfterLast(',')

                val fileNameSuffix = html
                    .substringAfterLast("$fileId:\"")
                    .substringBefore('"')

                "/$colorTokenScriptName-$fileNameSuffix.js"
                    .also(::println)
            }
        }
    }

    skrape(HttpFetcher) {
        request {
            url = host + colorTokenScriptPath
        }
        response {
            htmlDocument {
                println(allElements.joinToString { "$it\n".trim() })
            }
        }
    }
}
