// PurchaseDetailActivity

// actividad para el caso de uso Ver datos de una compra.
// utiliza el layout purchase_detail_activity.

package georeduy.client.activities;

// imports

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PurchaseDetailActivity extends Activity {
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.purchase_detail_activity);
        
        // obtener datos de la visita a partir del id.
        String purchaseId = getIntent().getStringExtra (PurchasesListActivity.EXTRA_PURCHASE_ID);
        
        int idx = Integer.parseInt (purchaseId);
        // Purchase purchase = getPurchase (idx);

        TextView viewPurchaseId = (TextView) findViewById (R.id.textview_purchase_id);
        TextView viewName = (TextView) findViewById (R.id.textview_name);
        TextView viewPrice = (TextView) findViewById (R.id.textview_price);
        
        viewPurchaseId.setText (purchaseId);
        viewName.setText ("Compra " + purchaseId);
        viewPrice.setText ("$ " + purchaseId);
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
    	startActivity (intent_purchase_publish_review);
    }
}
