/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package georeduy.client.util;

import georeduy.client.activities.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Helper class providing methods and constants common to other classes in the
 * app.
 */
public final class CommonUtilities {

    /**
     * Google API project id registered to use GCM.
     */
	public static final String SENDER_ID = "290947280954";

    /**
     * Tag used on log messages.
     */
	public static final String TAG = "GCMDemo";

    /**
     * Intent used to display a message in the screen.
     */
    public static final String DISPLAY_MESSAGE_ACTION =
            "com.google.android.gcm.demo.app.DISPLAY_MESSAGE";

    /**
     * Intent's extra that contains the message to be displayed.
     */
    public static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
    
    public static void AlertMessage(Context context, String message) {
    	final AlertDialog alertDialog = new AlertDialog.Builder (context).create ();

		TextView mensaje = new TextView (context);
		mensaje.setText (message);
		mensaje.setTextSize (24);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins (20, 40, 20, 40);
		mensaje.setLayoutParams (layoutParams);
		alertDialog.setView (mensaje);
		
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				alertDialog.cancel();
			}
		});
		alertDialog.show();
    }
}
