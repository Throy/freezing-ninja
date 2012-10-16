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
import georeduy.client.activities.R;

public class MainActivity extends Activity {
	private String returnToken;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginlayout);
		// Test de comunicacion con el servidor
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", "Agustin");
		params.put("password", "1234");

		try {
	        TokenRepository.getInstance().setToken(
	                GeoRedClient.Get("/Session", params));
        } catch (Exception e1) {
	        // TODO: Handle error
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

		Intent myIntent = new Intent(this, GCMActivity.class);
		startActivity(myIntent);
		
	}

	

}
