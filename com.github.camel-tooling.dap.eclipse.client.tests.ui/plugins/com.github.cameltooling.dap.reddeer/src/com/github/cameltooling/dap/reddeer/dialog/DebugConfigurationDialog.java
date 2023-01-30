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
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.DebugConfigurationsDialog;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfiguration;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import com.github.cameltooling.dap.reddeer.launchconfigurations.CamelTextualDebugConfiguration;
import com.github.cameltooling.dap.reddeer.launchconfigurations.LaunchGroupConfiguration;
import com.github.cameltooling.dap.reddeer.launchconfigurations.MavenBuildLaunchConfiguration;

/**
 * Represents 'Debug Configuration' dialog. 
 * 
 * @author fpospisi
 */
public class DebugConfigurationDialog extends DebugConfigurationsDialog {

	private static final String DEBUG_CONF = "Debug Configurations";
	
	/**
	 * Start debug for given configuration. 
	 * 
	 * @param lc Kind of Launch Configuration. 
	 * @param name Name of configuration.
	 */
	public void debug(LaunchConfiguration lc, String name) {
		debugWithoutWait(lc, name);
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}
	
	/**
	 * Start debug for given configuration without waiting for running job. 
	 * 
	 * @param lc Kind of Launch Configuration. 
	 * @param name Name of configuration.
	 */
	public void debugWithoutWait(LaunchConfiguration lc, String name) {
		open();
		select(lc, name);
		String shellText = new DefaultShell().getText();
		Button button = new PushButton("Debug");
		button.click();
		new WaitWhile(new ShellIsAvailable(shellText), TimePeriod.LONG);
	}
	
	/**
	 * Check if given configuration exists.
	 * 
	 * @param lc Kind of Launch Configuration. 
	 * @param name Name of configuration.
	 * @return true if exists, otherwise false
	 */
	public boolean configurationExists(LaunchConfiguration lc, String name) {
		open();
		Shell shell = new DefaultShell(DEBUG_CONF);
		new DefaultTree(shell).getItem(lc.getType()).expand();
		List<TreeItem> items = new DefaultTree().getAllItems();
		for (TreeItem item : items) {
			System.out.println(item.getText());
			if (item.getText().equals(name)) {
				close();
				return true;
			}
		}
		close();
		return false;
	}
	
	/**
	 * Remove configuration with given name. 
	 * 
	 * @param lc Kind of Launch Configuration. 
	 * @param name Name of configuration.
	 */
	public void deleteConfiguration(LaunchConfiguration lc, String name) {
		open();
		delete(lc, name);
		close(true);	
	}
	
	// Maven	
	/**
	 * Creates 'Maven Build' configuration with given name and baseDirectory. 
	 * 
	 * @param name Name of created configuration. 
	 * @param baseDirectory Used base directory. 
	 */
	public void createMaven(String name, String baseDirectory) {
		open();
		create(new MavenBuildLaunchConfiguration(), name);
		Shell shell = new DefaultShell(DEBUG_CONF);
		new LabeledText(shell, "Name:").setText(name);
		new LabeledText(shell, "Base directory:").setText(baseDirectory);
		new LabeledText(shell, "Goals:").setText("clean compile camel:run");
		new LabeledText(shell, "Profiles:").setText("camel.debug");
		new CheckBox(shell, "Skip Tests").click();
		close(true);
	}
	
	/**
	 * Check if 'Maven Build' configuration with given name exists.
	 * 
	 * @param name Name of configuration.
	 * @return true if exists, otherwise false
	 */
	public boolean mavenExists(String name) {
		return configurationExists(new MavenBuildLaunchConfiguration(), name);
	}
	
	/**
	 * Remove 'Maven Build' with given name. 
	 * 
	 * @param name Name of removed configuration. 
	 */
	public void deleteMaven(String name) {
		deleteConfiguration(new MavenBuildLaunchConfiguration(), name);
	}
	
	/**
	 * Debug 'Maven Build' with given name.
	 * 
	 * @param name Name of 'Maven Build'. 
	 * @param wait Wait until Job start running.  
	 */
	public void debugMaven(String name, boolean wait) {
		if(wait) debug(new MavenBuildLaunchConfiguration(), name);
		else debugWithoutWait(new MavenBuildLaunchConfiguration(), name);
	}
	
