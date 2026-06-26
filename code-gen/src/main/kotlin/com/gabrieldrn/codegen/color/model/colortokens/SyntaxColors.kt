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

package com.gabrieldrn.codegen.color.model.colortokens

import kotlinx.serialization.Serializable

/**
 * Unlike Carbon's other color components, syntax tokens are flattened directly at the root of
 * the theme JSON (no `syntaxColors` wrapper object) — [com.gabrieldrn.codegen.color.serializers.ThemeSerializer]
 * nests them under this model's shape before deserialization.
 */
@Serializable
data class SyntaxColors(
    val syntaxAngleBracket: String,
    val syntaxAnnotation: String,
    val syntaxArithmeticOperator: String,
    val syntaxAtom: String,
    val syntaxAttribute: String,
    val syntaxAttributeName: String,
    val syntaxAttributeValue: String,
    val syntaxBitwiseOperator: String,
    val syntaxBlockComment: String,
    val syntaxBool: String,
    val syntaxBrace: String,
    val syntaxBracket: String,
    val syntaxCharacter: String,
    val syntaxClassName: String,
    val syntaxColor: String,
    val syntaxComment: String,
    val syntaxCompareOperator: String,
    val syntaxConstant: String,
    val syntaxContent: String,
    val syntaxContentSeparator: String,
    val syntaxControlKeyword: String,
    val syntaxControlOperator: String,
    val syntaxDefinition: String,
    val syntaxDefinitionKeyword: String,
    val syntaxDefinitionOperator: String,
    val syntaxDeleted: String,
    val syntaxDerefOperator: String,
    val syntaxDocComment: String,
    val syntaxDocString: String,
    val syntaxDocumentMeta: String,
    val syntaxEmphasis: String,
    val syntaxEscape: String,
    val syntaxFloat: String,
    val syntaxFunction: String,
    val syntaxHeading: String,
    val syntaxHeading1: String,
    val syntaxHeading2: String,
    val syntaxHeading3: String,
    val syntaxHeading4: String,
    val syntaxHeading5: String,
    val syntaxHeading6: String,
    val syntaxInserted: String,
    val syntaxInteger: String,
    val syntaxInvalid: String,
    val syntaxKeyword: String,
    val syntaxLabelName: String,
    val syntaxLineComment: String,
    val syntaxLink: String,
    val syntaxList: String,
    val syntaxLiteral: String,
    val syntaxLocal: String,
    val syntaxLogicOperator: String,
    val syntaxMacroName: String,
    val syntaxMeta: String,
    val syntaxModifier: String,
    val syntaxModuleKeyword: String,
    val syntaxMonospace: String,
    val syntaxName: String,
    val syntaxNamespace: String,
    val syntaxNull: String,
    val syntaxNumber: String,
    val syntaxOperator: String,
    val syntaxOperatorKeyword: String,
    val syntaxParen: String,
    val syntaxProcessingInstruction: String,
    val syntaxPropertyName: String,
    val syntaxPunctuation: String,
    val syntaxQuote: String,
    val syntaxRegexp: String,
    val syntaxSelf: String,
    val syntaxSeparator: String,
    val syntaxSpecial: String,
    val syntaxSpecialString: String,
    val syntaxSquareBracket: String,
    val syntaxStandard: String,
    val syntaxStrikethrough: String,
    val syntaxString: String,
    val syntaxStrong: String,
    val syntaxTag: String,
    val syntaxTagName: String,
    val syntaxType: String,
    val syntaxTypeName: String,
    val syntaxTypeOperator: String,
    val syntaxUnit: String,
    val syntaxUpdateOperator: String,
    val syntaxUrl: String,
    val syntaxVariable: String,
    val syntaxVariableName: String,
)
