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

import static org.junit.Assert.assertEquals;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.condition.ConsoleHasText;
import org.eclipse.reddeer.eclipse.core.resources.ProjectItem;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.ui.perspectives.DebugPerspective;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.cameltooling.dap.reddeer.dialog.DebugConfigurationDialog;
import com.github.cameltooling.dap.reddeer.dialog.DefaultEditorDialog;
import com.github.cameltooling.dap.reddeer.dialog.RunConfigurationDialog;
import com.github.cameltooling.dap.reddeer.editor.GenericEditor;
import com.github.cameltooling.dap.reddeer.utils.ImportIntoWorkspace;
import com.github.cameltooling.dap.reddeer.utils.JavaProjectFactory;
import com.github.cameltooling.dap.reddeer.views.DebugView;

/**
 * 
 * @author fpospisi
 */
@OpenPerspective(DebugPerspective.class)
@RunWith(RedDeerSuite.class)
public class InspectVariableTest {

	public static final String PROJECT_FOLDER_PATH = "resources/camel-examples/main-xml";
	public static final String PROJECT_NAME = "main-xml";

	public static final String MVN_CONF = "mvn_conf";
	public static final String MVN_BASE = "${workspace_loc:/main-xml}";
	public static final String CTD_CONF = "ctd_conf";
	public static final String GROUPED_CONF = "grouped_conf";

	public static final String[] ROUTE_PATH = { "src", "main", "resources", "routes", "my-route.xml" };

	public static final String EXPECTED_MESSAGE_1 = "Hello how are you?";
	public static final String EXPECTED_MESSAGE_2 = "Bye World";

	/**
	 * Prepare test environment.
	 */
	@BeforeClass
	public static void setupTestEnvironment() {
		DefaultEditorDialog.setDefault("*.xml", GenericEditor.GTE);
		
		ImportIntoWorkspace.importFolder(PROJECT_FOLDER_PATH);

		new RunConfigurationDialog().createMaven(MVN_CONF, MVN_BASE);
		new DebugConfigurationDialog().createCTD(CTD_CONF);
		new DebugConfigurationDialog().createLaunchGroup(GROUPED_CONF, MVN_CONF, CTD_CONF);
	}

	/**
	 * Clean after test.
	 */
	@AfterClass
	public static void cleanTestEnvironment() {
		DebugView.removeAllConfigurations();
		JavaProjectFactory.deleteAllProjects();
	}

	/**
	 * Test for inspecting variable when breakpoint is hit.
	 */
	@Test
	public void testInspectVariable() {
		ProjectExplorer explorer = new ProjectExplorer();
		explorer.open();
		ProjectItem routeFile = explorer.getProject(PROJECT_NAME).getProjectItem(ROUTE_PATH);
		routeFile.open();

		// Set breakpoint. 
		GenericEditor editor = new GenericEditor();
		editor.activate();
		editor.setPosition(editor.getPosition("<log message=\"${body}\"/>"));
		editor.setBreakpoint();

		// Run configuration.
		new DebugConfigurationDialog().debugLaunchGroup(GROUPED_CONF, true);
		new WaitUntil(new ConsoleHasText(EXPECTED_MESSAGE_1), TimePeriod.LONG);
		
		// Wait until breakpoint is hit and variable loaded.
		DebugView.waitUntilVariableIsLoaded();
		assertEquals(EXPECTED_MESSAGE_1, DebugView.getMessageBodyFromVariable());

		new DebugView().disableAllbreakpoints();
	}
}
