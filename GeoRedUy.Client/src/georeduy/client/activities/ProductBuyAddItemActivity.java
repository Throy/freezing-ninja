// ProductBuyAddItemActivity

// actividad para el caso de uso Agregar item a la compra.
// utiliza el layout product_buy_add_item_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.ProductsController;
import georeduy.client.controllers.VisitsController;
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
	private int productId;
	
	
	// precio unitario
	private int priceUnit;
	
	// unidadtes
	private int units;
	
	// inicializadores
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.product_buy_add_item_activity);

		// obtener datos del producto a partir del id.
		productId = Integer.parseInt (getIntent().getStringExtra (ProductsListActivity.EXTRA_PRODUCT_ID));

		// Product product = ProductsController.getInstance().getProduct (idx);

		priceUnit = productId * 10;
		units = 1;

		TextView viewProductId = (TextView) findViewById (R.id.textview_product_id);
		TextView viewName = (TextView) findViewById (R.id.textview_name);
		TextView viewPriceUnit = (TextView) findViewById (R.id.textview_priceunit);
		TextView viewUnits = (TextView) findViewById (R.id.textview_units);
		TextView viewPriceTotal = (TextView) findViewById (R.id.textview_pricetotal);

		viewProductId.setText ("" + productId);
		viewName.setText ("Producto " + productId);
		viewPriceUnit.setText ("$ " + priceUnit);
		viewUnits.setText ("" + 1);
		viewPriceTotal.setText ("$ " + priceUnit * units);

		// agregar listener al campo viewUnits para actualizar el precio.
		viewUnits.addTextChangedListener (new TextWatcher() {
			public void afterTextChanged (Editable s) {
				TextView viewUnits = (TextView) findViewById (R.id.textview_units);
				Button buttonAdd = (Button) findViewById (R.id.button_product_add);
				
				// actualizar cantidad de unidades, o asignar 0
				try {
					units = Integer.parseInt (viewUnits.getText ().toString ());
					buttonAdd.setEnabled (true);
				}
				catch (NumberFormatException exception) {
					units = 0;
					buttonAdd.setEnabled (false);
				}
				
				TextView viewPriceTotal = (TextView) findViewById (R.id.textview_pricetotal);
				viewPriceTotal.setText ("$ " + priceUnit * units);
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
    	
		// intentar agrergar el producto a la compra
		ProductsController.getInstance().purchaseAddItem (productId, units);
		
		// mostrar confirmación
		AlertDialog alertDialog = new AlertDialog.Builder (this).create ();

		alertDialog.setTitle ("Producto agregado");
		alertDialog.setMessage ("Agregaste a la compra "
				+ units + " unidades del producto de id "
				+ productId
				+ ".");
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				// cerrar la actividad.
				finish ();
			}
		});
		
		alertDialog.show();
    }

    // cliquear Cancelar -> salir del menú
    
    public void button_product_cancel_onClick (View view) {
    	finish ();
    }
}
