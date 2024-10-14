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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * This class contains methods to validate and decorate search fields.
 */
public class SearchDecoration {
	private static Map<String, Double> characterWidths = new HashMap<>();

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
			createMap();
			String message = e.getLocalizedMessage();
			StringBuilder sBuilder = new StringBuilder();

			int i = 0;
			while (i < message.length() && "\n\r".indexOf(message.charAt(i)) == -1) { //$NON-NLS-1$
				i++;
			}
			int j = i + 2;
			while (j < message.length() && "\n\r".indexOf(message.charAt(j)) == -1) { //$NON-NLS-1$
				j++;
			}

			String firstLine = message.substring(0, i);

			String indexString = firstLine.replaceAll("\\D+", ""); //$NON-NLS-1$ //$NON-NLS-2$
			int errorIndex = Integer.parseInt(indexString);

			sBuilder.append(firstLine);
			sBuilder.append(System.lineSeparator());

			String secondLine = message.substring(i + 2, j); // the +2 because of the \n\r
			sBuilder.append(secondLine);
			sBuilder.append(System.lineSeparator());

			double sum = 0d;
			String subsString = secondLine.substring(0, errorIndex);

			for (char c : subsString.toCharArray()) {
				String cString = Character.toString(c);
				sum += getCharacterWidth(cString) + 1; // the +1 is important, i found out through trial and error that
														// it works best
			}

			double whitespace = 0d;
			while (whitespace < sum) {
				sBuilder.append(" "); //$NON-NLS-1$
				whitespace += getCharacterWidth(" "); //$NON-NLS-1$
			}

