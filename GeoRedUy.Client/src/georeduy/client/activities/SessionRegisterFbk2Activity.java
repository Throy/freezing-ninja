// SessionRegisterFbk2Activity

// actividad para el caso de uso Registrarse en el sistema por Facebook, paso 2.
// utiliza el layout session_register_fbk2_activity.

package georeduy.client.activities;

// imports

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SessionRegisterFbk2Activity extends Activity {
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.session_register_fbk2_activity);

        // obtener datos del usuario.
        String email = getIntent().getStringExtra (SessionRegisterFbk1Activity.EXTRA_USER_EMAIL);

        TextView viewEmail = (TextView) findViewById (R.id.textview_email);
        
        viewEmail.setText (email);
    }
    
    // funciones del programador
    
    // cliquear Register -> registrarse por Facebook
    
    public void button_register_onClick (View view) {
		// registrarse, paso 1
		String email = ((TextView) findViewById (R.id.textview_email)).getText ().toString ();
		String username = ((TextView) findViewById (R.id.edittext_username)).getText ().toString ();
    	
		// *** sessionFacebookController.registerStep2 (email, username); ***
		
		// mostrar confirmación
		AlertDialog alertDialog = new AlertDialog.Builder (this).create ();
		
		/*
		alertDialog.setTitle ("Te registraste con\n apodo \""
				+ ((EditText) findViewById (R.id.edittext_username)).getText ().toString ()
				+ "\", \n nombre de pila \""
				+ ((EditText) findViewById (R.id.edittext_firstname)).getText ().toString ()
				+ "\", \n y apellido \""
				+ ((EditText) findViewById (R.id.edittext_lastname)).getText ().toString ()
				+ "\".");
				*/

		TextView mensaje = new TextView (this);
		mensaje.setText ("Te registraste por Facebook con\n apodo \""
				+ ((EditText) findViewById (R.id.edittext_username)).getText ().toString ()
				+ "\".");
		mensaje.setTextSize (24);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins (20, 40, 20, 40);
		mensaje.setLayoutParams (layoutParams);
		alertDialog.setView (mensaje);
		
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				// cerrar la actividad.
				finish ();
			}
		});
		alertDialog.show();
    }
}
