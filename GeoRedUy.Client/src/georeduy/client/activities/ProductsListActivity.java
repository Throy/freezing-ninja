// ProductsListActivity

// actividad para el caso de uso Listar oroductos.
// utiliza el layout list_activity.

package georeduy.client.activities;

// imports

import georeduy.client.lists.ProductsListAdapter;

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

public class ProductsListActivity extends Activity {
	
	// datos de los items
	
    public static final String PRODUCT_ITEM_ID = "tsi2.GeoRedDemo.product_id";
    public static final String PRODUCT_ITEM_NAME = "tsi2.GeoRedDemo.product_name";
    public static final String PRODUCT_ITEM_DESCRIPTION = "tsi2.GeoRedDemo.product_description";
    public static final String PRODUCT_ITEM_PRICE = "tsi2.GeoRedDemo.product_price";
    public static final String PRODUCT_ITEM_DATE = "tsi2.GeoRedDemo.product_date";
    
    // extras de intents

    public static final String EXTRA_PRODUCT_ID = "tsi2.GeoRedDemo.product_id";
    
    // constructor

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.list_activity);
        
        /*
        ListItem[] itemsList = {item};
        
        // poblar lista
        ArrayAdapter adapter = new ArrayAdapter<ListItem>(this, 
                android.R.layout.simple_expandable_list_item_1, itemsList); 		// toma los myStringArray [idx].toString()
        ListView listView = (ListView) findViewById (R.id.listView_list);
        listView.setAdapter(adapter);
        */
        
        // poblar lista 2
        
        ArrayList <HashMap <String, String>> itemsStringList = new ArrayList <HashMap <String, String>> ();
        ArrayList <HashMap <String, Integer>> itemsIntList = new ArrayList <HashMap <String, Integer>> ();

        for (int idx = 0; idx < 5; idx += 1) {
            // crear item
            HashMap <String, String> itemStringMap = new HashMap <String, String> ();
            itemStringMap.put (PRODUCT_ITEM_NAME, "Producto " + idx);
            itemStringMap.put (PRODUCT_ITEM_DESCRIPTION, "Es un producto " + idx + " y " + (((idx + 5) % 8) + 5));
            itemStringMap.put (PRODUCT_ITEM_PRICE, "$ " + idx);
            itemStringMap.put (PRODUCT_ITEM_DATE, "2012 / 10 / " + idx);
 
            // adding HashList to ArrayList
            itemsStringList.add (itemStringMap);

            // crear item
            HashMap <String, Integer> itemIntMap = new HashMap <String, Integer> ();
            itemIntMap.put (PRODUCT_ITEM_ID, idx);
 
            // adding HashList to ArrayList
            itemsIntList.add (itemIntMap);
        }
 
        // poblar lista de items
        ProductsListAdapter adapter = new ProductsListAdapter (this, itemsStringList, itemsIntList);
        ListView listView = (ListView) findViewById (R.id.listView_list);
        listView.setAdapter (adapter);
        
        // cliquear línea -> iniciar actividad de Ver datos
        listView.setOnItemClickListener (new OnItemClickListener() {

        	public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
            	// crear intent de la actividad Ver datos de un producto.
            	Intent intent_product_detail = new Intent (parent.getContext (), ProductDetailActivity.class);
            	
            	// agregar id del producto al intent
            	String productId = ((TextView) view.findViewById (R.id.product_id)).getText().toString();
            	intent_product_detail.putExtra (EXTRA_PRODUCT_ID, productId);
            	
            	// ejecutar intent.
            	startActivity (intent_product_detail);
        	}
        });
    }
    
    // cliquear botón -> iniciar actividad de Agregar item a la compra. 
    
    public void button_product_item_onClick (View view) {
    	/*
    	// crear intent de la actividad Agregar item a la compra.
    	Intent intent_product_add_item = new Intent (this, ProductBuyAddItemActivity.class);
    	
    	// agregar id del producto al intent
    	String productId = ((TextView) ((View) view.getParent ()).findViewById (R.id.product_id)).getText().toString();
    	intent_product_add_item.putExtra (EXTRA_PRODUCT_ID, productId);
    	
    	// ejecutar intent.
    	startActivity (intent_product_add_item);
    	*/
    	
		// nada
		final Dialog alertDialog = new Dialog (this);
		alertDialog.setContentView (R.layout.product_buy_add_item_dialog);

        TextView viewId = (TextView) alertDialog.findViewById (R.id.product_id);
        TextView viewName = (TextView) alertDialog.findViewById (R.id.textview_name);
        
        viewId.setText (((TextView) ((View) view.getParent ()).findViewById (R.id.product_id)).getText ().toString ());
        viewName.setText (((TextView) ((View) view.getParent ()).findViewById (R.id.name)).getText ().toString ());
        
		// botón ok
        Button buttonOk = (Button) alertDialog.findViewById (R.id.button_product_add);
        buttonOk.setOnClickListener (new OnClickListener() {
            public void onClick (View view) {
				// mostrar confirmación
        		AlertDialog alertDialogOk = new AlertDialog.Builder (ProductsListActivity.this).create ();
        		alertDialogOk.setTitle ("Producto agregado"); // + ((TextView) ((View) view.getParent ()).findViewById (R.id.product_id)).getText ().toString ());
        		alertDialogOk.setMessage ("Agregaste a la compra "
        				+ ((TextView) ((View) view.getParent ().getParent ()).findViewById (R.id.textview_units)).getText ().toString ()
		        		+ " unidades del producto de id "
						+ ((TextView) ((View) view.getParent ().getParent ()).findViewById (R.id.product_id)).getText ().toString ()
						+ ".");
        		
        		alertDialogOk.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
        			public void onClick (DialogInterface dialog, int which) {
                    	alertDialog.dismiss ();
        			}
        		});
        		alertDialogOk.show();
        		//alertDialog.dismiss ();
            }
        });
		
		// botón cancel
        Button buttonCancel = (Button) alertDialog.findViewById (R.id.button_product_cancel);
        buttonCancel.setOnClickListener (new OnClickListener() {
            public void onClick (View view) {
            	//alertDialog.dismiss();
            	alertDialog.dismiss ();
            }
        });
		
		alertDialog.show();
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
