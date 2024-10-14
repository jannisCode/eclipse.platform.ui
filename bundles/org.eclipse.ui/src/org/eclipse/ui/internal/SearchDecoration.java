/*******************************************************************************
 * Copyright (c) 2024 Vector Informatik GmbH and others.
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

package org.eclipse.ui.internal;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * This class contains methods to validate and decorate search fields.
 */
public class SearchDecoration {

	private SearchDecoration() {
		// avoid instantiation
	}

	/**
	 * Validate the given regular expression and change the control decoration
	 * accordingly. If the expression is invalid then the decoration will show an
	 * error icon and a message and if the expression is valid then the decoration
	 * will be hidden.
	 *
	 * @param regex            The regular expression to be validated.
	 * @param targetDecoration The control decoration that will show the result of
	 *                         the validation.
	 */
	public static boolean validateRegex(String regex, ControlDecoration targetDecoration) {
		String errorMessage = getValidationError(regex);
		if (errorMessage.isEmpty()) {
			targetDecoration.hide();
			return true;

		}

		Image decorationImage = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage();
		targetDecoration.setImage(decorationImage);
		targetDecoration.setDescriptionText(errorMessage);
		targetDecoration.show();
		return false;
	}

	/**
	 * Validate a regular expression.
	 *
	 * @return The appropriate error message if the regex is invalid or an empty
	 *         string if the regex is valid.
	 */
	private static String getValidationError(String regex) {
		try {
			Pattern.compile(regex);
			return ""; //$NON-NLS-1$
		} catch (PatternSyntaxException e) {
			String message = e.getLocalizedMessage();
			StringBuilder sBuilder = new StringBuilder();

			// Only preserve the first line of the original error message.
			int i = 0;
			while (i < message.length() && "\n\r".indexOf(message.charAt(i)) == -1) { //$NON-NLS-1$
				i++;
			}
			int j = i + 2;
			while (j < message.length() && "\n\r".indexOf(message.charAt(j)) == -1) { //$NON-NLS-1$
				j++;
			}
			int number;

			try {
				number = Integer.parseInt(message.substring(i - 2, i));
			} catch (Exception e1) {
				number = Integer.parseInt(message.substring(i - 1, i));
			}
			sBuilder.append(message.substring(0, i));

			sBuilder.append(System.lineSeparator());
			String message_two = message.substring(i + 2, j);
			String arrowString = " <-- "; //$NON-NLS-1$
			String m3 = message_two.substring(0, number + 1);
			String m4 = ""; //$NON-NLS-1$
			if (message_two.length() > number) {
				m4 = message_two.substring(number + 1, message_two.length());

			}

			sBuilder.append(m3 + arrowString + m4);

//			sBuilder.append(System.lineSeparator());
//
//			for (int x = 0; x < message_two.length(); x++) {
//				if (x % 3 == 0) {
//					sBuilder.append(" "); //$NON-NLS-1$
//				} else {
//					sBuilder.append("  "); //$NON-NLS-1$
//				}
//			}

//			sBuilder.append("^"); //$NON-NLS-1$
			return sBuilder.toString();
		}
	}

}