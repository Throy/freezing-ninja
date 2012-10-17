package georeduy.client.activities;

import georeduy.client.model.User;
import georeduy.client.model.UserData;
import georeduy.client.util.GeoRedClient;
import georeduy.client.util.OnCompletedCallback;
import georeduy.client.util.TokenRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

public class MainActivity extends Activity {
	private String returnToken;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginlayout);

		Map<String, String> params = new HashMap<String, String>();
        
        User user = new User();
        user.setPassword("1234");
        user.setUserName("Agustin");
        UserData userData = new UserData();
        userData.setEmail("agustin@lavabit.com");
        user.setUserData(userData);

        Gson gson = new Gson();
        
		params.put("userInfo", gson.toJson(user));

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

		Intent myIntent = new Intent(this, GCMActivity.class);
		startActivity(myIntent);
		
	}

	

}