			sBuilder.append("^"); //$NON-NLS-1$
			return sBuilder.toString();
		}
	}

	private static double getCharacterWidth(String s) {
		try {
			return characterWidths.get(s);
		} catch (Exception e) {
			return 8;
		}
	}
	private static void createMap() {
		characterWidths.put(" ", 4.4453125); //$NON-NLS-1$
		characterWidths.put("!", 4.4453125); //$NON-NLS-1$
		characterWidths.put("\"", 5.6796875); //$NON-NLS-1$
		characterWidths.put("#", 8.8984375); //$NON-NLS-1$
		characterWidths.put("$", 8.8984375); //$NON-NLS-1$
		characterWidths.put("%", 14.2265625); //$NON-NLS-1$
		characterWidths.put("&", 10.671875); //$NON-NLS-1$
		characterWidths.put("'", 3.0546875); //$NON-NLS-1$
		characterWidths.put("(", 5.328125); //$NON-NLS-1$
		characterWidths.put(")", 5.328125); //$NON-NLS-1$
		characterWidths.put("*", 6.2265625); //$NON-NLS-1$
		characterWidths.put("+", 9.34375); //$NON-NLS-1$
		characterWidths.put(",", 4.4453125); //$NON-NLS-1$
		characterWidths.put("-", 5.328125); //$NON-NLS-1$
		characterWidths.put(".", 4.4453125); //$NON-NLS-1$
		characterWidths.put("/", 4.4453125); //$NON-NLS-1$
		characterWidths.put("0", 8.8984375); //$NON-NLS-1$
		characterWidths.put("1", 7.7228125); //$NON-NLS-1$
		characterWidths.put("2", 8.8984375); //$NON-NLS-1$
		characterWidths.put("3", 8.8984375); //$NON-NLS-1$
		characterWidths.put("4", 8.8984375); //$NON-NLS-1$
		characterWidths.put("5", 8.8984375); //$NON-NLS-1$
		characterWidths.put("6", 8.8984375); //$NON-NLS-1$
		characterWidths.put("7", 8.8984375); //$NON-NLS-1$
		characterWidths.put("8", 8.8984375); //$NON-NLS-1$
		characterWidths.put("9", 8.8984375); //$NON-NLS-1$
		characterWidths.put(":", 4.4453125); //$NON-NLS-1$
		characterWidths.put(";", 4.4453125); //$NON-NLS-1$
		characterWidths.put("<", 9.34375); //$NON-NLS-1$
		characterWidths.put("=", 9.34375); //$NON-NLS-1$
		characterWidths.put(">", 9.34375); //$NON-NLS-1$
		characterWidths.put("?", 8.8984375); //$NON-NLS-1$
		characterWidths.put("@", 16.2421875); //$NON-NLS-1$
		characterWidths.put("A", 10.671875); //$NON-NLS-1$
		characterWidths.put("B", 10.671875); //$NON-NLS-1$
		characterWidths.put("C", 11.5546875); //$NON-NLS-1$
		characterWidths.put("D", 11.5546875); //$NON-NLS-1$
		characterWidths.put("E", 10.671875); //$NON-NLS-1$
		characterWidths.put("F", 9.7734375); //$NON-NLS-1$
		characterWidths.put("G", 12.4453125); //$NON-NLS-1$
		characterWidths.put("H", 11.5546875); //$NON-NLS-1$
		characterWidths.put("I", 4.4453125); //$NON-NLS-1$
		characterWidths.put("J", 8.0); //$NON-NLS-1$
		characterWidths.put("K", 10.671875); //$NON-NLS-1$
		characterWidths.put("L", 8.8984375); //$NON-NLS-1$
		characterWidths.put("M", 13.328125); //$NON-NLS-1$
		characterWidths.put("N", 11.5546875); //$NON-NLS-1$
		characterWidths.put("O", 12.4453125); //$NON-NLS-1$
		characterWidths.put("P", 10.671875); //$NON-NLS-1$
		characterWidths.put("Q", 12.4453125); //$NON-NLS-1$
		characterWidths.put("R", 11.5546875); //$NON-NLS-1$
		characterWidths.put("S", 10.671875); //$NON-NLS-1$
		characterWidths.put("T", 9.7734375); //$NON-NLS-1$
		characterWidths.put("U", 11.5546875); //$NON-NLS-1$
		characterWidths.put("V", 10.671875); //$NON-NLS-1$
		characterWidths.put("W", 15.1015625); //$NON-NLS-1$
		characterWidths.put("X", 10.671875); //$NON-NLS-1$
		characterWidths.put("Y", 10.671875); //$NON-NLS-1$
		characterWidths.put("Z", 9.7734375); //$NON-NLS-1$
		characterWidths.put("[", 4.4453125); //$NON-NLS-1$
		characterWidths.put("\\", 4.4453125); //$NON-NLS-1$
		characterWidths.put("]", 4.4453125); //$NON-NLS-1$
		characterWidths.put("^", 7.5078125); //$NON-NLS-1$
		characterWidths.put("_", 8.8984375); //$NON-NLS-1$
		characterWidths.put("`", 5.328125); //$NON-NLS-1$
		characterWidths.put("a", 8.8984375); //$NON-NLS-1$
		characterWidths.put("b", 8.8984375); //$NON-NLS-1$
		characterWidths.put("c", 8.0); //$NON-NLS-1$
		characterWidths.put("d", 8.8984375); //$NON-NLS-1$
		characterWidths.put("e", 8.8984375); //$NON-NLS-1$
		characterWidths.put("f", 4.15921875); //$NON-NLS-1$
		characterWidths.put("g", 8.8984375); //$NON-NLS-1$
		characterWidths.put("h", 8.8984375); //$NON-NLS-1$
		characterWidths.put("i", 3.5546875 + 0.2); //$NON-NLS-1$
		characterWidths.put("j", 3.5546875 + 0.2); //$NON-NLS-1$
		characterWidths.put("k", 8.0); //$NON-NLS-1$
		characterWidths.put("l", 3.5546875); //$NON-NLS-1$
		characterWidths.put("m", 13.328125); //$NON-NLS-1$
		characterWidths.put("n", 8.8984375); //$NON-NLS-1$
		characterWidths.put("o", 8.8984375); //$NON-NLS-1$
		characterWidths.put("p", 8.8984375); //$NON-NLS-1$
		characterWidths.put("q", 8.8984375); //$NON-NLS-1$
		characterWidths.put("r", 5.328125); //$NON-NLS-1$
		characterWidths.put("s", 8.0); //$NON-NLS-1$
		characterWidths.put("t", 4.4453125); //$NON-NLS-1$
		characterWidths.put("u", 8.8984375); //$NON-NLS-1$
		characterWidths.put("v", 8.0); //$NON-NLS-1$
		characterWidths.put("w", 11.5546875); //$NON-NLS-1$
		characterWidths.put("x", 8.0); //$NON-NLS-1$
		characterWidths.put("y", 8.0); //$NON-NLS-1$
		characterWidths.put("z", 8.0); //$NON-NLS-1$
		characterWidths.put("ä", 8.0); //$NON-NLS-1$
		characterWidths.put("ö", 8.0); //$NON-NLS-1$
		characterWidths.put("ü", 8.0); //$NON-NLS-1$
		characterWidths.put("{", 5.34375); //$NON-NLS-1$
		characterWidths.put("|", 4.15625); //$NON-NLS-1$
		characterWidths.put("}", 5.34375); //$NON-NLS-1$
		characterWidths.put("~", 9.34375); //$NON-NLS-1$
	}

}