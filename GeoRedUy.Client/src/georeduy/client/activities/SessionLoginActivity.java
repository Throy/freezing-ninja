// SessionLoginActivity

// actividad para el caso de uso Iniciar sesión en el sistema.
// utiliza el layout session_login_activity.

package georeduy.client.activities;

// imports
import georeduy.client.controllers.SessionController;
import georeduy.client.util.CommonUtilities;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class SessionLoginActivity extends Activity {
    // facebook api.
    Facebook facebook = new Facebook("341284062604349");
    // permissions array
    private static final String[] PERMS = new String[]{"user_events"};
    
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
		final String username = ((TextView) findViewById (R.id.edittext_username)).getText ().toString ();
		final String password = ((TextView) findViewById (R.id.edittext_password)).getText ().toString ();

		(new AsyncTask<Activity, String, String>() {

			@Override
			protected String doInBackground(Activity... params) {
				try {
					// intentar iniciar sesión
					SessionController.getInstance().login (username, password);
			        
					// abrir menú de GCM
			        Intent intent = new Intent(params[0], GCMActivity.class);
					startActivity(intent);
		        }
				catch (Exception e) {
			        CommonUtilities.showAlertMessage (params[0], "Error SLA bloc", e.getMessage());
		        }
				return "";
			}
		}).execute(this);

    }
    
    // cliquear Registrarse -> mostrar formulario para registrarse
    
    public void button_register_onClick (View view) {
    	Intent intent = new Intent (this, SessionRegisterActivity.class);
		startActivity(intent);
    }
    
    // cliquear Registrarse -> mostrar formulario de Registrarse por Facebook, paso 1
    
    public void button_session_register_fbk_onClick (View view) {
    	facebook.authorize(this, PERMS, new DialogListener() {
            @Override
            public void onComplete(Bundle values) {
            	String token = facebook.getAccessToken();
            	Log.i("GEOUYRED", "facebook token: " + token);
            }

            @Override
            public void onFacebookError(FacebookError error) {}

            @Override
            public void onError(DialogError e) {}

            @Override
            public void onCancel() {}
        });
    }
    
    // cliquear Msin menu -> abrir menú principal 
    // *** en realidad no va ***
    
    public void button_main_menu_onClick (View view) {
    	Intent intent_main_menu = new Intent (this, MainMenuActivity.class);
    	startActivity (intent_main_menu);
    }
}
