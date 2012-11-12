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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import georeduy.client.activities.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Looper;
import android.text.format.Formatter;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
     * Intent used to display a message in the screen.
     */
    public static final String DISPLAY_CHAT_MESSAGE_ACTION =
            "georeduy.client.DISPLAY_CHAT_MESSAGE_ACTION";

    /**
     * Intent's extra that contains the message to be displayed.
     */
    public static final String EXTRA_CHAT_MESSAGE = "chat_message";
    public static final String EXTRA_CHAT_USER_ID = "chat_user_id";
    
	public static final String EXTRA_USER_ID = "georeduy.client.user_id";
    public static final String EXTRA_USER_NAME = "georeduy.client.user_name";

	public static final String NEW_SITE_ACTION = "georeduy.client.NEW_SITE_ACTION";

	public static final String NEW_STORE_ACTION = "georeduy.client.NEW_STORE_ACTION";

	public static final String NEW_EVENT_ACTION = "georeduy.client.NEW_EVENT_ACTION";
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
    
    public static void showAlertMessage (Context context, String title, String message) {
    
    	final AlertDialog alertDialog = new AlertDialog.Builder (context).create ();

    	/*
		TextView mensaje = new TextView (context);
		mensaje.setText (message);
		mensaje.setTextSize (24);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins (20, 40, 20, 40);
		mensaje.setLayoutParams (layoutParams);
		alertDialog.setView (mensaje);
		*/

		alertDialog.setTitle (title);
		alertDialog.setMessage (message);
		
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				alertDialog.cancel();
			}
		});
		alertDialog.show();
    }

    // dar formato a fecha
    public static String dateToString (Date date) {
    	SimpleDateFormat format = new java.text.SimpleDateFormat("dd/MMM HH:MM");
    	return format.format (date); 
    }
    /*
    public static String trimId (String id) {
    	return id.substring (id.length() - 3, id.length());
    }
    */

    // dar formato a fecha
    public static String stringToPrice (String priceString) {
    	Double priceDouble = Double.parseDouble (priceString);
    	DecimalFormat format = new DecimalFormat ("###,###.00");
    	return "$ " + format.format (priceDouble);
    }

    // dar formato a fecha
    public static String doubleToPrice (Double priceDouble) {
    	DecimalFormat format = new DecimalFormat ("###,###.00");
    	return "$ " + format.format (priceDouble);
    }
    
	public static int dpToPx(int dp, Context ctx) {
		Resources r = ctx.getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
	}
	
	//originally: http://stackoverflow.com/questions/5418510/disable-the-touch-events-for-all-the-views
	//modified for the needs here
	public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
		int childCount = viewGroup.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = viewGroup.getChildAt(i);
			if(view.isFocusable())
				view.setEnabled(enabled);
			if (view instanceof ViewGroup) {
				enableDisableViewGroup((ViewGroup) view, enabled);
			} else if (view instanceof ListView) {
				if(view.isFocusable())
					view.setEnabled(enabled);
				ListView listView = (ListView) view;
				int listChildCount = listView.getChildCount();
				for (int j = 0; j < listChildCount; j++) {
					if(view.isFocusable())
						listView.getChildAt(j).setEnabled(false);
				}
			}
		}
	}

	// devuelve la distancia en metros entre dos puntos del mapa.
	// las coordenadas están en grados.
	public static int distance(double lon1, double lat1, double lon2, double lat2) {

		final double earthRadius = 6371; // km

		lat1 = Math.toRadians(lat1);
		lon1 = Math.toRadians(lon1);
		lat2 = Math.toRadians(lat2);
		lon2 = Math.toRadians(lon2);

		double dlon = (lon2 - lon1);
		double dlat = (lat2 - lat1);

		double sinlat = Math.sin(dlat / 2);
		double sinlon = Math.sin(dlon / 2);

		double a = (sinlat * sinlat) + Math.cos(lat1)*Math.cos(lat2)*(sinlon*sinlon);
		double c = 2 * Math.asin (Math.min(1.0, Math.sqrt(a)));

		double distanceInMeters = earthRadius * c * 1000;

		return (int)distanceInMeters;

	}
}
