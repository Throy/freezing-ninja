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
    	
		// *** sessionFacebookController.registerFbk_step2 (email, username); ***
	
		// mostrar confirmación
		AlertDialog alertDialog = new AlertDialog.Builder (this).create ();

		alertDialog.setTitle ("Confirmación");
		alertDialog.setMessage ("Te registraste por Facebook con apodo \""
				+ ((EditText) findViewById (R.id.edittext_username)).getText ().toString ()
				+ "\".");
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				// cerrar la actividad.
				finish ();
			}
		});
		alertDialog.show();
    }
}
