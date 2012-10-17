// SessionLoginActivity

// actividad para el caso de uso Iniciar sesi�n en el sistema.
// utiliza el layout session_login_activity.

package georeduy.client.activities;

// imports
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.GeoRedClient;
import georeduy.client.util.TokenRepository;

import java.util.HashMap;
import java.util.Map;

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

public class SessionLoginActivity extends Activity {
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.session_login_activity);
    }
    
    // funciones del programador
    
    // cliquear Login -> iniciar sesi�n en el sistema
    
    public void button_login_onClick (View view) {
		// registrarse, paso 1
		String username = ((TextView) findViewById (R.id.edittext_username)).getText ().toString ();
		String password = ((TextView) findViewById (R.id.edittext_password)).getText ().toString ();
    	
		// *** sessionController.login (username, password); ***
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", username);
		params.put("password", password);

		try {
	        TokenRepository.getInstance().setToken(
	                GeoRedClient.Get("/Session", params));
	        
	        Intent intent = new Intent(this, GCMActivity.class);
			startActivity(intent);
        } catch (Exception e) {
	        CommonUtilities.AlertMessage(this, e.getMessage());
        }
    }
    
    public void button_register_onClick (View view) {
    	Intent intent = new Intent(this, SessionRegisterActivity.class);
		startActivity(intent);
    }
}