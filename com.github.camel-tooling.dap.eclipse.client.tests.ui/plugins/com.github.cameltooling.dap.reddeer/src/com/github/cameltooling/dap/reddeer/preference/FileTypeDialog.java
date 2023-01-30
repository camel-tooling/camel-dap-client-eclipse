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
package com.github.cameltooling.dap.reddeer.preference;

import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;

/**
 * Represents File Type dialog.
 * 
 * @author fpospisi
 *
 */
public class FileTypeDialog {

	public static final String FILE_TYPE = "File type:";
	public static final String ADD_FILE_TYPE = "Add File Type";

	/**
	 * Gets label of Labeled Text.
	 * 
	 * @return Label representation of Labeled Text.
	 */
	public LabeledText getFileType() {
		return new LabeledText(FILE_TYPE);
	}

	/**
	 * Gets string of Labeled Text.
	 * 
	 * @return String representation of Labeled Text.
	 */
	public String getTextFileType() {
		return new LabeledText(FILE_TYPE).getText();
	}

	/**
	 * Gets Add File Type shell.
	 * 
	 * @return Add File Type shell.
	 */
	public DefaultShell getShellAddFileType() {
		return new DefaultShell(ADD_FILE_TYPE);
	}

	/**
	 * Gets text of Add File Type shell.
	 * 
	 * @return String representation of Add File Type shell.
	 */
	public String getTextAddFileType() {
		return new DefaultShell(ADD_FILE_TYPE).getText();
	}

	/**
	 * Sets file type.
	 * 
	 * @param Required file type.
	 */
	public void setTextFileType(String filetype) {
		new LabeledText(FILE_TYPE).setText(filetype);
	}
}
