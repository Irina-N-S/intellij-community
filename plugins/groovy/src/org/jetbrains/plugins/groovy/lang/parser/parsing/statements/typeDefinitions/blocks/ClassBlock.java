/*
 * Copyright 2000-2007 JetBrains s.r.o.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.plugins.groovy.lang.parser.parsing.statements.typeDefinitions.blocks;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.plugins.groovy.GroovyBundle;
import org.jetbrains.plugins.groovy.lang.lexer.GroovyElementType;
import org.jetbrains.plugins.groovy.lang.parser.GroovyElementTypes;
import org.jetbrains.plugins.groovy.lang.parser.parsing.auxiliary.Separators;
import org.jetbrains.plugins.groovy.lang.parser.parsing.statements.typeDefinitions.members.ClassMember;
import org.jetbrains.plugins.groovy.lang.parser.parsing.util.ParserUtils;

/**
 * @autor: Dmitry.Krasilschikov
 * @date: 16.03.2007
 */
public class ClassBlock implements GroovyElementTypes {
  public static GroovyElementType parse(PsiBuilder builder, String className) {
    //see also InterfaceBlock, EnumBlock, AnnotationBlock
    //allow errors
    PsiBuilder.Marker cbMarker = builder.mark();

    if (!ParserUtils.getToken(builder, mLCURLY)) {
      builder.error(GroovyBundle.message("lcurly.expected"));
      cbMarker.rollbackTo();
      return WRONGWAY;
    }

    ClassMember.parse(builder, className);

    IElementType sep = Separators.parse(builder);

    while (!WRONGWAY.equals(sep)) {
      ClassMember.parse(builder, className);

      sep = Separators.parse(builder);
    }

    if (builder.getTokenType() != mRCURLY) {
      builder.error(GroovyBundle.message("rcurly.expected"));
    }

    while (!builder.eof() && !ParserUtils.getToken(builder, mRCURLY)) {
      ClassMember.parse(builder, className);

      builder.advanceLexer();
    }

    cbMarker.done(CLASS_BODY);
    return CLASS_BODY;
  }
}
