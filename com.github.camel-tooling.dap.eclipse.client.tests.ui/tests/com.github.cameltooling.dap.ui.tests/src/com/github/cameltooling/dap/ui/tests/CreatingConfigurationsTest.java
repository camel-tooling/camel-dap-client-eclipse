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
package com.github.cameltooling.dap.ui.tests;

import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.condition.ConsoleHasText;
import org.eclipse.reddeer.eclipse.ui.console.ConsoleView;
import org.eclipse.reddeer.eclipse.ui.perspectives.DebugPerspective;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.github.cameltooling.dap.reddeer.dialog.DebugConfigurationDialog;
import com.github.cameltooling.dap.reddeer.utils.ImportIntoWorkspace;
import com.github.cameltooling.dap.reddeer.utils.JavaProjectFactory;
import com.github.cameltooling.dap.reddeer.views.DebugView;

/**
 * Checks creating of debug configuration for Maven, Camel Textual Debug and
 * Launch Group.
 * 
 * @author fpospisi
 */
@OpenPerspective(DebugPerspective.class)
@RunWith(RedDeerSuite.class)
public class CreatingConfigurationsTest {

	public static final String RESOURCES_BUILDER_PATH = "resources/my-route.xml";

	public static final String PROJECT_FOLDER_PATH = "resources/camel-examples/main-xml";

	public static final String MVN_CONF = "mvn_conf";
	public static final String MVN_BASE = "${workspace_loc:/main-xml}";
	public static final String CTD_CONF = "ctd_conf";
	public static final String GROUPED_CONF = "grouped_conf";

	public static final String EXPECTED_LOG = "Hello how are you?";

	@BeforeClass
	public static void setupTestEnvironment() {
		ImportIntoWorkspace.importFolder(PROJECT_FOLDER_PATH);
	}

	@AfterClass
	public static void removeCreatedConfigurations() {
		DebugView.removeAllConfigurations();
		JavaProjectFactory.deleteAllProjects();
	}

	/*
	 * Create Maven configuration.
	 */
	@Test
	public void testCreatingMavenConfiguration() {
		new DebugConfigurationDialog().createMaven(MVN_CONF, MVN_BASE);
		assertTrue(new DebugConfigurationDialog().mavenExists(MVN_CONF));
	}

	/*
	 * Create Camel Textual Debug configuration.
	 */
	@Test
	public void testCreatingCTDConfiguration() {
		new DebugConfigurationDialog().createCTD(CTD_CONF);
		assertTrue(new DebugConfigurationDialog().CTDExists(CTD_CONF));
	}

	/*
	 * Grouped configuration and launch.
	 */
	@Test
	public void testCreatingLGConfigurations() {
		new DebugConfigurationDialog().createLaunchGroup(GROUPED_CONF, MVN_CONF, CTD_CONF);
		assertTrue(new DebugConfigurationDialog().launchGroupExists(GROUPED_CONF));

		new DebugConfigurationDialog().debugLaunchGroup(GROUPED_CONF, true);
		new ConsoleView().switchConsole(new RegexMatcher(".*" + MVN_CONF + ".*"));
		new WaitUntil(new ConsoleHasText(EXPECTED_LOG), TimePeriod.DEFAULT);

		new DebugView().terminateConfiguration(GROUPED_CONF, DebugView.GROUPED_DEBUG);
	}
}
