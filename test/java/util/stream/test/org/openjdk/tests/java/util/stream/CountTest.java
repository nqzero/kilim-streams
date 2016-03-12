/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/**
 * @test
 * @summary Tests counting of streams
 * @bug 8031187
 */

package org.openjdk.tests.java.util.stream;

import java.util.concurrent.atomic.AtomicLong;
import stream2.DoubleStream;
import stream2.DoubleStreamTestDataProvider;
import stream2.IntStream;
import stream2.IntStreamTestDataProvider;
import stream2.LongStream;
import stream2.LongStreamTestDataProvider;
import stream2.OpTestCase;
import stream2.Stream;
import stream2.StreamTestDataProvider;
import stream2.TestData;

import org.testng.annotations.Test;

public class CountTest extends OpTestCase {

    @Test(dataProvider = "StreamTestData<Integer>", dataProviderClass = StreamTestDataProvider.class)
    public void testOps(String name, TestData.OfRef<Integer> data) {
        AtomicLong expectedCount = new AtomicLong();
        data.stream().forEach(e -> expectedCount.incrementAndGet());

        withData(data).
                terminal(Stream::count).
                expectedResult(expectedCount.get()).
                exercise();
    }

    @Test(dataProvider = "IntStreamTestData", dataProviderClass = IntStreamTestDataProvider.class)
    public void testOps(String name, TestData.OfInt data) {
        AtomicLong expectedCount = new AtomicLong();
        data.stream().forEach(e -> expectedCount.incrementAndGet());

        withData(data).
                terminal(IntStream::count).
                expectedResult(expectedCount.get()).
                exercise();
    }

    @Test(dataProvider = "LongStreamTestData", dataProviderClass = LongStreamTestDataProvider.class)
    public void testOps(String name, TestData.OfLong data) {
        AtomicLong expectedCount = new AtomicLong();
        data.stream().forEach(e -> expectedCount.incrementAndGet());

        withData(data).
                terminal(LongStream::count).
                expectedResult(expectedCount.get()).
                exercise();
    }

    @Test(dataProvider = "DoubleStreamTestData", dataProviderClass = DoubleStreamTestDataProvider.class)
    public void testOps(String name, TestData.OfDouble data) {
        AtomicLong expectedCount = new AtomicLong();
        data.stream().forEach(e -> expectedCount.incrementAndGet());

        withData(data).
                terminal(DoubleStream::count).
                expectedResult(expectedCount.get()).
                exercise();
    }
}
