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
package com.github.cameltooling.dap.reddeer.debug;

import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.table.DefaultTableItem;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Debug view implementation.
 * 
 * @author fpospisi
 */
public class DebugView extends WorkbenchView {

	public DebugView() {
		super("Debug");
	}
	
	public void open() {
		new ShellMenuItem(new WorkbenchShell(), "Window", "Navigation", "Find Actions").select();
		Shell shell = new DefaultShell("Find Actions");
		new DefaultText(shell, 0).setText("Debug (Debug)");
		new DefaultTableItem().click();
	}
}
