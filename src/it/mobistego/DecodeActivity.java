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
package it.mobistego;

import it.mobistego.alg.LSB2bit;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

/**
 * 
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 * 
 */
public class DecodeActivity extends Activity implements Runnable {

	private Context context;
	private Handler handler;
	private ProgressDialog dd;
	private Uri photoUri;
	private final Runnable runnableDismmiss = new Runnable() {

		
		public void run() {
			dd.dismiss();

		}
	};

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.decoder);
		context = this;
		handler = new Handler();
		dd = new ProgressDialog(this);
		dd.setIndeterminate(true);
		dd.setMessage(context.getText(R.string.decoding));
		dd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dd.show();
		photoUri = getIntent().getData();
		Thread tt = new Thread(this, "Decoding Mobistego");
		tt.start();

	}

	
	public void run() {
		Bitmap image = null;
		try {
			Cursor cursor = getContentResolver().query(photoUri, null, null,
					null, null);
			cursor.moveToFirst();

			int idx = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			String absoluteFilePath = cursor.getString(idx);

			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inDither = false;
			opt.inScaled = false;
			opt.inDensity = 0;
			opt.inJustDecodeBounds = false;
			opt.inPurgeable = false;
			opt.inSampleSize = 1;
			opt.inScreenDensity = 0;
			opt.inTargetDensity = 0;
			image = BitmapFactory.decodeFile(absoluteFilePath, opt);

		} catch (Exception e) {
			e.printStackTrace();
		}

		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getPixels(pixels, 0, image.getWidth(), 0, 0, image.getWidth(),
				image.getHeight());
		Log.v("Decode", "" + pixels[0]);
		Log.v("Decode Alpha", "" + (pixels[0] >> 24 & 0xFF));
		Log.v("Decode Red", "" + (pixels[0] >> 16 & 0xFF));
		Log.v("Decode Green", "" + (pixels[0] >> 8 & 0xFF));
		Log.v("Decode Blue", "" + (pixels[0] & 0xFF));
		Log.v("Decode", "" + pixels[0]);
		Log.v("Decode", "" + image.getPixel(0, 0));
		byte[] b = null;
		try {
			b = LSB2bit.convertArray(pixels);
		} catch (OutOfMemoryError er) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(context.getText(R.string.errorImageTooLarge))
					.setCancelable(false).setPositiveButton(
							context.getText(R.string.ok),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									DecodeActivity.this.finish();
								}
							});
			handler.post(runnableDismmiss);
			AlertDialog alert = builder.create();
			alert.show();
			return;
		}
		final String vvv = LSB2bit.decodeMessage(b, image.getWidth(), image
				.getHeight());
		handler.post(runnableDismmiss);
		if (vvv == null) {
			handler.post(new Runnable() {
				
				public void run() {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setMessage(
							context.getText(R.string.errorNoMobistegoImage))
							.setCancelable(false).setPositiveButton(
									context.getText(R.string.ok),
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											DecodeActivity.this.finish();
										}
									});

					AlertDialog alert = builder.create();
					alert.show();
				}
			});
		} else {
			Log.v("Coded message", vvv);
			Runnable runnableSetText = new Runnable() {
				
				public void run() {
					TextView textDec = (TextView) findViewById(R.id.EditTextDecodedMessage);
					textDec.setText(vvv);
				}
			};
			handler.post(runnableSetText);

		}
	}

}
