/*******************************************************************************
 * Copyright (c) 2023 Vector Informatik GmbH and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Vector Informatik GmbH - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.internal.findandreplace;


import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.swt.graphics.Image;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;

/**
 *
 */
public class SearchDecoration {
	String message;

	public void decorateA(ControlDecoration decoration, String regex) {

		if (!isValidRegex(regex)) {
			Image decorationImage = FieldDecorationRegistry.getDefault()
					.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage();
			decoration.setImage(decorationImage);
			decoration.setDescriptionText(message); // $NON-NLS-1$
			decoration.show();
		} else
			decoration.hide();
	}


	private boolean isValidRegex(String string) {
		try {
			Pattern compile = Pattern.compile(string);
			message = ""; //$NON-NLS-1$
			return true;
		} catch (PatternSyntaxException e) {
			message = e.getLocalizedMessage();

			int i = 0;
			while (i < message.length() && "\n\r".indexOf(message.charAt(i)) == -1) { //$NON-NLS-1$
				i++;
			}

			message = e.getLocalizedMessage().substring(0, i);
			return false;
		}
	}

}