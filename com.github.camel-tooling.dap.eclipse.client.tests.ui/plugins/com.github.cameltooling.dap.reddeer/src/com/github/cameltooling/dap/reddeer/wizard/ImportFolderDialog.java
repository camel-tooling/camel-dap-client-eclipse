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
package com.github.cameltooling.dap.reddeer.wizard;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;

/**
 * Wizard dialog page for importing folder.
 * 
 * @author fpospisi
 */
public class ImportFolderDialog extends WizardPage {

	public ImportFolderDialog(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	public void setImportSource(String source) {
		new LabeledCombo(this, "Import source:").setText(source);
	}
	
	public void closeNewlyImportedProjects() {
		new CheckBox(this, "Close newly imported projects upon completion").click();
	}

	public void searchForNestedProjects() {
		new CheckBox(this, "Search for nested projects").click();
	}

	public void detectAndConfigureNatures() {
		new CheckBox(this, "Detect and configure project natures").click();
	}
}
