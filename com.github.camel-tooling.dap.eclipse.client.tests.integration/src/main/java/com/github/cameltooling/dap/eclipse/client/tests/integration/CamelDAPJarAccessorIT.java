/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cameltooling.dap.eclipse.client.tests.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.junit.Test;

import com.github.cameltooling.dap.eclipse.client.CamelDAPJarAcccessor;

public class CamelDAPJarAccessorIT {

	@Test
	public void testFindJar() throws Exception {
		String path = CamelDAPJarAcccessor.computeCamelDebugAdapterServerJarPath();
		if (Platform.OS_WIN32.equals(Platform.getOS())) {
			// This is a dirty workaround in the test as we are passing encapsulated string for windows and not the other OS.
			path = path.substring(1, path.length() -1);
		}
		assertThat(new File(path)).exists();
	}
	
}
