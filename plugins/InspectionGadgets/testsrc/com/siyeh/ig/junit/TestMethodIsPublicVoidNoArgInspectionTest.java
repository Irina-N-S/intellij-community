/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
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
package com.siyeh.ig.junit;

import com.intellij.codeInspection.InspectionProfileEntry;
import com.siyeh.ig.LightJavaInspectionTestCase;
import org.jetbrains.annotations.Nullable;

/**
 * @author Bas Leijdekkers
 */
public class TestMethodIsPublicVoidNoArgInspectionTest extends LightJavaInspectionTestCase {

  public void testJUnit3TestMethodIsPublicVoidNoArg() { doTest(); }
  public void testJUnit4TestMethodIsPublicVoidNoArg() { doTest(); }
  public void testJUnit4RunWith() { doTest(); }

  @Nullable
  @Override
  protected InspectionProfileEntry getInspection() {
    return new TestMethodIsPublicVoidNoArgInspection();
  }

  @Override
  protected String[] getEnvironmentClasses() {
    return new String[] {
      "package org.junit; " +
      "public @interface Test {\n" +
      "    java.lang.Class<? extends java.lang.Throwable> expected() default org.junit.Test.None.class;" +
      "}",

      "package org.junit.runner;" +
      "@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)\n" +
      "@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})\n" +
      "@java.lang.annotation.Inherited\n" +
      "public @interface RunWith {\n" +
      "    Class<? extends Runner> value();\n" +
      "}",

      "package org.junit.runner;" +
      "public abstract class Runner {}",

      "package junit.framework;" +
      "public abstract class TestCase {}",

      "package mockit;" +
      "@Retention(value=RUNTIME) @Target(value={FIELD,PARAMETER})" +
      "public @interface Mocked {}"};
  }
}
