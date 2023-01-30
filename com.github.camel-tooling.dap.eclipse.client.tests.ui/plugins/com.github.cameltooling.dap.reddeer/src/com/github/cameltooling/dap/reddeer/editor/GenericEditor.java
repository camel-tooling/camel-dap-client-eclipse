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
package com.github.cameltooling.dap.reddeer.editor;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.eclipse.reddeer.workbench.impl.editor.DefaultEditor;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;

/**
 * Represents Generic Editor.
 * 
 * @author fpospisi
 */
public class GenericEditor extends DefaultEditor implements ReferencedComposite {
	
	public static final String GTE = "Generic Text Editor";

	private DefaultStyledText editor = new DefaultStyledText();

	/**
	 * Sets breakpoint on current line.
	 */
	public void setBreakpoint() {
		new ShellMenuItem(new WorkbenchShell(), "Run", "Toggle Breakpoint").select();
	}

	/**
	 * Clicks on Step Over button.
	 */
	public void stepOver() {
		new ShellMenuItem(new WorkbenchShell(), "Run", "Step Over").select();
	}

	/**
	 * Gets current position inside file.
	 * 
	 * @param text Searched text for position.
	 * @return Position of text.
	 */
	public int getPosition(String text) {
		return Integer.valueOf(editor.getPositionOfText(text));
	}

	/**
	 * Set position inside file.
	 * 
	 * @param text Searched text for position.
	 */
	public void setPosition(int text) {
		editor.selectPosition(text);
	}
}
