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

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 *
 */
public class MainActivity extends Activity {
	//private Context context;
	public static final int PICK_ENCODER = 1;
	public static final int PICK_IMAGE = 2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_wrapper);		
		//context = this;
		initClickListner();
		
	}
	
	
	private void initClickListner()
	{
		Button buttonEncode = (Button) findViewById(R.id.ButtonEncode);
		buttonEncode.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v) {
				
				 Intent intent = new Intent();
				  
				  intent.setComponent(new ComponentName(EncodeActivity.class.getPackage().getName(),
						  EncodeActivity.class.getCanonicalName()));
				  startActivity(intent);
			}
		}
		);
		
		
		Button buttonDecode = (Button) findViewById(R.id.ButtonDecode);
		
		buttonDecode.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v) {
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK); 
				photoPickerIntent.setType("image/png"); 
				startActivityForResult(photoPickerIntent, PICK_IMAGE);
				
				
			}
		}
		);
	}
	
	
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) { 
		super.onActivityResult(requestCode, resultCode, intent); 

		switch (requestCode) {
		case (PICK_IMAGE) :
			if (resultCode == RESULT_OK) {

				Uri photoUri = intent.getData();
				if (photoUri != null) { 
					try {
						  Intent intent1 = new Intent();
						  intent1.setData(photoUri);
						  intent1.setComponent(new ComponentName(DecodeActivity.class.getPackage().getName(),
								  DecodeActivity.class.getCanonicalName()));
						  startActivity(intent1);
						
					} catch (Exception e) { 
						e.printStackTrace(); 
					} 
				} 
			}
		break;
		default:
			break;

		}		
	}

}
