// VisitCommentActivity

// actividad principal.
// utiliza el layout main_activity.

package georeduy.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainMenuActivity extends Activity {
	
	// inicializadores
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.main_menu_activity);
    }
    
    // para colocar menú contextual.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate (R.menu.activity_main, menu);
        return true;
    }
    
    // *** funciones del programador ***
    
    // *** sesión ***
    
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
    
    // *** listas ***
    
    // cliquear Listar sitios -> abrir lista
    
    public void button_sites_list_onClick (View view) {
    	// crear intent de la actividad Listar.
    	Intent intent_sites_list = new Intent (this, SitesListActivity.class);
    	
    	// ejecutar intent.
    	startActivity (intent_sites_list);
    }
    
    // cliquear Listar locales -> abrir lista
    
    public void button_stores_list_onClick (View view) {
    	// crear intent de la actividad Listar.
    	Intent intent_stores_list = new Intent (this, StoresListActivity.class);
    	
    	// ejecutar intent.
    	startActivity (intent_stores_list);
    }
    
    // cliquear Listar productos -> abrir lista
    
    public void button_products_list_onClick (View view) {
    	// crear intent de la actividad Listar.
    	Intent intent_products_list = new Intent (this, ProductsListActivity.class);
    	
    	// agregar id del local al intent
    	intent_products_list.putExtra (StoresListActivity.EXTRA_STORE_ID, 0);
    	
    	// ejecutar intent.
    	startActivity (intent_products_list);
    }
    
    // cliquear Listar compras -> abrir lista
    
    public void button_purchases_list_onClick (View view) {
    	// crear intent de la actividad Listar.
    	Intent intent_purchases_list = new Intent (this, PurchasesListActivity.class);
    	
    	// ejecutar intent.
    	startActivity (intent_purchases_list);
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
    
    // cliquear Mapa -> ver mapa
    
    public void button_map_onClick (View view) {
    	Intent intent_map = new Intent (this, MapaActivity.class);
    	startActivity (intent_map);
    }
}
