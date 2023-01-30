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

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.reddeer.common.wait.AbstractWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.condition.ConsoleHasText;
import org.eclipse.reddeer.eclipse.core.resources.ProjectItem;
import org.eclipse.reddeer.eclipse.debug.ui.views.breakpoints.BreakpointsView;
import org.eclipse.reddeer.eclipse.ui.console.ConsoleView;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.ui.perspectives.DebugPerspective;
import org.eclipse.reddeer.junit.internal.runner.ParameterizedRequirementsRunnerFactory;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;

import com.github.cameltooling.dap.reddeer.condition.VariableIsLoaded;
import com.github.cameltooling.dap.reddeer.dialog.DebugConfigurationDialog;
import com.github.cameltooling.dap.reddeer.dialog.DefaultEditorDialog;
import com.github.cameltooling.dap.reddeer.dialog.RunConfigurationDialog;
import com.github.cameltooling.dap.reddeer.editor.GenericEditor;
import com.github.cameltooling.dap.reddeer.utils.ImportIntoWorkspace;
import com.github.cameltooling.dap.reddeer.utils.JavaProjectFactory;
import com.github.cameltooling.dap.reddeer.views.DebugView;

@OpenPerspective(DebugPerspective.class)
@RunWith(RedDeerSuite.class)
@UseParametersRunnerFactory(ParameterizedRequirementsRunnerFactory.class)
public class DebuggingCamelDSLsTest {

	public static final String MVN_CONF = "mvn_conf";
	public static final String CTD_CONF = "ctd_conf";

	public static final String PROJECT_FOLDER_PATH = "resources/camel-examples/main-";

	public static final String EXPECTED_MVN_LOG = "Hello how are you?";
	public static final String EXPECTED_CTD_LOG = "\"command\":\"attach\",\"success\":true}";

	public static final String EXPECTED_MESSAGE_1 = "Hello how are you?";
	public static final String EXPECTED_MESSAGE_2 = "Bye World";

	// used file type
	@Parameter
	public static String filetype;

	// name of imported project
	@Parameter(1)
	public String project;

	// path to route file
	@Parameter(2)
	public String[] path;

	// definition of log in route file specific file type
	@Parameter(3)
	public String log;

	// how many steps over are required between logs
	@Parameter(4)
	public int steps;

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "xml", "main-xml", new String[] { "src", "main", "resources", "routes", "my-route.xml" },
						"<log message=\"${body}\"/>", 3 },
				{ "yaml", "main-yaml", new String[] { "src", "main", "resources", "routes", "my-route.yaml" },
						"- log: \"${body}\"", 3 },
				{ "java", "main-java", new String[] { "src", "main", "java", "org", "apache", "camel", "example",
						"MyRouteBuilder.java" }, ".log(\"${body}\")", 2 } });
	}

	/**
	 * Sets Generic Editor as default editor for xml. Imports project and creates
	 * run/debug configurations.
	 */
	@Before

	public void setupTestEnvironment() {
		DefaultEditorDialog.setDefault("*." + filetype, GenericEditor.GTE);

		ImportIntoWorkspace.importFolder(PROJECT_FOLDER_PATH + filetype);

		new RunConfigurationDialog().createMaven(MVN_CONF, "${workspace_loc:/" + project + "}");
		new DebugConfigurationDialog().createCTD(CTD_CONF);
	}

	@After
	public void cleanTestEnvironment() {
		new DebugView().disconnectConfiguration(CTD_CONF, DebugView.CAMEL_TEXT_DEBUG);
		new DebugView().terminateConfiguration(MVN_CONF, DebugView.MVN_DEBUG);

		new BreakpointsView().removeAllBreakpoints();

		DebugView.removeAllConfigurations();

		JavaProjectFactory.deleteAllProjects();
	}

	@Test
	public void DSLTest() {
		// Open file with camel route.
		ProjectExplorer explorer = new ProjectExplorer();
		explorer.open();
		ProjectItem routeFile = explorer.getProject(project).getProjectItem(path);
		routeFile.open();

		// Run project, wait until it's properly running.
		new RunConfigurationDialog().runMaven(MVN_CONF);
		new WaitUntil(new ConsoleHasText(EXPECTED_MVN_LOG), TimePeriod.LONG);

		// Open Console view
		ConsoleView console = new ConsoleView();
		console.activate();
		console.toggleShowConsoleOnStandardOutChange(false);

		// Run CTD, wait until attached.
		new DebugConfigurationDialog().debugCTD(CTD_CONF, true);
		new WaitUntil(new ConsoleHasText(EXPECTED_CTD_LOG), TimePeriod.LONG);

		// Set breakpoint.
		GenericEditor editor = new GenericEditor();
		editor.activate();

		editor.setPosition(editor.getPosition(log));
		editor.setBreakpoint();

		// Check Message Body.
		new WaitUntil(new VariableIsLoaded(), TimePeriod.VERY_LONG);
		assertEquals(EXPECTED_MESSAGE_1, DebugView.getMessageBodyFromVariable());

		// Do steps inside code.
		for (int i = 0; i < steps; i++) {
			AbstractWait.sleep(TimePeriod.getCustom(3));
			editor.stepOver();
		}

		// Disable first breakpoint.
		new DebugView().disableAllbreakpoints();

		// Add new breakpoint.
		editor.activate();
		editor.setBreakpoint();
			
		new WaitUntil(new VariableIsLoaded(), TimePeriod.VERY_LONG);
		assertEquals(EXPECTED_MESSAGE_2, DebugView.getMessageBodyFromVariable());

		// Disable second breakpoint.
		new DebugView().disableAllbreakpoints();
	}
}
