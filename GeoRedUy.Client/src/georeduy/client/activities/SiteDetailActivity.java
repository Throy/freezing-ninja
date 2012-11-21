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
import georeduy.client.util.Config;
import georeduy.client.util.DownloadImageTask;
import georeduy.client.util.OnCompletedCallback;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SiteDetailActivity extends Activity {
	
	// atributos
	
	private static Site site;
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.site_detail_activity);
        
        // obtener datos del sitio a partir del id.
        String siteId = getIntent().getStringExtra (SitesListActivity.EXTRA_SITE_ID);
        
		SitesController.getInstance().getSite (siteId, new OnCompletedCallback() {

			@Override
			public void onCompleted (String response, String error)
			{
				// TODO Auto-generated method stub
				if (error == null) {

					// obtener sitio
			        Gson gson = new Gson();
			        site = gson.fromJson (response, Site.class);

			        // msotrar datos en el menú
			        TextView viewVisitId = (TextView) findViewById (R.id.textview_site_id);
			        TextView viewName = (TextView) findViewById (R.id.textview_name);
			        TextView viewDescription = (TextView) findViewById (R.id.textview_description);
			        TextView viewAddress = (TextView) findViewById (R.id.textview_address);
			        ImageView viewImage = (ImageView) findViewById(R.id.siteImageView);
			        
			        /*byte[] decodedImage = Base64.decode(site.getImage(), Base64.DEFAULT);
			        Bitmap siteBitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
			        viewImage.setImageBitmap(siteBitmap);*/
			        
			        new DownloadImageTask(viewImage).execute(Config.SERVER_URL + "/Sites/GetImageById?siteId=" + site.getId());
			        
			        viewVisitId.setText (site.getId ());
			        viewDescription.setText (site.getDescription ());
			        viewName.setText (site.getName ());
			        viewAddress.setText (site.getAddress ());
			        
			        // *** falta: tags, foto ***
				}
				
				else {
					CommonUtilities.showAlertMessage (SiteDetailActivity.this, "Error SDA bvi", "Hubo un error:\n" + error);
					//finish();
				}
			}});
		
        
    }
    
    // funciones del programador

    // cliquear botón -> visitar sitio
    
    public void button_visit_item_onClick (View view) {
    	
    	// si se está demasiado lejos, mostrar aviso.
    	if (! SitesController.getInstance().visitIsAllowed (site)) {
			CommonUtilities.showAlertMessage (this, "Aviso", "Estás demasiado lejos del sitio para visitarlo.");
    	}

		// intentar visitar el sitio
    	else {
			SitesController.getInstance().visitSite (site.getId (), new OnCompletedCallback() {
	
				@Override
				public void onCompleted (String response, String error)
				{
					if (error == null) {
						// mostrar confirmación
				        CommonUtilities.showAlertMessage (SiteDetailActivity.this, "Confirmación", "Visitaste el sitio.");
					}
					
					else {
						CommonUtilities.showAlertMessage (SiteDetailActivity.this, "Error SDA bvi", "Hubo un error:\n" + error);
					}
				}});
    	}
		
    }
}
