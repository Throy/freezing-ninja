// SitesListActivity

// actividad para el caso de uso Listar sitios.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.SitesController;
import georeduy.client.lists.SitesListAdapter;
import georeduy.client.model.Site;
import georeduy.client.model.Visit;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.OnCompletedCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SitesListActivity extends Activity {
	
	// datos de los items
	
    public static final String SITE_ITEM_ID = "georeduy.client.activities.site_id";
    public static final String SITE_ITEM_NAME = "georeduy.client.activities.site_name";
    public static final String SITE_ITEM_DESCRIPTION = "georeduy.client.activities.site_description";
    public static final String SITE_ITEM_ADDRESS = "georeduy.client.activities.site_address";
    
    // extras de intents

    public static final String EXTRA_SITE_ID = "georeduy.client.activities.site_id";
    
    // sitios
    public List <Site> sites;
    
    // constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.list_activity);

        // poblar lista

        SitesController.getInstance().getSitesByPosition (MapaActivity.latitudCurrent, MapaActivity.longitudCurrent, new OnCompletedCallback() {
			
			@Override
			public void onCompleted (String response, String error)
			{
				if (error == null) {

					// obtener sitios
			        Gson gson = new Gson();
		        	Type listType = new TypeToken <ArrayList <Site>>() {}.getType();
		    		sites = gson.fromJson (response, listType);
		            
		            ArrayList <HashMap <String, String>> itemsStringList = new ArrayList <HashMap <String, String>> ();
		            ArrayList <HashMap <String, Integer>> itemsIntList = new ArrayList <HashMap <String, Integer>> ();

		    		if (sites != null) {
			            for (Site site : sites) {
			                // crear item
			                HashMap <String, String> itemStringMap = new HashMap <String, String> ();
			                itemStringMap.put (SITE_ITEM_ID, site.getId ());
			                itemStringMap.put (SITE_ITEM_NAME, site.getName ());
			                itemStringMap.put (SITE_ITEM_DESCRIPTION, site.getDescription ());
			                itemStringMap.put (SITE_ITEM_ADDRESS, site.getAddress ());
			     
			                // adding HashList to ArrayList
			                itemsStringList.add (itemStringMap);
	
			                // crear item
			                HashMap <String, Integer> itemIntMap = new HashMap <String, Integer> ();
			     
			                // adding HashList to ArrayList
			                itemsIntList.add (itemIntMap);
			            }
		    		}
		     
		            // poblar lista de items
		            SitesListAdapter adapter = new SitesListAdapter (SitesListActivity.this, itemsStringList, itemsIntList);
		            ListView listView = (ListView) findViewById (R.id.listView_list);
		            listView.setAdapter (adapter);
		            
		            // cliquear línea -> iniciar actividad de Ver datos
		            listView.setOnItemClickListener (new OnItemClickListener() {

		            	public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
		                	// crear intent de la actividad Ver datos de un sitio.
		                	Intent intent_site_detail = new Intent (parent.getContext (), SiteDetailActivity.class);
		                	
		                	// agregar id del sitio al intent
		                	String siteId = ((TextView) view.findViewById (R.id.site_id)).getText().toString();
		                	intent_site_detail.putExtra (EXTRA_SITE_ID, siteId);
		                	
		                	// ejecutar intent.
		                	startActivity (intent_site_detail);
		            		
		            		/*
		            		//AlertDialog dialog = new AlertDialog (this);
		            		
		            		// crear alerta
		            		AlertDialog alertDialog = new AlertDialog.Builder (VisitsListActivity.this).create ();
		            		
		            		// mensaje
		            		alertDialog.setTitle ("Message");
		            		String visitName = ((TextView) view.findViewById (R.id.name)).getText().toString();
		            		alertDialog.setMessage ("What do you think about visit " + visitName + "?");

		            		// button ok
		            		alertDialog.setButton (DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
		            			public void onClick (DialogInterface dialog, int which) {
		            				// nada
		                    		AlertDialog alertDialog = new AlertDialog.Builder (VisitsListActivity.this).create ();
		                    		alertDialog.setTitle ("Bueno, ok");
		                    		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
		                    			public void onClick (DialogInterface dialog, int which) {
		                    			}
		                    		});
		                    		alertDialog.show();
		            			}
		            		});

		            		// button cancel
		            		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
		            			public void onClick (DialogInterface dialog, int which) {
		            				// nada
		                    		AlertDialog alertDialog = new AlertDialog.Builder (VisitsListActivity.this).create ();
		                    		alertDialog.setTitle ("Malo, cancel");
		                    		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
		                    			public void onClick (DialogInterface dialog, int which) {
		                    			}
		                    		});
		                    		alertDialog.show();
		            			}
		            		});
		            		alertDialog.show();
		            		*/
		            	}
		            });
				}
				
				else {
					CommonUtilities.showAlertMessage (SitesListActivity.this, "Error SLA onCr", "Hubo un error:\n" + error);
				}
			}
		});
        
        /*
        
        // insertar listener
        listView.setOnItemClickListener (new OnItemClickListener() {

        	public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
        		//AlertDialog dialog = new AlertDialog (this);
        		
        		// crear alerta
        		AlertDialog alertDialog = new AlertDialog.Builder (SitesListActivity.this).create ();
        		
        		// mensaje
        		alertDialog.setTitle ("Message");
        		String siteName = ((TextView) view.findViewById (R.id.name)).getText().toString();
        		alertDialog.setMessage ("What do you think about visit " + siteName + "?");

        		// button ok
        		alertDialog.setButton (DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
        			public void onClick (DialogInterface dialog, int which) {
        				// nada
                		AlertDialog alertDialog = new AlertDialog.Builder (SitesListActivity.this).create ();
                		alertDialog.setTitle ("Bueno, ok");
                		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
                			public void onClick (DialogInterface dialog, int which) {
                			}
                		});
                		alertDialog.show();
        			}
        		});

        		// button cancel
        		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
        			public void onClick (DialogInterface dialog, int which) {
        				// nada
                		AlertDialog alertDialog = new AlertDialog.Builder (SitesListActivity.this).create ();
                		alertDialog.setTitle ("Malo, cancel");
                		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
                			public void onClick (DialogInterface dialog, int which) {
                			}
                		});
                		alertDialog.show();
        			}
        		});
        		alertDialog.show();
        	}
        });
        */

    }
    
    // cliquear botón -> realizar visita
    
    public void button_site_item_onClick (View view) {
    	
    	// obtener el id del sitio
    	String siteId = ((TextView) ((View) view.getParent ()).findViewById (R.id.site_id)).getText ().toString ();

    	// obtener el sitio
    	Site site = null;
    	for (Site site_idx : MapaActivity.currentSites) {
    		if (site_idx.getId ().equals (siteId)) {
    			site = site_idx;
    		}
    	}
    	
		// intentar visitar el sitio
		SitesController.getInstance().visitSite (siteId, new OnCompletedCallback() {
			
			@Override
			public void onCompleted (String response, String error)
			{
				if (error == null) {
					// mostrar confirmación
			        CommonUtilities.showAlertMessage (SitesListActivity.this, "Confirmación", "Visitaste el sitio.");
				}
				
				else {
					CommonUtilities.showAlertMessage (SitesListActivity.this, "Error SDA bvi", "Hubo un error:\n" + error);
				}
			}
		});
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        	// button home
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask (this);
                return true;
        }
        return super.onOptionsItemSelected (item);
    }

}
