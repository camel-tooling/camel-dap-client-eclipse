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

import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfiguration;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.RunConfigurationsDialog;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

import com.github.cameltooling.dap.reddeer.launchconfigurations.MavenBuildLaunchConfiguration;

/**
 * Represents 'Run Configuration' dialog. 
 * 
 * @author fpospisi
 */
public class RunConfigurationDialog extends RunConfigurationsDialog {
	
	private static final String RUN_CONF = "Run Configurations";
	
	/**
	 * Start run for given configuration. 
	 * 
	 * @param lc Kind of Launch Configuration. 
	 * @param name Name of configuration.
	 */
	public void run(LaunchConfiguration lc, String name) {
		open();
		select(lc, name);
		run();
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
		Shell shell = new DefaultShell(RUN_CONF);
		new LabeledText(shell, "Name:").setText(name);
		new LabeledText(shell, "Base directory:").setText(baseDirectory);
		new LabeledText(shell, "Goals:").setText("clean compile camel:run");
		new LabeledText(shell, "Profiles:").setText("camel.debug");
		new CheckBox(shell, "Skip Tests").click();
		close(true);
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
	 * Run 'Maven Build' with given name. 
	 * 
	 * @param name Name of 'Maven Build'. 
	 */
	public void runMaven(String name) {
		open();
		select(new MavenBuildLaunchConfiguration(), name);
		run();
	}
}
