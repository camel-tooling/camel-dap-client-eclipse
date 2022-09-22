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

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.condition.ConsoleHasText;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.ui.perspectives.DebugPerspective;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.eclipse.reddeer.workbench.impl.editor.DefaultEditor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.cameltooling.dap.reddeer.dialog.DebugConfigurationDialog;
import com.github.cameltooling.dap.reddeer.utils.CreateNewEmptyFile;
import com.github.cameltooling.dap.reddeer.utils.JavaProjectFactory;
import com.github.cameltooling.dap.reddeer.views.DebugView;
import com.github.cameltooling.dap.ui.tests.utils.EditorManipulator;

/**
 * 
 * @author fpospisi
 */
@OpenPerspective(DebugPerspective.class)
@RunWith(RedDeerSuite.class)
public class AttachingDebuggerTest {

	private static final String PROJECT_NAME = "attaching-dap";
	private static final String CAMEL_CONTEXT = "camel-context.xml";
	
	public static final String CTD = "Camel Textual Debug";
	public static final String DEBUG_CONF = "debug_conf";

	public static final String RESOURCES_CONTEXT_PATH = "resources/camel-context-cbr.xml";
 
	/**
	 * Prepares test environment. Creates Java project, XML camel context and
	 * default Apache Camel Textual Debug configuration.
	 */
	@BeforeClass
	public static void setupTestEnvironment() {
		//create debug configuration
		DebugConfigurationDialog.createCTD(DEBUG_CONF);
		
		//create project with camel context
		JavaProjectFactory.create(PROJECT_NAME);
		new ProjectExplorer().selectProjects(PROJECT_NAME);
		CreateNewEmptyFile.genericFile(CAMEL_CONTEXT);
		DefaultEditor editor = new DefaultEditor(CAMEL_CONTEXT);
		editor.activate();
		EditorManipulator.copyFileContentToXMLEditor(RESOURCES_CONTEXT_PATH);
	}
	
	/**
	 * Clean environment after test.
	 * Removes created debug configurations and projects.
	 */
	@AfterClass
	public static void removeCreatedConfigurations() {	
		DebugConfigurationDialog.removeAllConfigurations();
		new CleanWorkspaceRequirement().fulfill();
	}
	
	/**
	 * Tests if Apache Camel Textual Debug is started properly.
	 */
	@Test
	public void testAttachingDebugger() {
		DebugConfigurationDialog.debug(CTD, DEBUG_CONF);
		new WaitUntil(new ConsoleHasText("\"command\":\"attach\",\"success\":true}"), TimePeriod.LONG);
		new DebugView().cancelLaunchingJob(DEBUG_CONF);
	}
}
