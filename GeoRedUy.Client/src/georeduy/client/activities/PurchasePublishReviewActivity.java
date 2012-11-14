// PurchaseReviewActivity

// actividad para el caso de uso Publicar evaluación de una compra.
// utiliza el layout visit_comment_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.ProductsController;
import georeduy.client.util.OnCompletedCallback;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PurchasePublishReviewActivity extends Activity {
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.purchase_publish_review_activity);
        
        // obtener datos de la visita a partir del id.
        String visitId = getIntent().getStringExtra (PurchasesListActivity.EXTRA_PURCHASE_ID);
        
        // DTVisit visit = getVisit (visitId);

        TextView viewVisitId = (TextView) findViewById (R.id.textview_purchase_id);
        viewVisitId.setText (visitId);
    }
    
    // funciones del programador
    
    // cliquear Send -> publicar comentario
    
    public void button_send_onClick (View view) {

    	// obtener el id de la visita y el comentario
    	String purchaseId = ((TextView) findViewById (R.id.textview_purchase_id)).getText ().toString ();
    	String review = ((EditText) findViewById (R.id.edittext_review)).getText ().toString ();
    	
		// intentar visitar el sitio
		ProductsController.getInstance().publishPurchaseReview (purchaseId, review, new OnCompletedCallback() {
			
			@Override
			public void onCompleted (String response, String error)
			{
				// mostrar confirmación
				AlertDialog alertDialog = new AlertDialog.Builder (PurchasePublishReviewActivity.this).create ();

				alertDialog.setTitle ("Confirmación");
				alertDialog.setMessage ("Enviaste la evaluación.");
				alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
					public void onClick (DialogInterface dialog, int which) {
						// cerrar la actividad.
				    	setResult (PurchasesListActivity.ACTIVITY_RESULT_UPDATE);
						finish ();
					}
				});
				
				alertDialog.show();
			}
		});
    }
}
