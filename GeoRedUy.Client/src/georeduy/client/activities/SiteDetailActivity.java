// SiteDetailActivity

// actividad para el caso de uso Ver datos de un sitio.
// utiliza el layout site_detail_activity.

package georeduy.client.activities;

// imports

import com.google.gson.Gson;

import georeduy.client.controllers.SessionController;
import georeduy.client.controllers.SitesController;
import georeduy.client.model.Site;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.OnCompletedCallback;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SiteDetailActivity extends Activity {
	
	// atributos
	
	private static String siteId;
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.site_detail_activity);
        
        // obtener datos del sitio a partir del id.
        siteId = getIntent().getStringExtra (SitesListActivity.EXTRA_SITE_ID);
        
        Site site = SitesController.getInstance ().getSite (siteId);
        
        // *** en realidad este párrafo no va ***
        String name = getIntent().getStringExtra (SitesListActivity.SITE_ITEM_NAME);
        String address = getIntent().getStringExtra (SitesListActivity.SITE_ITEM_ADDRESS);

        // msotrar datos en el menú
        TextView viewVisitId = (TextView) findViewById (R.id.textview_site_id);
        TextView viewName = (TextView) findViewById (R.id.textview_name);
        TextView viewDescription = (TextView) findViewById (R.id.textview_description);
        TextView viewAddress = (TextView) findViewById (R.id.textview_address);
        
        /// *** en realidad debería tomar los datos del objeto site. ***
        viewVisitId.setText (siteId);
        viewDescription.setText ("Es un lugar " + siteId);
        viewName.setText (name);
        viewAddress.setText (address);
    }
    
    // funciones del programador

    // cliquear botón -> visitar sitio
    
    public void button_visit_item_onClick (View view) {
    	// obtener el id del sitio
    	String siteId = ((TextView) findViewById (R.id.textview_site_id)).getText ().toString ();
    	
		// intentar visitar el sitio
		SitesController.getInstance().visitSite (siteId, new OnCompletedCallback() {

			@Override
			public void onCompleted (String response, String error)
			{
				// TODO Auto-generated method stub
				if (error == null) {
					// mostrar confirmación
			        CommonUtilities.showAlertMessage (SiteDetailActivity.this, "Confirmación", "Visitaste el sitio de id " + SiteDetailActivity.siteId);
				}
				
				else {
					CommonUtilities.showAlertMessage (SiteDetailActivity.this, "Error SDA bvi", "Huboun error:\n" + error);
				}
				
				
				// *** si devolviera algo ***
				/*
		        Gson gson = new Gson();
		        gson.fromJson (response, ClaseRetorno.class)
		        */
			}});
		
    }
}
