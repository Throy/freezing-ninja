// SiteDetailActivity

// actividad para el caso de uso Ver datos de un sitio.
// utiliza el layout site_detail_activity.

package georeduy.client.activities;

// imports

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
        String siteId = getIntent().getStringExtra (SitesListActivity.EXTRA_SITE_ID);
        
        //int idx = Integer.parseInt (siteId);
        // DTVisit visit = getSite (idx);

        TextView viewVisitId = (TextView) findViewById (R.id.textview_site_id);
        TextView viewName = (TextView) findViewById (R.id.textview_name);
        TextView viewAddress = (TextView) findViewById (R.id.textview_address);
        
        viewVisitId.setText (siteId);
        viewName.setText ("Sitio " + siteId);
        viewAddress.setText ("Calle " + siteId);
    }
    
    // funciones del programador

    // cliquear botón -> visitar sitio
    
    public void button_visit_item_onClick (View view) {
        CommonUtilities.showAlertMessage (this, "Confirmación", "Visitaste el sitio de id " + ((TextView) findViewById (R.id.textview_site_id)).getText ().toString ());
    }
}
