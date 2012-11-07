// SessionRegisterActivity

// actividad para el caso de uso Registrarse en el sistema.
// utiliza el layout session_register_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.SessionController;
import georeduy.client.model.User;
import georeduy.client.model.UserData;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.GCMServer;
import georeduy.client.util.OnCompletedCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
		// obtener datos
		final String username = ((TextView) findViewById (R.id.edittext_username)).getText ().toString ();
		final String password1 = ((TextView) findViewById (R.id.edittext_password1)).getText ().toString ();
		final String password2 = ((TextView) findViewById (R.id.edittext_password2)).getText ().toString ();
		final String firstname = ((TextView) findViewById (R.id.edittext_firstname)).getText ().toString ();
		final String lastname = ((TextView) findViewById (R.id.edittext_lastname)).getText ().toString ();
		final String email = ((TextView) findViewById (R.id.edittext_email)).getText ().toString ();
    	
        // si las contraseñas no coinciden, mostrar mensaje de error.
		if (! password1.equals (password2)) {
			CommonUtilities.showAlertMessage (this, "Datos incorrectos", getString (R.string.register_passwords_do_not_match));
			return;
		}
		
		(new AsyncTask<Activity, String, String>() {
			@Override
			protected String doInBackground(Activity... params) {
				try {
					// generar objeto User
			        User user = new User();
			        user.setPassword (password1);
			        user.setUserName (username);
			        UserData userData = new UserData();
			        userData.setEmail (email);
			        userData.setName (firstname);
			        userData.setLastName (lastname);
			        user.setUserData (userData);
		
					// intentar registrarse en el sistema
					SessionController.getInstance().register_step1 (user, new OnCompletedCallback() {
						
						@Override
						public void onCompleted(String response, String error) {
							if (error != null) {
								CommonUtilities.showAlertMessage (SessionRegisterActivity.this, "Error SRA broc", error);
							} else {
								// Inicializar GCM
						    	GCMServer.InitGCM(SessionRegisterActivity.this);
						    	// Ir al mapa
						        Intent intent = new Intent(SessionRegisterActivity.this, MapaActivity.class);
								startActivity(intent);
							}
						}
					});
		
					// abrir menú de GCM
			        Intent intent = new Intent (params[0], GCMActivity.class);
					startActivity(intent);	        
				}
				catch (Exception e) {
			        
		        }
				
				return "";
			}
		}).execute(this);
		
    }
}
