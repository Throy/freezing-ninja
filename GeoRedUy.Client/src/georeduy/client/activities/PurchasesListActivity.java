// PurchasesListActivity

// actividad para el caso de uso Listar compras.
// utiliza el layout purchases_list_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.ProductsController;
import georeduy.client.lists.PurchasesListAdapter;
import georeduy.client.model.Product;
import georeduy.client.model.Purchase;
import georeduy.client.model.User;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.OnCompletedCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PurchasesListActivity extends Activity {
	
	// constantes
	
	// datos de los items
	
    public static final String PURCHASE_ITEM_ID = "tsi2.GeoRedDemo.purchase_id";
    public static final String PURCHASE_ITEM_NAME = "tsi2.GeoRedDemo.purchase_name";
    public static final String PURCHASE_ITEM_PRICE = "tsi2.GeoRedDemo.purchase_price";
    public static final String PURCHASE_ITEM_DATE = "tsi2.GeoRedDemo.purchase_date";
    
    // extras de intents

    public static final String EXTRA_PURCHASE_ID = "tsi2.GeoRedDemo.purchase_id";
    
    // resultados
	public static final int ACTIVITY_RESULT_NORMAL = 1;
	public static final int ACTIVITY_RESULT_UPDATE = 9;
    
    // atributos
    
    public static List <Purchase> purchases;
    
    // constructor

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.list_activity);
        
        updateList();
    }
    
    // actualizar la lista
    public void updateList() {
        ProductsController.getInstance ().getPurchases (new OnCompletedCallback() {
			
			@Override
			public void onCompleted (String response, String error)
			{
				if (error == null) {

					// obtener compras
			        Gson gson = new Gson();
		        	Type listType = new TypeToken <ArrayList <Purchase>>() {}.getType();
		    		purchases = gson.fromJson (response, listType);
		            
		            // inicializar hashtags
		            
		            ArrayList <HashMap <String, String>> itemsStringList = new ArrayList <HashMap <String, String>> ();
		            ArrayList <HashMap <String, Integer>> itemsIntList = new ArrayList <HashMap <String, Integer>> ();
					
			        for (Purchase purchase : purchases) {
			        	
			            // crear item
			            HashMap <String, String> itemStringMap = new HashMap <String, String> ();
			            itemStringMap.put (PURCHASE_ITEM_ID, purchase.getId ());
			            itemStringMap.put (PURCHASE_ITEM_NAME, purchase.getRealStore ().getName ());
			            itemStringMap.put (PURCHASE_ITEM_PRICE, CommonUtilities.stringToPrice (purchase.getPricetotal ()));
			            itemStringMap.put (PURCHASE_ITEM_DATE, CommonUtilities.dateToString (purchase.getDate ()));
			 
			            // adding HashList to ArrayList
			            itemsStringList.add (itemStringMap);

			            // crear item
			            HashMap <String, Integer> itemIntMap = new HashMap <String, Integer> ();
			 
			            // adding HashList to ArrayList
			            itemsIntList.add (itemIntMap);
			        }
			        
			        // poblar lista de compras
			        PurchasesListAdapter adapter = new PurchasesListAdapter (PurchasesListActivity.this, itemsStringList, itemsIntList);
			        ListView listView = (ListView) findViewById (R.id.listView_list);
			        listView.setAdapter (adapter);
			        
			        // cliquear línea -> iniciar actividad de Ver datos
			        listView.setOnItemClickListener (new OnItemClickListener() {

			        	public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
			            	// crear intent de la actividad Ver datos de una compra.
			            	Intent intent_purchase_detail = new Intent (parent.getContext (), PurchaseDetailActivity.class);
			            	
			            	// agregar id de la compra al intent
			            	String purchaseId = ((TextView) view.findViewById (R.id.purchase_id)).getText().toString();
			            	intent_purchase_detail.putExtra (EXTRA_PURCHASE_ID, purchaseId);
			            	
			            	// ejecutar intent.
			    	        startActivityForResult (intent_purchase_detail, PurchasesListActivity.ACTIVITY_RESULT_NORMAL);
			        	}
			        });
				}
				
				else {
					CommonUtilities.showAlertMessage (PurchasesListActivity.this, "Error PurLA onCr", "Hubo un error:\n" + error);
					//finish();
				}
			}
		});
    }
    
    // actualizar la lista
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == PurchasesListActivity.ACTIVITY_RESULT_UPDATE) {
        	updateList();
        }
    }
    
    // cliquear Evaluar -> iniciar actividad de Evaluar compra. 
    
    public void button_purchase_item_onClick (View view) {

    	// crear intent de la actividad Agregar item a la compra.
    	Intent intent_purchase_review = new Intent (this, PurchasePublishReviewActivity.class);
    	
    	// agregar id de la visita al intent
    	String purchaseId = ((TextView) ((View) view.getParent ()).findViewById (R.id.purchase_id)).getText().toString();
    	intent_purchase_review.putExtra (EXTRA_PURCHASE_ID, purchaseId);
    	
    	// ejecutar intent.
        startActivity (intent_purchase_review);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
        	// button home
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask (this);
                return true;
        }
        return super.onOptionsItemSelected (item);
    }
}
