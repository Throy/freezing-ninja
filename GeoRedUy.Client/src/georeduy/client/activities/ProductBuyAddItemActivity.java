// ProductBuyAddItemActivity

// actividad para el caso de uso Agregar item a la compra.
// utiliza el layout product_buy_add_item_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.ProductsController;
import georeduy.client.model.Product;
import georeduy.client.util.CommonUtilities;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProductBuyAddItemActivity extends Activity {
	// variables
	
	// id del producto
	private String productId;
	
	// nombre del producto
	private String productName;
	
	// precio unitario
	private Double priceUnit;
	
	// unidadtes
	private int units;
	
	// inicializadores
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.product_buy_add_item_activity);

		// obtener datos del producto a partir del id.
		productId = getIntent().getStringExtra (ProductsListActivity.EXTRA_PRODUCT_ID);
		Product product = ProductsController.getInstance().getProduct (productId);

		units = ProductsController.getInstance().purchaseGetItemUnits (productId);
		priceUnit = Double.parseDouble (product.getPrice ());
		productName = product.getName ();

		TextView viewProductId = (TextView) findViewById (R.id.textview_product_id);
		TextView viewName = (TextView) findViewById (R.id.textview_name);
		TextView viewPriceUnit = (TextView) findViewById (R.id.textview_priceunit);
		TextView viewUnits = (TextView) findViewById (R.id.textview_units);
		TextView viewPriceTotal = (TextView) findViewById (R.id.textview_pricetotal);

		viewProductId.setText (productId);
		viewName.setText (productName);
		viewPriceUnit.setText (CommonUtilities.doubleToPrice (priceUnit));
		viewUnits.setText ("" + units);
		viewPriceTotal.setText (CommonUtilities.doubleToPrice (priceUnit * units));

		// agregar listener al campo viewUnits para actualizar el precio.
		viewUnits.addTextChangedListener (new TextWatcher() {
			public void afterTextChanged (Editable s) {
				TextView viewUnits = (TextView) findViewById (R.id.textview_units);
				//Button buttonAdd = (Button) findViewById (R.id.button_product_add);
				
				// actualizar cantidad de unidades, o asignar 1
				try {
					units = Integer.parseInt (viewUnits.getText ().toString ());
					//buttonAdd.setEnabled (true);
				}
				catch (NumberFormatException exception) {
					units = 1;
					//buttonAdd.setEnabled (false);
				}
				
				TextView viewPriceTotal = (TextView) findViewById (R.id.textview_pricetotal);
				viewPriceTotal.setText (CommonUtilities.doubleToPrice (priceUnit * units));
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
	}
    
    // funciones del programador

    // cliquear Agregar -> agregar producto a la compra
    
    public void button_product_add_onClick (View view) {
    	
		// agrergar el producto a la compra
		ProductsController.getInstance().purchaseAddItemUnits (productId, units);
		
		// cerrar la actividad.
		finish ();
    }

    // cliquear Cancelar -> salir del men�
    
    public void button_product_cancel_onClick (View view) {
    	finish ();
    }
}
