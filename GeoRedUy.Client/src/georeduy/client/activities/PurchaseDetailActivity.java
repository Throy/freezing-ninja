// PurchaseDetailActivity

// actividad para el caso de uso Ver datos de una compra.
// utiliza el layout purchase_detail_activity.

package georeduy.client.activities;

// imports

import georeduy.client.model.Comment;
import georeduy.client.model.Purchase;
import georeduy.client.model.PurchaseItem;
import georeduy.client.model.Review;
import georeduy.client.util.CommonUtilities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PurchaseDetailActivity extends Activity {
	
	// atributos
	
	private static Purchase purchase;
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.purchase_detail_activity);
        
        updateItem ();
    }
    
    // actualizar el ítem
    public void updateItem () {
        
        // obtener datos de la visita a partir del id.
        String purchaseId = getIntent().getStringExtra (PurchasesListActivity.EXTRA_PURCHASE_ID);
        
        purchase = null;
        for (Purchase purchase_idx : PurchasesListActivity.purchases) {
        	if (purchase_idx.getId ().equals (purchaseId)) {
        		purchase = purchase_idx;
        	}
        }

        TextView viewPurchaseId = (TextView) findViewById (R.id.textview_purchase_id);
        TextView viewStore = (TextView) findViewById (R.id.textview_store);
        TextView viewPrice = (TextView) findViewById (R.id.textview_price);
        TextView viewDate = (TextView) findViewById (R.id.textview_date);
        TextView viewProductsNames = (TextView) findViewById (R.id.textview_products_names);
        TextView viewProductsPrices = (TextView) findViewById (R.id.textview_products_prices);
        TextView viewReviews = (TextView) findViewById (R.id.textview_reviews);
        
        viewPurchaseId.setText (purchase.getId ());
        viewStore.setText (purchase.getRealStore ().getName ());
        viewPrice.setText (CommonUtilities.stringToPrice (purchase.getPricetotal ()));
        viewDate.setText (CommonUtilities.dateToString (purchase.getDate ()));
        
        String productsNamesText = "";
        String productsPricesText = "";
        
        for (PurchaseItem item : purchase.getItems ()) {
        	productsNamesText +=        	
        			item.getRealProduct ().getName () + "\n\n";
        	productsPricesText +=        	
	    			"\n" + item.getUnits () + " x "
	    			+ CommonUtilities.stringToPrice (item.getRealProduct ().getPrice ()) + "\n";
        }
        
        viewProductsNames.setText (productsNamesText);
        viewProductsPrices.setText (productsPricesText);

        String reviewsText = "";
        for (Review review : purchase.getReviews ()) {
        	reviewsText += review.getText () + "\n\n";
        }
        viewReviews.setText (reviewsText);
    }
    
    // actualizar la lista
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == PurchasesListActivity.ACTIVITY_RESULT_UPDATE) {
        	setResult (PurchasesListActivity.ACTIVITY_RESULT_UPDATE);
        	finish();
        }
    }
    
    // funciones del programador

    // cliquear botón -> iniciar actividad de Agregar item a la compra.
    
    public void button_purchase_item_onClick (View view) {

    	// crear intent de la actividad Publicar evaluación de compra.
    	Intent intent_purchase_publish_review = new Intent (this, PurchasePublishReviewActivity.class);
    	
    	// agregar id de la compra al intent
    	String purchaseId = ((TextView) findViewById (R.id.textview_purchase_id)).getText().toString();
    	intent_purchase_publish_review.putExtra (PurchasesListActivity.EXTRA_PURCHASE_ID, purchaseId);
    	
    	// ejecutar intent.
    	startActivityForResult (intent_purchase_publish_review, PurchasesListActivity.ACTIVITY_RESULT_NORMAL);
    }
}
