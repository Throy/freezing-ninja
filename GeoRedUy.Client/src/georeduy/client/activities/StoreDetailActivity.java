// StoreDetailActivity

// actividad para el caso de uso Ver datos de un local.
// utiliza el layout store_detail_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.SessionController;
import georeduy.client.controllers.ProductsController;
//import georeduy.client.model.Product;
import georeduy.client.util.CommonUtilities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StoreDetailActivity extends Activity {
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.store_detail_activity);
        
        // obtener datos del local a partir del id.
        String id = getIntent().getStringExtra (StoresListActivity.EXTRA_STORE_ID);
        
        //Store store = ProductsController.getInstance ().getStore (id);
        
        // *** en realidad este párrafo no va ***
        String name = getIntent().getStringExtra (StoresListActivity.STORE_ITEM_NAME);
        String address = getIntent().getStringExtra (StoresListActivity.STORE_ITEM_ADDRESS);

        // msotrar datos en el menú
        TextView viewStoreId = (TextView) findViewById (R.id.textview_store_id);
        TextView viewName = (TextView) findViewById (R.id.textview_name);
        TextView viewDescription = (TextView) findViewById (R.id.textview_description);
        TextView viewAddress = (TextView) findViewById (R.id.textview_address);
        
        /// *** en realidad debería tomar los datos del objeto store. ***
        viewStoreId.setText ("" + id);
        viewDescription.setText ("Es un lugar " + id);
        viewName.setText (name);
        viewAddress.setText (address);
    }
    
    // funciones del programador

    // cliquear Comprar -> abrir lista de productos
    
    public void button_store_buy_onClick (View view) {
    	// crear intent de la actividad Listar productos de un local.
    	Intent intent_products_list = new Intent (this, ProductsListActivity.class);
    	
    	// agregar id del local al intent
    	String storeId = ((TextView) findViewById (R.id.textview_store_id)).getText().toString();
    	intent_products_list.putExtra (StoresListActivity.EXTRA_STORE_ID, storeId);
    	
    	// ejecutar intent.
    	startActivity (intent_products_list);
    }
}
