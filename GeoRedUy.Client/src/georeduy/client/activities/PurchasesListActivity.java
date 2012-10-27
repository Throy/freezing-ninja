// PurchasesListActivity

// actividad para el caso de uso Listar compras.
// utiliza el layout purchases_list_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.ProductsController;
import georeduy.client.lists.PurchasesListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	// datos de los items
	
    public static final String PURCHASE_ITEM_ID = "tsi2.GeoRedDemo.purchase_id";
    public static final String PURCHASE_ITEM_NAME = "tsi2.GeoRedDemo.purchase_name";
    public static final String PURCHASE_ITEM_PRICE = "tsi2.GeoRedDemo.purchase_price";
    public static final String PURCHASE_ITEM_DATE = "tsi2.GeoRedDemo.purchase_date";
    
    // extras de intents

    public static final String EXTRA_PURCHASE_ID = "tsi2.GeoRedDemo.purchase_id";
    
    // constructor

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.list_activity);
        
        // inicializar hashtags
        
        ArrayList <HashMap <String, String>> itemsStringList = new ArrayList <HashMap <String, String>> ();
        ArrayList <HashMap <String, Integer>> itemsIntList = new ArrayList <HashMap <String, Integer>> ();
        
        // *** ListArray <Purchase> listPurchases = ProductsController.getInstance ().getPurchases (); ***

        for (int idx = 0; idx < 5; idx += 1) {
        	// inicializar datos
        	int price = idx * 10;
        	
            // crear item
            HashMap <String, String> itemStringMap = new HashMap <String, String> ();
            itemStringMap.put (PURCHASE_ITEM_NAME, "Compra " + idx);
            itemStringMap.put (PURCHASE_ITEM_PRICE, "$ " + price);
            itemStringMap.put (PURCHASE_ITEM_DATE, "2012 / 10 / " + idx);
 
            // adding HashList to ArrayList
            itemsStringList.add (itemStringMap);

            // crear item
            HashMap <String, Integer> itemIntMap = new HashMap <String, Integer> ();
            itemIntMap.put (PURCHASE_ITEM_ID, idx);
 
            // adding HashList to ArrayList
            itemsIntList.add (itemIntMap);
        }
        
        // poblar lista de compras
        PurchasesListAdapter adapter = new PurchasesListAdapter (this, itemsStringList, itemsIntList);
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
            	startActivity (intent_purchase_detail);
        	}
        });
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
