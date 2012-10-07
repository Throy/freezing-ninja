package com.example.androidgooglemapsproject;

import georeduy.client.util.GeoRedClient;
import georeduy.client.util.OnCompletedCallback;
import georeduy.client.util.TokenRepository;

import java.io.IOException;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.resting.component.RequestParams;
import com.google.resting.component.impl.BasicRequestParams;
import com.example.androidgooglemapsproject.CustomItemizedOverlay;

public class MainActivity extends MapActivity {

	private MapView mapView;
	private static final int latitudeE6 = 37985339;
	private static final int longitudeE6 = 23716735;
	private String returnToken;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
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

		// Test de comunicacion con el servidor
		RequestParams params = new BasicRequestParams();
		params.add("userName", "Agustin");
		params.add("password", "1234");

		TokenRepository.getInstance().setToken(
		        GeoRedClient.get("/Session", params));

		final AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(this).create();

		params = new BasicRequestParams();
		params.add("name", "AgustinC");

		GeoRedClient.getAsync("/Notifications", params,
		        new OnCompletedCallback() {

			        @Override
			        public void onCompleted(String response) {
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

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
