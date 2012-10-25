// SessionRegisterFbk1Activity

// actividad para el caso de uso Registrarse en el sistema por Facebook, paso 1.
// utiliza el layout session_register_fbk1_activity.

package georeduy.client.activities;

// imports

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SessionRegisterFbk1Activity extends Activity {
	
	// datos de los items
	
    public static final String EXTRA_USER_EMAIL = "tsi2.GeoRedDemo.user_email";
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.session_register_fbk1_activity);
        
    }
    
    // funciones del programador
    
    // cliquear Register -> registrarse en el sistema, paso 1
    
    public void button_register_onClick (View view) {
		// registrarse, paso 1
		String email = ((EditText) findViewById (R.id.edittext_email)).getText ().toString ();
		String password = ((TextView) findViewById (R.id.edittext_password)).getText ().toString ();
    	
		// *** sessionFacebookController.registerFbk_step1 (email, password); ***
		
		// mostrar confirmación
		AlertDialog alertDialog = new AlertDialog.Builder (this).create ();

		alertDialog.setTitle ("Confirmación");
		alertDialog.setMessage ("Te registraste con\n correo \"" + email + "\".");
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				// cerrar la actividad.
				finish ();
			    
				// pasar al segundo menú
				button_register2_onClick();
			}
		});
		alertDialog.show();
    }
    
    // mostrar la actividad Registrarse por Facebook, paso 2.
    
    public void button_register2_onClick () {
    	// crear intent de la actividad Registrarse por Facebook, paso 2.
    	Intent intent_session_register_fbk = new Intent (this, SessionRegisterFbk2Activity.class);
    	
    	// agregar correo del usuario al intent
    	String email = ((TextView) findViewById (R.id.edittext_email)).getText().toString();
    	intent_session_register_fbk.putExtra (EXTRA_USER_EMAIL, email);
    	
    	// ejecutar intent.
    	startActivity (intent_session_register_fbk);
    }
}
