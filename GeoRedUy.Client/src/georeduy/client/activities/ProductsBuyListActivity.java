// ProductsBuyListActivity

// actividad para el caso de uso Comprar productos.
// utiliza el layout products_buy_list_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.ProductsController;
import georeduy.client.lists.ProductsBuyListAdapter;
import georeduy.client.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ProductsBuyListActivity extends Activity {
	
	// instancia singleton
	private static ProductsBuyListActivity _instance;
	
    // constructor

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.products_buy_list_activity);
        
        // inicializar singleton
        _instance = this;
        
        // poblar lista de �tems
        
        ArrayList <HashMap <String, String>> itemsStringList = new ArrayList <HashMap <String, String>> ();
        ArrayList <HashMap <String, Integer>> itemsIntList = new ArrayList <HashMap <String, Integer>> ();

        List <Product> products = ProductsController.getInstance ().purchaseGetProducts ();
        HashMap <String, Integer> productUnits = ProductsController.getInstance ().purchaseGetUnits ();
        HashMap <String, String> productPrices = ProductsController.getInstance ().purchaseGetPrices ();

        for (Product product : products) {
        	try {
        		// si se agregaron unidades, ...
        		int units = productUnits.get (product.getId ());
        		if (units >= 1) {

            		// s�lo agregar productos con al menos 1 �tem
            		
                    // crear item
                    HashMap <String, String> itemStringMap = new HashMap <String, String> ();
                    itemStringMap.put (ProductsListActivity.PRODUCT_ITEM_ID, product.getId ());
                    itemStringMap.put (ProductsListActivity.PRODUCT_ITEM_NAME, product.getName ());
                    itemStringMap.put (ProductsListActivity.PRODUCT_ITEM_PRICE, product.getPrice ());
         
                    // adding HashList to ArrayList
                    itemsStringList.add (itemStringMap);

                    // crear item
                    HashMap <String, Integer> itemIntMap = new HashMap <String, Integer> ();
                    itemIntMap.put (ProductsListActivity.PRODUCT_ITEM_UNITS, units);
         
                    // adding HashList to ArrayList
                    itemsIntList.add (itemIntMap);
        		}
        	}
        	catch (NullPointerException ex) {
        	}
        	
        }
 
        // poblar lista de items
        ProductsBuyListAdapter adapter = new ProductsBuyListAdapter (this, itemsStringList, itemsIntList);
        ListView listView = (ListView) findViewById (R.id.listView_list);
        listView.setAdapter (adapter);
        
        // actualizar precio total
        updatePriceTotal ();
    }
    
    // actualizar el precio total. 
    
    public static ProductsBuyListActivity getInstance() {
        return _instance;
    }
    
    // actualizar el precio total. 
    
    public void updatePriceTotal () {
        TextView viewPricetotal = (TextView) findViewById (R.id.pricetotal);
        viewPricetotal.setText ("$ " + ProductsController.getInstance().purchaseGetPricetotal ());
    }
    
    // cliquear Comprar -> iniciar actividad de Comprar productos. 
    
    public void button_product_buy_onClick (View view) {
    	// confirmar realizaci�n de compra
    	final AlertDialog alertDialog = new AlertDialog.Builder (this).create ();

		alertDialog.setTitle ("Realizar compra");
		alertDialog.setMessage ("�Seguro que quer�s realizar la compra?");
		
		// realizar la compra
		alertDialog.setButton (DialogInterface.BUTTON_POSITIVE, "S�", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				// realizar compra.
				ProductsController.getInstance ().purchaseConfirm ();
				
				// *** mostrar confirmaci�n ***
				
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
    
    // cliquear Cancelar -> salir del men�.
    
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
