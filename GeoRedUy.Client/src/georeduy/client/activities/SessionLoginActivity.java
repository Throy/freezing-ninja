// SessionLoginActivity

// actividad para el caso de uso Iniciar sesión en el sistema.
// utiliza el layout session_login_activity.

package georeduy.client.activities;

// imports
import static georeduy.client.util.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static georeduy.client.util.CommonUtilities.SENDER_ID;
import georeduy.client.controllers.SessionController;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.Config;
import georeduy.client.util.GCMServer;
import georeduy.client.util.OnCompletedCallback;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.google.android.gcm.GCMRegistrar;

public class SessionLoginActivity extends Activity {
    // facebook api.
    Facebook facebook = new Facebook("341284062604349");
    // permissions array
    private static final String[] PERMS = new String[]{"user_events"};
	public static final int ACTIVITY_RESULT_NORMAL = 1;
	public static final int ACTIVITY_RESULT_MAP = 9;
    
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

		
		SessionController.getInstance().login (username, password, new OnCompletedCallback() {
			
			@Override
			public void onCompleted(String response, String error) {
				if (error != null) {
					CommonUtilities.showAlertMessage (SessionLoginActivity.this, "Error SLA bloc", error);
				} else {
					onLoginSuccess (false);
				}
			}
		});

    }
    
    // cliquear Registrarse -> mostrar formulario para registrarse
    
    public void button_register_onClick (View view) {
    	Intent intent = new Intent (this, SessionRegisterActivity.class);
        startActivityForResult (intent, SessionLoginActivity.ACTIVITY_RESULT_NORMAL);
    }
    
    // cliquear Registrarse -> mostrar formulario de Registrarse por Facebook, paso 1
    
    public void button_session_register_fbk_onClick (View view) {
    	final Activity thisActivity = this;
    	facebook.authorize(this, PERMS, new DialogListener() {
            @Override
            public void onComplete(Bundle values) {
            	final String token = facebook.getAccessToken();
            	
				// intentar iniciar sesión
				SessionController.getInstance().loginExternal("facebook", token, new OnCompletedCallback() {
					
					@Override
					public void onCompleted(String response, String error) {
						if (error != null) {
							CommonUtilities.showAlertMessage (SessionLoginActivity.this, "Error SLA bloc", error);
						} else {
							onLoginSuccess (true);
						}
					}
				});
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
    
    // luego de iniciar sesión, abre el mapa e inicializa el GCM.
    
    public void onLoginSuccess (boolean startNotiConfig) {
    	// Inicializar GCM
    	GCMServer.InitGCM(this);
    	
    	// Ir al mapa
        Intent intent = new Intent(this, MapaActivity.class);
        
        if (startNotiConfig) {
        	intent.putExtra (MapaActivity.START_NOTI_CONFIG, MapaActivity.START_NOTI_CONFIG_TRUE);
        }
		startActivity(intent);
    }

    // luego de ejecutar el registro, abre el mapa.
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == SessionLoginActivity.ACTIVITY_RESULT_MAP) {
        	onLoginSuccess (true);
        }
    }
}
