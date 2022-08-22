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
package com.github.cameltooling.dap.reddeer.dialog;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;

/**
 * 
 * @author fpospisi
 */
public class DebugConfigurationDialog {

	private static final String DEBUG_CONF = "Debug Configurations";
	private static final String CAMEL_TEXT_DEBUG = "Camel Textual Debug";
	private static final String DEBUG_BTN = "Debug";

	/**
	 * Creates Apache Camel Debug Configuration with specified name.
	 *
	 * @param name for creating new configuration
	 */
	public static void create(String name) {
		new ShellMenuItem(new WorkbenchShell(), "Run", "Debug Configurations...").select();
		new WaitUntil(new ShellIsAvailable(DEBUG_CONF), TimePeriod.DEFAULT);
		Shell shell = new DefaultShell(DEBUG_CONF);
		new DefaultTree(shell).getItem(CAMEL_TEXT_DEBUG).doubleClick();
		new LabeledText(shell, "Name:").setText(name);
		new PushButton("Apply").click();
		new PushButton("Close").click();
	}

	/**
	 * Run debugger.
	 *
	 * @param debugConfiguration used for debug
	 */
	public static void debug(String debugConfiguration) {
		new ShellMenuItem(new WorkbenchShell(), "Run", "Debug Configurations...").select();
		new WaitUntil(new ShellIsAvailable(DEBUG_CONF), TimePeriod.DEFAULT);
		Shell shell = new DefaultShell(DEBUG_CONF);
		new DefaultTree(shell).getItem(CAMEL_TEXT_DEBUG, debugConfiguration).select();
		new PushButton(DEBUG_BTN).click();
	}

	/**
	 * Run debugger for specified file in specified project.
	 *
	 * @param project where required file is located
	 * @param file to debug
	 * @param debugConfiguration used for debug
	 */
	public static void debugFile(String project, String file, String debugConfiguration) {
		DefaultProject proj = new ProjectExplorer().getProject(project);
		proj.getProjectItem(file).select();
		new ContextMenuItem("Debug As", "Debug Configurations...").select();
		new WaitUntil(new ShellIsAvailable(DEBUG_CONF), TimePeriod.DEFAULT);
		Shell shell = new DefaultShell(DEBUG_CONF);
		new DefaultTree(shell).getItem(CAMEL_TEXT_DEBUG, debugConfiguration).select();
		new PushButton(DEBUG_BTN).click();
	}
}
