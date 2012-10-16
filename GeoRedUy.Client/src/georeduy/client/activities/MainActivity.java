package georeduy.client.activities;

import georeduy.client.activities.CustomItemizedOverlay;
import georeduy.client.util.GeoRedClient;
import georeduy.client.util.OnCompletedCallback;
import georeduy.client.util.TokenRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.resting.component.RequestParams;
import com.google.resting.component.impl.BasicRequestParams;
<<<<<<< HEAD:GeoRedUy.Client/src/com/example/androidgooglemapsproject/MainActivity.java
import com.google.resting.json.JSONException;
import com.google.resting.json.JSONObject;
import com.example.androidgooglemapsproject.CustomItemizedOverlay;
=======
import georeduy.client.activities.R;
>>>>>>> 8b45cba5dd4cdb6052061dcfd86375bd162a8931:GeoRedUy.Client/src/georeduy/client/activities/MainActivity.java

public class MainActivity extends Activity {
	private String returnToken;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
<<<<<<< HEAD:GeoRedUy.Client/src/com/example/androidgooglemapsproject/MainActivity.java
		setContentView(R.layout.activity_main);

		mapView = (MapView) findViewById(R.id.map_view);
		mapView.setBuiltInZoomControls(true);
		super.onCreate(savedInstanceState);
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.cart);
		CustomItemizedOverlay itemizedOverlay = new CustomItemizedOverlay(
		        drawable, this);

		GeoPoint point = new GeoPoint(latitudeE6, longitudeE6);
		OverlayItem overlayitem = new OverlayItem(point, "Hola",
		        "Estoy en atenas!");

		itemizedOverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedOverlay);

		MapController mapController = mapView.getController();

		mapController.animateTo(point);
		mapController.setZoom(6);

		
		
=======
		setContentView(R.layout.loginlayout);
		// Test de comunicacion con el servidor
>>>>>>> 8b45cba5dd4cdb6052061dcfd86375bd162a8931:GeoRedUy.Client/src/georeduy/client/activities/MainActivity.java
		Map<String, String> params = new HashMap<String, String>();
		JSONObject userInfo = new JSONObject();
		try {
	        userInfo.put("userName", "Agustin"); 
			userInfo.put("password", "1234");
			userInfo.put("email", "agustin@lavabit.com");
		} catch (JSONException e2) {
	        e2.printStackTrace();
        }
		params.put("userInfo", userInfo.toString());

		try {
	        GeoRedClient.Post("/Session/Register", params);
        } catch (Exception e1) {
	        // TODO: Handle error
        	e1.printStackTrace();
        }
		
		// Test de comunicacion con el servidor
		params = new HashMap<String, String>();
		params.put("userName", "Agustin");
		params.put("password", "1234");

		try {
	        TokenRepository.getInstance().setToken(
	                GeoRedClient.Get("/Session", params));
        } catch (Exception e1) {
	        // TODO: Handle error
        	e1.printStackTrace();
        }

		final AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(this).create();

		params = new HashMap<String, String>();
		params.put("name", "AgustinC");

		GeoRedClient.GetAsync("/Notifications/SayHello", params,
		        new OnCompletedCallback() {

			        @Override
			        public void onCompleted(String response, String error) {
				        alertDialog.setTitle("Rest Client Test");
				        alertDialog.setMessage(response);
				        alertDialog.show();
			        }
		        });
		
		Thread t = new Thread(new Runnable(){
			
			@Override
			public void run(){
				AccountManager accountManager = AccountManager.get(MainActivity.this);
				AccountManagerFuture<Bundle> amf = accountManager
				        .getAuthTokenByFeatures("com.google", "cp", null,
				        		MainActivity.this, Bundle.EMPTY, Bundle.EMPTY, null,
				                null);

				Bundle bundle = null;
				String token = null;
				try {
					bundle = amf.getResult();
					String name = (String) bundle
					        .get(AccountManager.KEY_ACCOUNT_NAME);
					String type = (String) bundle
					        .get(AccountManager.KEY_ACCOUNT_TYPE);
					 returnToken = bundle
					        .getString(AccountManager.KEY_AUTHTOKEN);
					
				} catch (OperationCanceledException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (AuthenticatorException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		t.start();

		//Intent myIntent = new Intent(this, GCMActivity.class);
		//startActivity(myIntent);
		
	}

	

}
