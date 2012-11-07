// SessionController

// controlador para los casos de uso de sesión.

package georeduy.client.controllers;

// imports

import georeduy.client.model.User;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.GeoRedClient;
import georeduy.client.util.OnCompletedCallback;
import georeduy.client.util.TokenRepository;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class SessionController
{
	// *******************
	// instancia singleton
	// *******************
	
	private static SessionController _instance = null;
	
	// *************
	// constructores
	// *************
	
	private SessionController () {
		
	}
	
	public static SessionController getInstance() {
		if (_instance == null) {
			_instance = new SessionController ();
		}
		
		return _instance;
	}

	// *******
	// métodos
	// *******
	
	// iniciar sesión.
	
	public void login (String username, String password, final OnCompletedCallback callback) {
		Map <String, String> params = new HashMap<String, String>();
		params.put ("userName", username);
		params.put ("password", password);

		GeoRedClient.GetAsync ("/Session", params, new OnCompletedCallback() {
			
			@Override
			public void onCompleted(String response, String error) {
				TokenRepository.getInstance().setToken (response);
				if (callback != null)
					callback.onCompleted(response, error);
			}
		});
		
	}
	
	public void loginExternal (String tokenType, String accessToken, final OnCompletedCallback callback) {
		Map <String, String> params = new HashMap<String, String>();
		params.put ("tokenType", tokenType);
		params.put ("accessToken", accessToken);

		GeoRedClient.GetAsync ("/Session/LogInExternal", params, new OnCompletedCallback() {
			
			@Override
			public void onCompleted(String response, String error) {
				TokenRepository.getInstance().setToken (response);
				if (callback != null)
					callback.onCompleted(response, error);
			}
		});
	}
	
	// registrarse en el sistema, paso 1
	
	public void register_step1 (final User user, final OnCompletedCallback callback) {
        Gson gson = new Gson();

		Map<String, String> params1 = new HashMap <String, String>();
		params1.put ("userInfo", gson.toJson(user));

	    GeoRedClient.PostAsync ("/Session/Register", params1, new OnCompletedCallback() {
			
			@Override
			public void onCompleted(String response, String error) {
				login(user.getUserName (), user.getPassword (), callback);
			}
		});
	}
	
}
