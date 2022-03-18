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

import java.util.Arrays;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.lsp4e.debug.DSPPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CamelDebugLaunchConfigurationTab extends AbstractLaunchConfigurationTab {

	private static final String DEFAULT_JSON_LAUNCH_ARGS = "{\n"
			+ "\"request\": \"attach\"\n"
			+ "}";
	private Text startArgumentsText;

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(DSPPlugin.ATTR_DSP_PARAM, DEFAULT_JSON_LAUNCH_ARGS);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			startArgumentsText.setText(configuration.getAttribute(DSPPlugin.ATTR_DSP_PARAM, DEFAULT_JSON_LAUNCH_ARGS));
		} catch (CoreException e) {
			CamelDAPClientActivator.getInstance().getLog().log(new Status(IStatus.ERROR, CamelDAPClientActivator.ID,
					"Cannot initialize from launch configuration.", e)); //$NON-NLS-1$
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(DSPPlugin.ATTR_CUSTOM_LAUNCH_PARAMS, false);
		configuration.setAttribute(DSPPlugin.ATTR_CUSTOM_DEBUG_ADAPTER, false);
		configuration.setAttribute(DSPPlugin.ATTR_DSP_ARGS, Arrays.asList("-jar", CamelDAPJarAcccessor.computeCamelDebugAdapterServerJarPath()));
		configuration.setAttribute(DSPPlugin.ATTR_DSP_CMD, "java");
		configuration.setAttribute(DSPPlugin.ATTR_DSP_MODE, DSPPlugin.DSP_MODE_LAUNCH);
		configuration.setAttribute(DSPPlugin.ATTR_DSP_MONITOR_DEBUG_ADAPTER, true);
		configuration.setAttribute(DSPPlugin.ATTR_DSP_PARAM, startArgumentsText.getText());
	}

	@Override
	public String getName() {
		return "Main";
	}

	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		comp.setLayout(new GridLayout(1, true));
		comp.setFont(parent.getFont());
		
		Label description = new Label(comp, SWT.NONE);
		description.setText("Arguments provided when initializing the Camel Debug Adapter. It is a JSON string. Additional possible parameters are attach_jmx_url and attach_pid.");
		description.setLayoutData(GridDataFactory.fillDefaults().create());
			
		startArgumentsText = new Text(comp, SWT.BORDER | SWT.MULTI);
		startArgumentsText.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());
		startArgumentsText.setText(DEFAULT_JSON_LAUNCH_ARGS);
		startArgumentsText.addModifyListener(e -> updateLaunchConfigurationDialog());
	}

}