	// Camel Textual Debug
	/**
	 * Creates 'Camel Textual Debug' configuration with given name and baseDirectory. 
	 * 
	 * @param name Name of created configuration. 
	 */
	public void createCTD(String name) {
		open();
		create(new CamelTextualDebugConfiguration(), name);
		close(true);
	}
	
	/**
	 * Check if 'Camel Textual Debug' configuration with given name exists.
	 * 
	 * @param name Name of configuration.
	 * @return true if exists, otherwise false
	 */
	public boolean CTDExists(String name) {
		return configurationExists(new CamelTextualDebugConfiguration(), name);
	}
	
	/**
	 * Remove 'Camel Textual Debug' with given name. 
	 * 
	 * @param name Name of removed configuration. 
	 */
	public void deleteCTD(String name) {
		deleteConfiguration(new CamelTextualDebugConfiguration(), name);
	}
	
	/**
	 * Debug 'Camel Textual Debug' with given name.
	 * 
	 * @param name Name of 'Camel Textual Debug'. 
	 * @param wait Wait until Job start running.  
	 */
	public void debugCTD(String name, boolean wait) {
		if(wait) debug(new CamelTextualDebugConfiguration(), name);
		else debugWithoutWait(new CamelTextualDebugConfiguration(), name);
	}
	
	// Launch Group	
	/**
	 * Creates 'Launch Group' configuration for existing 'Maven Build' and 'Camel Textual Debug' configurations. 
	 * 
	 * @param name Name of created configuration. 
	 * @param mvnConfName Name of existing 'Maven Build' configuration. 
	 * @param ctdConfName Name of existing 'Camel Textual Debug' configuration. 
	 */
	public void createLaunchGroup(String name, String mvnConfName, String ctdConfName) {
		open();
		create(new LaunchGroupConfiguration(), name);
		Shell shell = new DefaultShell(DEBUG_CONF);
		new WaitUntil(new ShellIsAvailable(shell), TimePeriod.DEFAULT);
		
		// Maven
		new PushButton(shell, "Add...").click();
		new WaitUntil(new ShellIsAvailable("Add Launch Configuration"), TimePeriod.DEFAULT);
		
		String[] mvnConfAbsPath = { "Maven Build", mvnConfName };
		new DefaultTreeItem(mvnConfAbsPath).select();
		new LabeledCombo("Post launch action:").setSelection(3);
		new LabeledText("Regular Expression:").setText(".*JMX Connector thread started and listening at.*");
		new OkButton().click();
		
		// Camel Textual Debug
		new WaitUntil(new ShellIsAvailable(shell), TimePeriod.DEFAULT);
		new PushButton(shell, "Add...").click();
		new WaitUntil(new ShellIsAvailable("Add Launch Configuration"), TimePeriod.DEFAULT);

		String[] ctdConfAbsPath = { "Camel Textual Debug", ctdConfName };
		new DefaultTreeItem(ctdConfAbsPath).select();
		new CheckBox("Adopt launch if already running").click();
		new OkButton().click();

		new WaitUntil(new ShellIsAvailable(shell), TimePeriod.DEFAULT);		
		close(true);	
	}
	
	/**
	 * Check if 'Launch Group' configuration with given name exists.
	 * @param name Name of configuration.
	 * @return true if exists, otherwise false
	 */
	public boolean launchGroupExists(String name) {
		return configurationExists(new LaunchGroupConfiguration(), name);
	}

	/**
	 * Remove 'Launch Group' with given name. 
	 * @param name Name of removed configuration. 
	 */
	public void deleteLaunchGroup(String name) {
		deleteConfiguration(new LaunchGroupConfiguration(), name);
	}
	
	/**
	 * Debug 'Launch Group' with given name.
	 * 
	 * @param name Name of 'Launch Group'. 
	 * @param wait Wait until Job start running.  
	 */
	public void debugLaunchGroup(String name, boolean wait) {
		if(wait) debug(new LaunchGroupConfiguration(), name);
		else debugWithoutWait(new LaunchGroupConfiguration(), name);
	}
}
