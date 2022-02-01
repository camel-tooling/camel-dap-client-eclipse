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
package com.github.cameltooling.dap.eclipse.client;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.URIUtil;
import org.osgi.framework.Bundle;

public class CamelDAPJarAcccessor {
	
	private CamelDAPJarAcccessor() {
		// static methods only
	}

	public static String computeCamelDebugAdapterServerJarPath() {
		String camelDebugAdapterServerJarPath = "";
		Bundle bundle = Platform.getBundle(CamelDAPClientActivator.ID);
		URL fileURL = bundle.findEntries("/libs", "camel-dap-server-*.jar", false).nextElement();
		try {
			URL resolvedUrl = FileLocator.resolve(fileURL);
			File file = new File(URIUtil.toURI(resolvedUrl));
			if (Platform.OS_WIN32.equals(Platform.getOS())) {
				camelDebugAdapterServerJarPath = "\"" + file.getAbsolutePath() + "\"";
			} else {
				camelDebugAdapterServerJarPath = file.getAbsolutePath();
			}
		} catch (URISyntaxException | IOException exception) {
			CamelDAPClientActivator.getInstance().getLog().log(new Status(IStatus.ERROR, CamelDAPClientActivator.ID,
					"Cannot get the Camel DAP Server jar.", exception)); //$NON-NLS-1$
		}
		return camelDebugAdapterServerJarPath;
	}
	
}
