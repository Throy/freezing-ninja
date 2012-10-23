// SitesListActivity

// actividad para el caso de uso Listar sitios.

package georeduy.client.activities;

// imports

import georeduy.client.lists.SitesListAdapter;
import georeduy.client.util.CommonUtilities;

import java.util.ArrayList;
import java.util.HashMap;

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
	
    public static final String SITE_ITEM_ID = "tsi2.GeoRedDemo.site_id";
    public static final String SITE_ITEM_NAME = "tsi2.GeoRedDemo.site_name";
    public static final String SITE_ITEM_DESCRIPTION = "tsi2.GeoRedDemo.site_description";
    public static final String SITE_ITEM_ADDRESS = "tsi2.GeoRedDemo.site_address";
    
    // extras de intents

    public static final String EXTRA_SITE_ID = "tsi2.GeoRedDemo.site_id";
    
    // constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.list_activity);
        
        /*
        ListItem[] itemsList = {item};
        
        // poblar lista
        ArrayAdapter adapter = new ArrayAdapter<ListItem>(this, 
                android.R.layout.simple_expandable_list_item_1, itemsList); 		// toma los myStringArray [idx].toString()
        ListView listView = (ListView) findViewById (R.id.listView_list);
        listView.setAdapter(adapter);
        */
        
        // poblar lista 2
        
        ArrayList <HashMap <String, String>> itemsStringList = new ArrayList <HashMap <String, String>> ();
        ArrayList <HashMap <String, Integer>> itemsIntList = new ArrayList <HashMap <String, Integer>> ();

        for (int idx = 0; idx < 5; idx += 1) {
            // crear item
            HashMap <String, String> itemStringMap = new HashMap <String, String> ();
            itemStringMap.put (SITE_ITEM_NAME, "Sitio " + idx);
            itemStringMap.put (SITE_ITEM_DESCRIPTION, "Es un lugar " + idx);
            itemStringMap.put (SITE_ITEM_ADDRESS, "Calle " + idx + " y " + (((idx + 5) % 8) + 6));
 
            // adding HashList to ArrayList
            itemsStringList.add (itemStringMap);

            // crear item
            HashMap <String, Integer> itemIntMap = new HashMap <String, Integer> ();
            itemIntMap.put (SITE_ITEM_ID, idx);
 
            // adding HashList to ArrayList
            itemsIntList.add (itemIntMap);
        }
 
        // poblar lista de items
        SitesListAdapter adapter = new SitesListAdapter (this, itemsStringList, itemsIntList);
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
    	/*
		// nada
		AlertDialog alertDialog = new AlertDialog.Builder (SitesListActivity.this).create ();
		
		alertDialog.setTitle ("Visitaste el sitio de id " + ((TextView) ((View) view.getParent ()).findViewById (R.id.site_id)).getText ().toString ());
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
			}
		});
		alertDialog.show();
		*/

        CommonUtilities.showAlertMessage (this, "Confirmación", "Visitaste el sitio de id " + ((TextView) ((View) view.getParent ()).findViewById (R.id.site_id)).getText ().toString ());
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
