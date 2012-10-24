// ProductsBuyListActivity

// actividad para el caso de uso Comprar productos.
// utiliza el layout products_buy_list_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.ProductsController;
import georeduy.client.lists.ProductsBuyListAdapter;

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

public class ProductsBuyListActivity extends Activity {
	
    // constructor

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.products_buy_list_activity);
        
        // poblar lista 2
        
        ArrayList <HashMap <String, String>> itemsStringList = new ArrayList <HashMap <String, String>> ();
        ArrayList <HashMap <String, Integer>> itemsIntList = new ArrayList <HashMap <String, Integer>> ();

        for (int idx = 0; idx < 5; idx += 1) {
            // crear item
            HashMap <String, String> itemStringMap = new HashMap <String, String> ();
            itemStringMap.put (ProductsListActivity.PRODUCT_ITEM_NAME, "Producto " + idx);
 
            // adding HashList to ArrayList
            itemsStringList.add (itemStringMap);

            // crear item
            HashMap <String, Integer> itemIntMap = new HashMap <String, Integer> ();
            itemIntMap.put (ProductsListActivity.PRODUCT_ITEM_ID, idx);
            itemIntMap.put (ProductsListActivity.PRODUCT_ITEM_PRICE, idx * 10);
            itemIntMap.put (ProductsListActivity.PRODUCT_ITEM_UNITS, idx * 2);
 
            // adding HashList to ArrayList
            itemsIntList.add (itemIntMap);
        }
 
        // poblar lista de items
        ProductsBuyListAdapter adapter = new ProductsBuyListAdapter (this, itemsStringList, itemsIntList);
        ListView listView = (ListView) findViewById (R.id.listView_list);
        listView.setAdapter (adapter);
    }
    
    // cliquear Agregar -> iniciar actividad de Agregar item a la compra. 
    
    public void button_product_item_onClick (View view) {
    }
    
    // cliquear Comprar -> iniciar actividad de Comprar productos. 
    
    public void button_product_buy_onClick (View view) {
    	// confirmar realización de compra
    	final AlertDialog alertDialog = new AlertDialog.Builder (this).create ();

		alertDialog.setTitle ("Realizar compra");
		alertDialog.setMessage ("¿Seguro que querés realizar la compra?");
		
		// realizar la compra
		alertDialog.setButton (DialogInterface.BUTTON_POSITIVE, "Sí", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				
				ProductsController.getInstance ().purchaseConfirm ();
				
				// *** mostrar confirmación ***
				
				// cerrar actividades.
				returnMenu ();
			}
		});
		
		// seguir comprando
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				alertDialog.cancel();
			}
		});
		alertDialog.show();
    }
    
    // finalizar el caso de uso Comprar productos.
    
    private void returnMenu () {
    	setResult (ProductsListActivity.ACTIVITY_RESULT_FINISH);
		finish();
    }
    
    // cliquear Cancelar -> salir del menú.
    
    public void button_product_cancel_onClick (View view) {
    	finish();
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
