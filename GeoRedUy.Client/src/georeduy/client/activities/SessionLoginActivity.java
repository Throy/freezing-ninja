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
	AsyncTask<Void, Void, Void> mRegisterTask;
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
			        
					onLoginSuccess();
		        }
				catch (Exception e) {
			        CommonUtilities.showAlertMessage (SessionLoginActivity.this, "Error SLA bloc", e.getMessage());
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
    	final Activity thisActivity = this;
    	facebook.authorize(this, PERMS, new DialogListener() {
            @Override
            public void onComplete(Bundle values) {
            	final String token = facebook.getAccessToken();
            	
            	(new AsyncTask<Activity, String, String>() {

        			@Override
        			protected String doInBackground(Activity... params) {
        				try {
        					// intentar iniciar sesión
        					SessionController.getInstance().loginExternal("facebook", token);
        			        
        					onLoginSuccess();
        		        }
        				catch (Exception e) {
        			        CommonUtilities.showAlertMessage (params[0], "Error SLA bloc", e.getMessage());
        		        }
        				return "";
        			}
        		}).execute(thisActivity);
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
    
    public void onLoginSuccess() {
    	// Inicializar GCM
    	InitGCM();
    	// Ir al mapa
        Intent intent = new Intent(this, MapaActivity.class);
		startActivity(intent);
    }
    
    public void InitGCM() {
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        
        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
            // Automatically registers application on startup.
            GCMRegistrar.register(this, SENDER_ID);
        } else {
            // Device is already registered on GCM, check server.
            //if (GCMRegistrar.isRegisteredOnServer(this)) {
                // Skips registration.
                // mDisplay.append(getString(R.string.already_registered) + "\n");
            //} else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = this;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        boolean registered =
                                GCMServer.register(context, regId);
                        // At this point all attempts to register with the app
                        // server failed, so we need to unregister the device
                        // from GCM - the app will try to register again when
                        // it is restarted. Note that GCM will send an
                        // unregistered callback upon completion, but
                        // GCMIntentService.onUnregistered() will ignore it.
                        if (!registered) {
                            GCMRegistrar.unregister(context);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);
            //}
        }
    }
}
