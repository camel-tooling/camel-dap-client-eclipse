/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cameltooling.dap.ui.tests.suite;

import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.github.cameltooling.dap.ui.tests.AttachingDebuggerTest;
import com.github.cameltooling.dap.ui.tests.CreatingConfigurationsTest;
import com.github.cameltooling.dap.ui.tests.DebuggingCamelDSLsTest;
import com.github.cameltooling.dap.ui.tests.InspectVariableTest;
import com.github.cameltooling.dap.ui.tests.PluginInfoTest;

import junit.framework.TestSuite;

/**
 * Runs Smoke Tests suite for Camel DAP Eclipse Client
 *
 * @author djelinek
 */
@SuiteClasses({
	AttachingDebuggerTest.class,
	CreatingConfigurationsTest.class,
	DebuggingCamelDSLsTest.class,
	InspectVariableTest.class,
	PluginInfoTest.class,
	})

@RunWith(RedDeerSuite.class)
public class SmokeTests extends TestSuite {

}
