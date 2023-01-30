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

import java.util.List;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.button.CancelButton;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;

/**
 * Represents Default Editor preference page.
 * 
 * @author fpospisi
 *
 */
public class DefaultEditorPreferencePage extends PreferencePage {

	public DefaultEditorPreferencePage(ReferencedComposite ref) {
		super(ref, "General", "Editors", "File Associations");
	}

	/**
	 * Sets default editor for required file type.
	 *
	 * @param type   File type for change.
	 * @param editor Required editor.
	 */
	public void set(String type, String editor) {
		if (type == null || editor == null) {
			return;
		}

		// .foo and *.foo are equal in the end
		String filetype;
		if (type.substring(0, 1).equals(".")) {
			filetype = "*" + type;
		} else {
			filetype = type;
		}

		// if file type not available, add new file type
		if (!fileTypeIsAvailable(filetype)) {
			new PushButton("Add...").click();
			new FileTypeDialog().setTextFileType(filetype);
			new OkButton().click();
		}

		// if editor is available, set as default
		if (editorIsAvailable(editor)) {
			List<TableItem> availableEditors = new DefaultTable(1).getItems();
			for (TableItem availableEditor : availableEditors) {
				if (availableEditor.getText().startsWith(editor)) {
					availableEditor.select();
				}
			}
			new PushButton("Default").click();
		}

		// editor is not available in associated editors
		else {
			clickAddAssociatedEditorBTN();
			// select required editor from available ones
			DefaultTree tree = new DefaultTree();
			for (TreeItem item : tree.getAllItems()) {
				if (item.getText().startsWith(editor)) {
					item.select();
				}
			}
			// ok if editor exists, otherwise cancel
			if (new OkButton().isEnabled())
				new OkButton().click();
			else
				new CancelButton().click();

			new PushButton("Default").click();
		}
	}

	/**
	 * Checks if file type is already inside list.
	 * 
	 * @param filetype Required file type.
	 * @return true - file type is already in list
	 * @return false - otherwise
	 */
	public boolean fileTypeIsAvailable(String filetype) {
		List<TableItem> jreItems = new DefaultTable().getItems();
		for (TableItem jreItem : jreItems) {
			if (jreItem.getText().equals(filetype)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if editor is available in list.
	 * 
	 * @param editor Required editor.
	 * @return true - editor is available
	 * @return false - otherwise
	 */
	public boolean editorIsAvailable(String editor) {
		List<TableItem> availableEditors = new DefaultTable(1).getItems();
		for (TableItem availableEditor : availableEditors) {
			if (availableEditor.getText().startsWith(editor)) {
				return true;
			}
		}
		return false;
	}

	public void clickAddAssociatedEditorBTN() {
		new PushButton(2).click();
	}
}
