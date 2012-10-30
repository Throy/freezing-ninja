// VisitCommentActivity

// actividad para el caso de uso Publicar comentario de una visita.
// utiliza el layout visit_publish_comment_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.SitesController;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class VisitPublishCommentActivity extends Activity {
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.visit_publish_comment_activity);
        
        // obtener datos de la visita a partir del id.
        String visitId = getIntent().getStringExtra (VisitsListActivity.EXTRA_VISIT_ID);
        
        // DTVisit visit = getVisit (visitId);

        TextView viewVisitId = (TextView) findViewById (R.id.textview_visit_id);
        viewVisitId.setText (visitId);
    }
    
    // funciones del programador
    
    // cliquear Send -> publicar comentario
    
    public void button_send_onClick (View view) {

    	// obtener el id de la visita y el comentario
    	int visitId = Integer.parseInt (((TextView) findViewById (R.id.textview_visit_id)).getText ().toString ());
    	String comment = ((EditText) findViewById (R.id.edittext_comment)).getText ().toString ();
    	
		// intentar visitar el sitio
		SitesController.getInstance().publishVisitComment (visitId, comment);
    	
		// mostrar confirmaci�n
		AlertDialog alertDialog = new AlertDialog.Builder (this).create ();

		alertDialog.setTitle ("Confirmaci�n");
		alertDialog.setMessage ("Enviaste el comentario \""
		+ comment
				+ "\" a la visita de id "
				+ visitId
				+ ".");
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				// cerrar la actividad.
				finish ();
			}
		});
		
		alertDialog.show();
    }
}