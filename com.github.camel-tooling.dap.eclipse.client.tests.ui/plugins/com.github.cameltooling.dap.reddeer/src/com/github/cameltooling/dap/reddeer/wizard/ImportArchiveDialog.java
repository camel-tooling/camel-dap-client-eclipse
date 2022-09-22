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
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Wizard dialog page for importing archive.
 * 
 * @author fpospisi
 */
public class ImportArchiveDialog extends WizardPage {

	public static final String FROM_ARCHIVE = "From archive file:";
	public static final String INTO_FODLER = "Into folder:";
	public static final String RESOURCES_TEXT = "Overwrite existing resources without warning";

	public ImportArchiveDialog(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	public void archiveFileSource(String str) {
		new LabeledCombo(this, FROM_ARCHIVE).setText(str);
	}

	public void inoFolder(String str) {
		new LabeledText(this, INTO_FODLER).setText(str);
	}

	public void overwriteResource() {
		new CheckBox(this, RESOURCES_TEXT).click();
	}
}
