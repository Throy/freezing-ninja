// StoreDetailActivity

// actividad para el caso de uso Ver datos de un local.
// utiliza el layout store_detail_activity.

package georeduy.client.activities;

// imports

import com.google.gson.Gson;

import georeduy.client.controllers.SessionController;
import georeduy.client.controllers.ProductsController;
import georeduy.client.model.RetailStore;
import georeduy.client.model.Site;
//import georeduy.client.model.Product;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.OnCompletedCallback;
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
        String storeId = getIntent().getStringExtra (StoresListActivity.EXTRA_STORE_ID);
        
        ProductsController.getInstance ().getStoreById (storeId, new OnCompletedCallback() {
			
			@Override
			public void onCompleted (String response, String error)
			{
				// TODO Auto-generated method stub
				if (error == null) {

					// obtener local
			        Gson gson = new Gson();
			        RetailStore store = gson.fromJson (response, RetailStore.class);
			        ProductsController.getInstance().setStorePayPalMail(store.getPayPalMail());
			        // mostrar datos en el men�
			        TextView viewStoreId = (TextView) findViewById (R.id.textview_store_id);
			        TextView viewName = (TextView) findViewById (R.id.textview_name);
			        TextView viewAddress = (TextView) findViewById (R.id.textview_address);
			        TextView viewTelephone = (TextView) findViewById (R.id.textview_telephone);
			        
			        viewStoreId.setText (store.getId ());
			        viewName.setText (store.getName ());
			        viewAddress.setText (store.getAddress ());
			        viewTelephone.setText (store.getPhoneNumber ());
				}
				
				else {
					CommonUtilities.showAlertMessage (StoreDetailActivity.this, "Error StoreDetA onCr", "Hubo un error:\n" + error);
					//finish();
				}
			}
		});
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
