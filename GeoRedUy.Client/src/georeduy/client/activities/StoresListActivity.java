// StoresListActivity

// actividad para el caso de uso Listar locales.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.ProductsController;
import georeduy.client.lists.StoresListAdapter;
import georeduy.client.model.RetailStore;
import georeduy.client.util.CommonUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class StoresListActivity extends Activity {
	
	// datos de los items
	
    public static final String STORE_ITEM_ID = "georeduy.client.activities.store_id";
    public static final String STORE_ITEM_NAME = "georeduy.client.activities.store_name";
    public static final String STORE_ITEM_DESCRIPTION = "georeduy.client.activities.store_description";
    public static final String STORE_ITEM_ADDRESS = "georeduy.client.activities.store_address";
    
    // extras de intents

    public static final String EXTRA_STORE_ID = "georeduy.client.activities.store_id";
    
    // constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.list_activity);
        
        // obtener locales de la caché
        List <RetailStore> stores = ProductsController.getInstance ().getStoresByPosition ();
        
        // poblar lista de locales
        
        ArrayList <HashMap <String, String>> itemsStringList = new ArrayList <HashMap <String, String>> ();
        ArrayList <HashMap <String, Integer>> itemsIntList = new ArrayList <HashMap <String, Integer>> ();

        if (stores != null) {
	        for (RetailStore store : stores) {
	            // crear item
	            HashMap <String, String> itemStringMap = new HashMap <String, String> ();
	            itemStringMap.put (STORE_ITEM_ID, store.getId ());
	            itemStringMap.put (STORE_ITEM_NAME, store.getName ());
	            itemStringMap.put (STORE_ITEM_DESCRIPTION, "");
	            itemStringMap.put (STORE_ITEM_ADDRESS, store.getAddress ());
	 
	            // adding HashList to ArrayList
	            itemsStringList.add (itemStringMap);
	
	            // crear item
	            HashMap <String, Integer> itemIntMap = new HashMap <String, Integer> ();
	 
	            // adding HashList to ArrayList
	            itemsIntList.add (itemIntMap);
	        }
        }
 
        // poblar lista de items
        StoresListAdapter adapter = new StoresListAdapter (this, itemsStringList, itemsIntList);
        ListView listView = (ListView) findViewById (R.id.listView_list);
        listView.setAdapter (adapter);
        
        // cliquear línea -> iniciar actividad de Ver datos
        listView.setOnItemClickListener (new OnItemClickListener() {

        	public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
            	// crear intent de la actividad Ver datos de un local.
            	Intent intent_store_detail = new Intent (parent.getContext (), StoreDetailActivity.class);
            	
            	// agregar id del local al intent
            	String storeId = ((TextView) view.findViewById (R.id.store_id)).getText().toString();
            	intent_store_detail.putExtra (EXTRA_STORE_ID, storeId);

                // *** en realidad este párrafo no va ***
            	String name = ((TextView) view.findViewById (R.id.name)).getText().toString();
            	String address = ((TextView) view.findViewById (R.id.address)).getText().toString();
            	intent_store_detail.putExtra (STORE_ITEM_NAME, name);
            	intent_store_detail.putExtra (STORE_ITEM_ADDRESS, address);
            	
            	// ejecutar intent.
            	startActivity (intent_store_detail);
        	}
        });
    }

    // cliquear Comprar -> abrir lista de productos
    
    public void button_store_item_onClick (View view) {
    	// crear intent de la actividad Listar productos de un local.
    	Intent intent_products_list = new Intent (this, ProductsListActivity.class);
    	
    	// agregar id del local al intent
    	String storeId = ((TextView) ((View) view.getParent ()).findViewById (R.id.store_id)).getText().toString();
    	intent_products_list.putExtra (StoresListActivity.EXTRA_STORE_ID, storeId);
    	
    	// ejecutar intent.
    	startActivity (intent_products_list);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        	// button home
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask (this);
                return true;
        }
        return super.onOptionsItemSelected (item);
    }

}
