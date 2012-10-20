// SessionLoginActivity

// actividad para el caso de uso Iniciar sesión en el sistema.
// utiliza el layout session_login_activity.

package georeduy.client.activities;

// imports
import georeduy.client.controllers.SessionController;
import georeduy.client.util.CommonUtilities;

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
    
    // cliquear Login -> iniciar sesión en el sistema
    
    public void button_login_onClick (View view) {
		// registrarse, paso 1
		String username = ((TextView) findViewById (R.id.edittext_username)).getText ().toString ();
		String password = ((TextView) findViewById (R.id.edittext_password)).getText ().toString ();

		try {
			// intentar iniciar sesión
			SessionController.getInstance().login (username, password);
	        
			// abrir menú de GCM
	        Intent intent = new Intent(this, GCMActivity.class);
			startActivity(intent);
        }
		catch (Exception e) {
	        CommonUtilities.showAlertMessage (this, "Error SLA bloc", e.getMessage());
        }
    }
    
    // cliquear Registrarse -> mostrar formulario para registrarse
    
    public void button_register_onClick (View view) {
    	Intent intent = new Intent (this, SessionRegisterActivity.class);
		startActivity(intent);
    }
    
    // cliquear Registrarse -> mostrar formulario de Registrarse por Facebook, paso 1
    
    public void button_session_register_fbk_onClick (View view) {
    	Intent intent_session_register_fbk1 = new Intent (this, SessionRegisterFbk1Activity.class);
    	startActivity (intent_session_register_fbk1);
    }
    
    // cliquear Msin menu -> abrir menú principal 
    // *** en realidad no va ***
    
    public void button_main_menu_onClick (View view) {
    	Intent intent_main_menu = new Intent (this, MainMenuActivity.class);
    	startActivity (intent_main_menu);
    }
}
