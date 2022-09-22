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

import java.util.List;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolBar;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;

/**
 * 
 * @author fpospisi
 */
public class DebugConfigurationDialog {

	private static final String DEBUG_CONF = "Debug Configurations";

	private static final String CAMEL_TEXT_DEBUG = "Camel Textual Debug";
	private static final String MVN_DEBUG = "Maven Build";
	private static final String GROUPED_DEBUG = "Launch Group";

	private static final String DEBUG_BTN = "Debug";

	/**
	 * Creates Camel Textual Debug debug configuration.
	 * 
	 * @param name of created configuration.
	 */
	public static void createCTD(String name) {
		openDebugConfigurations();
		createNewConfiguration(CAMEL_TEXT_DEBUG);

		// set values
		new LabeledText(new DefaultShell(DEBUG_CONF), "Name:").setText(name);

		saveChangesAndExit();
	}

	/**
	 * Creates Maven debug configuration.
	 * 
	 * @param name of created configuration.
	 * @param baseDirectory for configuration.
	 */
	public static void createMaven(String name, String baseDirectory) {
		openDebugConfigurations();
		createNewConfiguration(MVN_DEBUG);

		// set values
		Shell shell = new DefaultShell(DEBUG_CONF);
		new LabeledText(shell, "Name:").setText(name);
		new LabeledText(shell, "Base directory:").setText(baseDirectory);
		new LabeledText(shell, "Goals:").setText("compile camel:run");
		new LabeledText(shell, "Profiles:").setText("camel.debug");
		new CheckBox(shell, "Skip Tests").click();

		saveChangesAndExit();
	}

	/**
	 * Creates Launch Group debug configuration. Maven and CTD configuration must be
	 * created before.
	 * 
	 * @param name of created configuration.
	 * @param mvnConfName name of Maven configuration.
	 * @param ctdConfName name of Camel Textual Debug configuration.
	 */
	public static void createLaunchGroup(String name, String mvnConfName, String ctdConfName) {
		openDebugConfigurations();
		createNewConfiguration(GROUPED_DEBUG);
		
		Shell shell = new DefaultShell(DEBUG_CONF);
		new LabeledText(shell, "Name:").setText(name);

		// MAVEN
		new WaitUntil(new ShellIsAvailable(shell), TimePeriod.DEFAULT);
		new PushButton(shell, "Add...").click();
		new WaitUntil(new ShellIsAvailable("Add Launch Configuration"), TimePeriod.DEFAULT);

		String[] mvnConfAbsPath = { MVN_DEBUG, mvnConfName };
		new DefaultTreeItem(mvnConfAbsPath).select();
		new LabeledCombo("Post launch action:").setSelection(3);
		new LabeledText("Regular Expression:").setText(".*JMX Connector thread started and listening at.*");
		new OkButton().click();

		// CTD
		new WaitUntil(new ShellIsAvailable(shell), TimePeriod.DEFAULT);
		new PushButton(shell, "Add...").click();
		new WaitUntil(new ShellIsAvailable("Add Launch Configuration"), TimePeriod.DEFAULT);

		String[] ctdConfAbsPath = { CAMEL_TEXT_DEBUG, ctdConfName };
		new DefaultTreeItem(ctdConfAbsPath).select();
		new CheckBox("Adopt launch if already running").click();
		new OkButton().click();

		new WaitUntil(new ShellIsAvailable(shell), TimePeriod.DEFAULT);
		saveChangesAndExit();
	}

	/**
	 * Run debugger.
	 *
	 * @param configurationType used for debug.
	 * @param debugConfiguration used for debug.
	 */
	public static void debug(String configurationType, String debugConfiguration) {
		openDebugConfigurations();

		new DefaultTree(new DefaultShell(DEBUG_CONF)).getItem(configurationType, debugConfiguration).select();
		new PushButton(DEBUG_BTN).click();
	}

	/**
	 * Run debugger for specified file in specified project.
	 *
	 * @param project where required file is located.
	 * @param file to debug.
	 * @param debugConfiguration used for debug.
	 */
	public static void debugFile(String project, String file, String debugConfiguration) {
		DefaultProject proj = new ProjectExplorer().getProject(project);
		proj.getProjectItem(file).select();

		openDebugConfigurations();

		Shell shell = new DefaultShell(DEBUG_CONF);

		new DefaultTree(shell).getItem(CAMEL_TEXT_DEBUG, debugConfiguration).select();
		new PushButton(DEBUG_BTN).click();
	}

	/**
	 * Checks if required configuration is existing.
	 * 
	 * @param configurationType Type of configuration.
	 * @param configurationName Name of configuration.
	 * @return true If exists.
	 * @return false If not exists.
	 */
	public static boolean configurationExists(String configurationType, String configurationName) {
		openDebugConfigurations();

		Shell shell = new DefaultShell(DEBUG_CONF);

		new DefaultTree(shell).getItem(configurationType).expand();
		
		List<TreeItem> items = new DefaultTree().getAllItems();
		for (TreeItem item : items) {
			System.out.println(item.getText());
			if (item.getText().equals(configurationName)) {
				new PushButton("Close").click();
				return true;
			}
		}
		
		new PushButton("Close").click();
		return false;
	}

	/**
	 * Removes selected debug configuration.
	 * 
	 * @param configurationType Type of configuration.
	 * @param configurationName Name of configuration.
	 */
	public static void removeConfiguration(String configurationType, String configurationName) {
		openDebugConfigurations();
		Shell shell = new DefaultShell(DEBUG_CONF);

		new DefaultTree(shell).getItem(configurationType).expand();

		List<TreeItem> items = new DefaultTree().getAllItems();
		for (TreeItem item : items) {
			if (item.getText().equals(configurationName)) {
				item.select();
				deleteSelectedConfiguration();
			}
		}

		new PushButton("Close").click();
	}

	/**
	 * Removes all existing debug configurations.
	 */
	public static void removeAllConfigurations() {
		openDebugConfigurations();

		List<TreeItem> items = new DefaultTree().getAllItems();
		for (TreeItem item : items) {		
			item.select();
			
			DefaultToolBar toolbar = new DefaultToolBar(0);
			if (new DefaultToolItem(toolbar, 4).isEnabled()) { // checks if it's created configuration
				deleteSelectedConfiguration();
			}
		}
		
		new PushButton("Close").click();
	}

	/**
	 * Opens Debug Configuration wizard.
	 */
	private static void openDebugConfigurations() {
		new ShellMenuItem(new WorkbenchShell(), "Run", "Debug Configurations...").select();
		new WaitUntil(new ShellIsAvailable(DEBUG_CONF), TimePeriod.DEFAULT);
	}
	
	/**
	 * Removes debug configuration which is currently selected.
	 */
	private static void deleteSelectedConfiguration() {
		DefaultToolBar toolbar = new DefaultToolBar(0);
		new DefaultToolItem(toolbar, 4).click();
		new PushButton("Delete").click();
	}
	
	/**
	 * Creates new configuration for selected type.
	 * 
	 * @param configurationType Type of required configuration.
	 */
	private static void createNewConfiguration(String configurationType) {
		new DefaultTree(new DefaultShell(DEBUG_CONF)).getItem(configurationType).doubleClick();
	}
	
	/*
	 * Saves currently created changes and exits. 
	 */
	private static void saveChangesAndExit() {
		new PushButton("Apply").click();
		new PushButton("Close").click();
	}
}
