// VisitCommentActivity

// actividad principal.
// utiliza el layout main_activity.

package georeduy.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainMenuActivity extends Activity {

	// constantes

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
	// inicializadores
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.main_activity);
    }
    
    // para colocar menú contextual.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate (R.menu.activity_main, menu);
        return true;
    }
    
    // funciones del programador
    
    // cliquear Enviar -> mostrar mensaje
    
    /*
    public void sendMessage (View view) {
    	// crear intent
    	Intent intent_displayMessage = new Intent (this, DisplayMessageActivity.class);
    	
    	// agregar mensaje ingresado al intent
    	EditText editText = (EditText) findViewById (R.id.edit_text_message);
    	String message = editText.getText().toString();
    	intent_displayMessage.putExtra (EXTRA_MESSAGE, message);
    	
    	// ejecutar intent
    	startActivity (intent_displayMessage);
    }
    */
    
    // cliquear Listar -> abrir lista
    
    public void button_teh_list_onClick (View view) {
    	// crear intent de la actividad Listar.
    	Intent intent_list = new Intent (this, ListActivity.class);
    	
    	/*
    	// agregar dato al intent
    	EditText editText = (EditText) findViewById (R.id.editText_message);
    	String message = editText.getText().toString();
    	intent_displayMessage.putExtra (EXTRA_MESSAGE, message);
    	*/
    	
    	// ejecutar intent.
    	startActivity (intent_list);
    }
    
    // cliquear Registrarse -> mostrar formulario para registrarse
    
    public void button_session_register_onClick (View view) {
    	// crear intent de la actividad Registrarse.
    	Intent intent_session_register = new Intent (this, SessionRegisterActivity.class);
    	
    	// ejecutar intent.
    	startActivity (intent_session_register);
    }
    
    // cliquear Registrarse -> mostrar formulario de Registrarse por Facebook, paso 1
    
    public void button_session_register_fbk_onClick (View view) {
    	// crear intent de la actividad Registrarse por Facebook.
    	Intent intent_session_register_fbk1 = new Intent (this, SessionRegisterFbk1Activity.class);
    	
    	// ejecutar intent.
    	startActivity (intent_session_register_fbk1);
    }
    
    // cliquear Login -> mostrar formulario para iniciar sesión
    
    public void button_session_login_onClick (View view) {
    	// crear intent de la actividad Registrarse.
    	Intent intent_session_login = new Intent (this, SessionLoginActivity.class);
    	
    	// ejecutar intent.
    	startActivity (intent_session_login);
    }
    
    // cliquear Listar sitios -> abrir lista
    
    public void button_sites_list_onClick (View view) {
    	// crear intent de la actividad Listar.
    	Intent intent_sites_list = new Intent (this, SitesListActivity.class);
    	
    	// ejecutar intent.
    	startActivity (intent_sites_list);
    }
    
    // cliquear Listar productos -> abrir lista
    
    public void button_products_list_onClick (View view) {
    	// crear intent de la actividad Listar.
    	Intent intent_products_list = new Intent (this, ProductsListActivity.class);
    	
    	// ejecutar intent.
    	startActivity (intent_products_list);
    }
    
    // cliquear Listar usuarios -> abrir lista
    
    public void button_users_list_onClick (View view) {
    	// crear intent de la actividad Listar.
    	Intent intent_users_list = new Intent (this, UsersListActivity.class);
    	
    	// ejecutar intent.
    	startActivity (intent_users_list);
    }
    
    // cliquear Listar visitas -> abrir lista
    
    public void button_visits_list_onClick (View view) {
    	// crear intent de la actividad Listar.
    	Intent intent_visits_list = new Intent (this, VisitsListActivity.class);
    	
    	// ejecutar intent.
    	startActivity (intent_visits_list);
    }
}
