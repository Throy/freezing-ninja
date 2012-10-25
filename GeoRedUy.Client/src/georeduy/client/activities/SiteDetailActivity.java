// SiteDetailActivity

// actividad para el caso de uso Ver datos de un sitio.
// utiliza el layout site_detail_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.SessionController;
import georeduy.client.controllers.SitesController;
import georeduy.client.util.CommonUtilities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SiteDetailActivity extends Activity {
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.site_detail_activity);
        
        // obtener datos del sitio a partir del id.
        String id = getIntent().getStringExtra (SitesListActivity.EXTRA_SITE_ID);
        String address = getIntent().getStringExtra (SitesListActivity.SITE_ITEM_ADDRESS);
        String name = getIntent().getStringExtra (SitesListActivity.SITE_ITEM_NAME);
        
        //int idx = Integer.parseInt (siteId);
        // DTVisit visit = getSite (idx);

        TextView viewVisitId = (TextView) findViewById (R.id.textview_site_id);
        TextView viewName = (TextView) findViewById (R.id.textview_name);
        TextView viewAddress = (TextView) findViewById (R.id.textview_address);
        
        viewVisitId.setText (id);
        viewName.setText (name);
        viewAddress.setText (address);
    }
    
    // funciones del programador

    // cliquear botón -> visitar sitio
    
    public void button_visit_item_onClick (View view) {
    	// obtener el id del sitio
    	int siteId = Integer.parseInt (((TextView) findViewById (R.id.textview_site_id)).getText ().toString ());
    	
		// intentar visitar el sitio
		SitesController.getInstance().visitSite (siteId);
		
		// mostrar confirmación
        CommonUtilities.showAlertMessage (this, "Confirmación", "Visitaste el sitio de id " + siteId);
    }
}
