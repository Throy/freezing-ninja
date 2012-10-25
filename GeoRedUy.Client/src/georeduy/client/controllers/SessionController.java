// SessionController

// controlador para los casos de uso de sesión.

package georeduy.client.controllers;

// imports

import georeduy.client.model.User;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.GeoRedClient;
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
	
	public void login (String username, String password) throws Exception {
		Map <String, String> params = new HashMap<String, String>();
		params.put ("userName", username);
		params.put ("password", password);

		TokenRepository.getInstance().setToken (GeoRedClient.Get ("/Session", params));
	}
	
	public void loginExternal (String tokenType, String accessToken) throws Exception {
		Map <String, String> params = new HashMap<String, String>();
		params.put ("tokenType", tokenType);
		params.put ("accessToken", accessToken);

		TokenRepository.getInstance().setToken (GeoRedClient.Get ("/Session/LogInExternal", params));
	}
	
	// registrarse en el sistema, paso 1
	
	public void register_step1 (User user) throws Exception {
        Gson gson = new Gson();

		Map<String, String> params1 = new HashMap <String, String>();
		params1.put ("userInfo", gson.toJson(user));

	    GeoRedClient.Post ("/Session/Register", params1);
	    
	    Map<String, String> params2 = new HashMap<String, String>();
		params2.put ("userName", user.getUserName ());
		params2.put ("password", user.getPassword ());
	
	    TokenRepository.getInstance().setToken (GeoRedClient.Get ("/Session", params2));
	}
	
}
