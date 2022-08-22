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
package com.github.cameltooling.dap.reddeer.utils;

import com.github.cameltooling.dap.reddeer.wizard.NewGenericFileDialog;
import com.github.cameltooling.dap.reddeer.wizard.NewGenericFileWizard;
import com.github.cameltooling.dap.reddeer.wizard.NewJavaClassFirstPage;
import com.github.cameltooling.dap.reddeer.wizard.NewJavaClassWizard;

/**
 * Creates new empty file.
 *
 * @author fpospisi
 */
public class CreateNewEmptyFile {

	/**
	 * Creates new generic file.
	 *
	 * @param filename Name of file.
	 */
	public static void genericFile(String filename) {
		NewGenericFileWizard newGenFile = new NewGenericFileWizard();
		newGenFile.open();
		NewGenericFileDialog newGenFileDialog = new NewGenericFileDialog(newGenFile);
		newGenFileDialog.setFileName(filename);
		newGenFile.finish();
	}

	/**
	 * Creates new empty Java class.
	 *
	 * @param filename Name of class.
	 */
	public static void JavaClass(String filename) {
		NewJavaClassWizard newWizJavaClass = new NewJavaClassWizard();

		newWizJavaClass.open();
		NewJavaClassFirstPage newWizJavaClassPage = new NewJavaClassFirstPage(newWizJavaClass);
		newWizJavaClassPage.setClassName(filename);

		newWizJavaClass.finish();
	}
}
