/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
package stream2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * LoggingTestCase
 *
 */
@Test
public class LoggingTestCase extends Assert {
    private Map<String, Object> context = new HashMap<>();

    @BeforeMethod
    public void before() {
        context.clear();
    }

    @AfterMethod
    public void after(ITestResult result) {
        if (!result.isSuccess()) {
            List<Object> list = new ArrayList<>();
            Collections.addAll(list, result.getParameters());
            list.add(context.toString());
            result.setParameters(list.toArray(new Object[list.size()]));
        }
    }

    protected void setContext(String key, Object value) {
        context.put(key, value);
    }

    protected void clearContext(String key) {
        context.remove(key);
    }
}
