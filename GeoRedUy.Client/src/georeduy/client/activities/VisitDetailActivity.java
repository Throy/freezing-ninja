// VisitDetailActivity

// actividad para el caso de uso Ver datos de una visita.
// utiliza el layout visit_detail_activity.

package georeduy.client.activities;

// imports

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import georeduy.client.controllers.SitesController;
import georeduy.client.model.Visit;
import georeduy.client.util.OnCompletedCallback;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class VisitDetailActivity extends Activity {
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.visit_detail_activity);
        
        // obtener datos de la visita a partir del id.
        String visitId = getIntent().getStringExtra (VisitsListActivity.EXTRA_VISIT_ID);

        SitesController.getInstance ().getVisit (visitId, new OnCompletedCallback() {
			
			@Override
			public void onCompleted (String response, String error)
			{
				// obtener visita
		        Gson gson = new Gson();
	    		Visit visit = gson.fromJson (response, Visit.class);
	    		
	            TextView viewVisitId = (TextView) findViewById (R.id.textview_visit_id);
	            TextView viewName = (TextView) findViewById (R.id.textview_name);
	            TextView viewAddress = (TextView) findViewById (R.id.textview_address);
	            TextView viewDate = (TextView) findViewById (R.id.textview_date);
	            
	            viewVisitId.setText (visit.getId ());
	            viewName.setText (visit.getRealSite ().getName ());
	            viewAddress.setText (visit.getRealSite ().getAddress ());
                if (visit.getDate () != null) {
                	viewDate.setText (visit.getDate ().toString ());
                }
                else {
                	viewDate.setText ("2012 / 11 / X");
                }
			}
		});

    }
    
    // funciones del programador

    // cliquear botón -> iniciar actividad de Publicar comentario 
    
    public void button_visit_item_onClick (View view) {
    	// crear intent de la actividad Publicar comentario de una visita.
    	Intent intent_visit_comment = new Intent (this, VisitPublishCommentActivity.class);
    	
    	// agregar id de la visita al intent
    	String visitId = ((TextView) findViewById (R.id.textview_visit_id)).getText().toString();
    	intent_visit_comment.putExtra (VisitsListActivity.EXTRA_VISIT_ID, visitId);
    	
    	// ejecutar intent.
    	startActivity (intent_visit_comment);
    }
}
