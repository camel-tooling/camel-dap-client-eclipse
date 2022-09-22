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

import org.eclipse.reddeer.swt.impl.button.FinishButton;

import com.github.cameltooling.dap.reddeer.wizard.ImportArchiveDialog;
import com.github.cameltooling.dap.reddeer.wizard.ImportArchiveWizard;
import com.github.cameltooling.dap.reddeer.wizard.ImportFolderDialog;
import com.github.cameltooling.dap.reddeer.wizard.ImportFolderWizard;

/**
 * Imports required kind of input into workspace.
 *
 * @author fpospisi
 */
public class ImportIntoWorkspace {

	/**
	 * Imports archive into workspace.
	 * 
	 * @param sourceArchive Source of imported archive. 
	 * @param archiveDestination Destination folder in workspace.
	 * @param overwrite Overwrite existing resources without warning - true/false. 
	 */
	public static void importArchive(String sourceArchive, String archiveDestination, Boolean overwrite) {

		ImportArchiveWizard archiveWizard = new ImportArchiveWizard();
		archiveWizard.open();
		ImportArchiveDialog archiveDialog = new ImportArchiveDialog(archiveWizard);

		archiveDialog.archiveFileSource(sourceArchive);
		archiveDialog.inoFolder(archiveDestination);
		if (overwrite)
			archiveDialog.overwriteResource();

		new FinishButton().click();
	}
	
	/**
	 * Imports folder into workspace.
	 * 
	 * @param sourceFolder Source of imported folder. 
	 */
	public static void importFolder(String sourceFolder) {
		
		ImportFolderWizard folderWizard = new ImportFolderWizard();
		folderWizard.open();
		ImportFolderDialog folderDialog = new ImportFolderDialog(folderWizard);
		
		folderDialog.setImportSource(sourceFolder);

		new FinishButton().click();
	}
}
