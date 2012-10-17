// SessionRegisterActivity

// actividad para el caso de uso Registrarse en el sistema.
// utiliza el layout session_register_activity.

package georeduy.client.activities;

// imports

import georeduy.client.model.User;
import georeduy.client.model.UserData;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.GeoRedClient;
import georeduy.client.util.TokenRepository;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SessionRegisterActivity extends Activity {
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.session_register_activity);
    }
    
    // funciones del programador
    
    // cliquear Register -> registrrse en el sistema
    
    public void button_register_onClick (View view) {
		// registrarse en el sistema
		String username = ((TextView) findViewById (R.id.edittext_username)).getText ().toString ();
		String password1 = ((TextView) findViewById (R.id.edittext_password1)).getText ().toString ();
		String password2 = ((TextView) findViewById (R.id.edittext_password2)).getText ().toString ();
		String firstname = ((TextView) findViewById (R.id.edittext_firstname)).getText ().toString ();
		String lastname = ((TextView) findViewById (R.id.edittext_lastname)).getText ().toString ();
		String email = ((TextView) findViewById (R.id.edittext_email)).getText ().toString ();
    	
		Map<String, String> params = new HashMap<String, String>();
        
		if (!password1.equals(password2)) {
			CommonUtilities.AlertMessage(this, getString(R.string.register_passwords_do_not_match));
			return;
		}
		
        User user = new User();
        user.setPassword(password1);
        user.setUserName(username);
        UserData userData = new UserData();
        userData.setEmail(email);
        userData.setName(firstname);
        userData.setLastName(lastname);
        user.setUserData(userData);

        Gson gson = new Gson();
        
		params.put("userInfo", gson.toJson(user));

		try {
	        GeoRedClient.Post("/Session/Register", params);
	        
	        params = new HashMap<String, String>();
			params.put("userName", username);
			params.put("password", password1);

	        TokenRepository.getInstance().setToken(
	                GeoRedClient.Get("/Session", params));
	        
	        Intent intent = new Intent(this, GCMActivity.class);
			startActivity(intent);	        
		} catch (Exception e) {
	        CommonUtilities.AlertMessage(this, e.getMessage());
        }
		
    }
}
