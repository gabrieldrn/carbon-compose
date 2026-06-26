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

package com.gabrieldrn.codegen.common

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.buildCodeBlock
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation

/**
 * Tree node used to both describe the structure of a generated token set class and render its
 * per-theme instantiation code.
 */
sealed class Node {

    /**
     * Spec representing this node for code generation.
     */
    abstract val propertySpec: PropertySpec

    /**
     * Returns the code block instantiating this node's value.
     */
    abstract fun toCodeBlock(): CodeBlock

    /**
     * Represents the root of a token set, or a reference to another already-generated token set
     * (see [ReferenceNode]) — a class containing sub-tokens.
     *
     * @param abstractionClassName Class representation of this node.
     * @param subNodes Sub-nodes contained in this class/node.
     * @param abstractionTypeSpec Type representation associated to this node.
     */
    class ClassNode(
        val abstractionClassName: ClassName,
        val subNodes: Collection<Node>,
        val abstractionTypeSpec: TypeSpec,
        override val propertySpec: PropertySpec,
    ) : Node() {

        /**
         * Returns the code block instantiating this class, gathering the code blocks of every
         * node in [subNodes]. [CompatNode]s are excluded — they aren't constructor parameters.
         */
        override fun toCodeBlock(): CodeBlock {
            val tokensCodeBlockSet = subNodes
                .filterNot { it is CompatNode }
                .map { it.toCodeBlock() }

            return buildCodeBlock {
                add("\n%T(\n", abstractionClassName)
                indent()
                indent()
                tokensCodeBlockSet.forEachIndexed { index, property ->
                    add(property)
                    if (index < tokensCodeBlockSet.size - 1) add(",")
                    add("\n")
                }
                unindent()
                unindent()
                add(")")
            }
        }
    }

    /**
     * Represents a single color design token.
     *
     * @param property Reflected property used to resolve the token name and annotations.
     * @param model Class instance containing this token.
     */
    class TokenNode(
        property: KProperty1<out Any, *>,
        value: String,
    ) : Node() {

        private val composeColorMemberName = MemberName("androidx.compose.ui.graphics", "Color")

        /**
         * Resolved color value as a Compose-compatible `0xAARRGGBB` hex literal. Carbon's hex
         * values are already in `AARRGGBB` order, unlike some other design token pipelines.
         */
        private val tokenValue = "0x%s".format(value.removePrefix("#"))

        override val propertySpec: PropertySpec = PropertySpec
            .builder(
                name = property.name,
                type = androidx.compose.ui.graphics.Color::class.asTypeName()
            )
            .initializer(property.name)
            .apply {
                @OptIn(DelicateKotlinPoetApi::class)
                property.findAnnotation<Deprecated>()
                    ?.let(AnnotationSpec::get)
                    ?.let(::addAnnotation)
            }
            .build()

        override fun toCodeBlock(): CodeBlock = buildCodeBlock {
            add("${propertySpec.name} = %M($tokenValue)", composeColorMemberName)
        }
    }

    /**
     * Represents a reference to an already-generated token set (e.g. a Carbon component's
     * colors, such as `ButtonColors`), used as a property of another token set (e.g. `Theme`).
     * Unlike [ClassNode], the referenced class is *not* nested/redeclared here — only a
     * reference to its existing per-theme top-level instance is emitted.
     *
     * @param property Reflected property used to resolve the token name.
     * @param type Class of the referenced token set.
     * @param referencedInstanceName Name of the already-generated top-level property for this
     *   theme (e.g. `Gray10ButtonColors`).
     */
    class ReferenceNode(
        property: KProperty1<out Any, *>,
        type: ClassName,
        referencedInstanceName: String,
    ) : Node() {

        private val referencedInstanceMemberName =
            MemberName(type.packageName, referencedInstanceName)

        override val propertySpec: PropertySpec = PropertySpec
            .builder(name = property.name, type = type)
            .initializer(property.name)
            .build()

        override fun toCodeBlock(): CodeBlock = buildCodeBlock {
            add("${propertySpec.name} = %M", referencedInstanceMemberName)
        }
    }

    /**
     * Represents a deprecated compatibility shim that generates a computed property rather than
     * a constructor parameter.
     *
     * [toCodeBlock] returns an empty [CodeBlock] because compat nodes are not constructor
     * parameters — they have no entry in per-theme constructor call expressions. Their value is
     * resolved at runtime via a getter.
     */
    sealed class CompatNode : Node() {

        /**
         * Generates a computed deprecated property that delegates to [newName] at runtime:
         * ```kotlin
         * @Deprecated("Renamed to 'newName'.", replaceWith = ReplaceWith("newName"))
         * val oldName: T get() = newName
         * ```
         *
         * @param property Reflected property representing the old (renamed) token name.
         * @param newName New property name this token was renamed to.
         * @param propertyType [TypeName] of the generated property (must match the type of
         *   [newName] in the containing class).
         */
        class Renamed(
            property: KProperty1<out Any, *>,
            newName: String,
            propertyType: TypeName,
        ) : CompatNode() {

            override val propertySpec: PropertySpec = PropertySpec
                .builder(name = property.name, type = propertyType)
                .addAnnotation(
                    AnnotationSpec.builder(Deprecated::class)
                        .addMember("message = %S", "Renamed to '$newName'.")
                        .addMember(
                            CodeBlock.of("replaceWith = ReplaceWith(expression = %S)", newName)
                        )
                        .build()
                )
                .getter(
                    FunSpec.getterBuilder()
                        .addStatement("return %L", newName)
                        .build()
                )
                .build()

            override fun toCodeBlock(): CodeBlock = CodeBlock.of("")
        }
    }
}
