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


import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;

/**
 *
 */
public class SearchDecoration {

	public void decorateA(Composite control, ControlDecoration decoration, boolean endsWithA) {

		if (!endsWithA) {
			Image decorationImage = FieldDecorationRegistry.getDefault()
					.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage();
			decoration.setImage(decorationImage);
			decoration.setDescriptionText("I don't like strings that don't end with 'a'"); //$NON-NLS-1$
			decoration.show();
		} else
			decoration.hide();
	}
}