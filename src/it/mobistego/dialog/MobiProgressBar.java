/**
 * Copyright (C) 2009  Pasquale Paola

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package it.mobistego.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 *
 */
public class MobiProgressBar extends ProgressDialog {

	/**
	 * @param context
	 */
	public MobiProgressBar(Context context) {
		super(context);
		setProgressStyle(STYLE_HORIZONTAL);
	}

}
