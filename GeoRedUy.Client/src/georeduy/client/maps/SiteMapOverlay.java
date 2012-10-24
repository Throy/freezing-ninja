// SiteMapOverlay

// overlay del mapa para desplegar sitios.

package georeduy.client.maps;

import georeduy.client.activities.R;
import georeduy.client.activities.SiteDetailActivity;
import georeduy.client.activities.SitesListActivity;
import georeduy.client.util.CommonUtilities;

import java.util.ArrayList;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

public class SiteMapOverlay extends ItemizedOverlay <OverlayItem> {

	// items del mapa
	private ArrayList <OverlayItem> mapItems = new ArrayList <OverlayItem>();

	private Context context;

	public SiteMapOverlay (Drawable defaultMarker) {
		super(boundCenterBottom (defaultMarker));
	}

	public SiteMapOverlay (Drawable defaultMarker, Context context) {
		this(defaultMarker);
		this.context = context;
	}

	@Override
	protected OverlayItem createItem (int i) {
		return mapItems.get(i);
	}

	@Override
	public int size() {
		return mapItems.size();
	}

	// cliquear en el bot�n -> mostrar cuadro
	
	@Override
	protected boolean onTap (int index) {
		// obtener item
		MapOverlayItem item = (MapOverlayItem) mapItems.get(index);

    	// crear intent de la actividad Ver datos de un sitio.
    	Intent intent_site_detail = new Intent (context, SiteDetailActivity.class);
    	
    	// agregar id del sitio al intent
    	intent_site_detail.putExtra (SitesListActivity.EXTRA_SITE_ID, "" + item.getId ());
    	
    	// ejecutar intent.
    	context.startActivity (intent_site_detail);
		
		/*
		
		// crear cuadro de di�logo
		AlertDialog dialog = new AlertDialog.Builder (context).create ();
		
		// mostrar t�tulo y descripci�n del sitio.
		dialog.setTitle (item.getTitle());
		dialog.setMessage (item.getSnippet());
		
		// mostrar botones
		dialog.setButton (DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				CommonUtilities.showAlertMessage (context, "Confirmaci�n", "Agregaste la visita.");
				dialog.cancel();
			}
		});
		
		dialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				CommonUtilities.showAlertMessage (context, "Cancelaci�n", "Descartaste la visita.");
				dialog.cancel();
			}
		});
		
		dialog.show();
		*/

        //CommonUtilities.showAlertMessage (context, item.getTitle(), item.getSnippet());
		
		return true;
	}

	public void addOverlay (OverlayItem overlay) {
		mapItems.add (overlay);
		this.populate();
	}

}