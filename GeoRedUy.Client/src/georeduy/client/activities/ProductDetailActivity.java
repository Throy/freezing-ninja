// ProductDetailActivity

// actividad para el caso de uso Ver datos de un producto.
// utiliza el layout product_detail_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.ProductsController;
import georeduy.client.model.Product;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.Config;
import georeduy.client.util.DownloadImageTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailActivity extends Activity {
	// variables
	
	// id del producto
	private String productId;
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.product_detail_activity);
        
        // obtener datos de la visita a partir del id.
        productId = getIntent().getStringExtra (ProductsListActivity.EXTRA_PRODUCT_ID);
		Product product = ProductsController.getInstance().getProduct (productId);

        TextView viewProductId = (TextView) findViewById (R.id.textview_product_id);
        TextView viewName = (TextView) findViewById (R.id.textview_name);
        TextView viewDescription = (TextView) findViewById (R.id.textview_description);
        TextView viewPrice = (TextView) findViewById (R.id.textview_price);
        ImageView viewImage = (ImageView) findViewById(R.id.productImageView);
        
        new DownloadImageTask(viewImage).execute(Config.SERVER_URL + "/Products/GetImageById?productId=" + product.getId());
        
        viewProductId.setText (productId);
        viewName.setText (product.getName ());
        viewDescription.setText (product.getDescription ());
        viewPrice.setText (CommonUtilities.stringToPrice (product.getPrice ()));
    }
    
    // funciones del programador

    // cliquear botón -> iniciar actividad de Agregar item a la compra.
    
    public void button_product_item_onClick (View view) {
    	// crear intent de la actividad Agregar item a la compra.
    	Intent intent_product_buy_add_item = new Intent (this, ProductBuyAddItemActivity.class);
    	
    	// agregar id de la visita al intent
    	productId = ((TextView) findViewById (R.id.textview_product_id)).getText().toString();
    	intent_product_buy_add_item.putExtra (ProductsListActivity.EXTRA_PRODUCT_ID, productId);
    	
    	// ejecutar intent.
    	startActivity (intent_product_buy_add_item);
    }
}
