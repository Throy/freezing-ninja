// ProductDetailActivity

// actividad para el caso de uso Ver datos de un producto.
// utiliza el layout product_detail_activity.

package georeduy.client.activities;

// imports

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProductDetailActivity extends Activity {
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.product_detail_activity);
        
        // obtener datos de la visita a partir del id.
        String productId = getIntent().getStringExtra (ProductsListActivity.EXTRA_PRODUCT_ID);
        
        //int idx = Integer.parseInt (visitId);
        // DTVisit visit = getVisit (idx);

        TextView viewProductId = (TextView) findViewById (R.id.textview_product_id);
        TextView viewName = (TextView) findViewById (R.id.textview_name);
        TextView viewDescription = (TextView) findViewById (R.id.textview_description);
        TextView viewPrice = (TextView) findViewById (R.id.textview_price);
        
        viewProductId.setText (productId);
        viewName.setText ("Producto " + productId);
        viewDescription.setText ("Es un producto " + productId);
        viewPrice.setText ("$ " + productId);
    }
    
    // funciones del programador

    // cliquear botón -> iniciar actividad de Agregar item a la compra.
    
    public void button_product_item_onClick (View view) {
    	/*
    	// crear intent de la actividad Agregar item a la compra.
    	Intent intent_product_add_item = new Intent (this, ProductBuyAddItemActivity.class);

    	// agregar id del producto al intent
    	String productId = ((TextView) findViewById (R.id.textview_product_id)).getText().toString();
    	intent_product_add_item.putExtra (ProductsListActivity.EXTRA_PRODUCT_ID, productId);
    	
    	// ejecutar intent.
    	startActivity (intent_product_add_item);
    	*/
    }
}
