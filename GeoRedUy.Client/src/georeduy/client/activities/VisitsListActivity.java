// VisitsListActivity

// actividad para el caso de uso Listar visitas.
// utiliza el layout list_activity.

package georeduy.client.activities;

// imports

import georeduy.client.lists.VisitsListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
//import android.app.AlertDialog;
//import android.content.DialogInterface;

public class VisitsListActivity extends Activity {
	
	// datos de los items
	
    public static final String VISIT_ITEM_ID = "tsi2.GeoRedDemo.visit_id";
    public static final String VISIT_ITEM_NAME = "tsi2.GeoRedDemo.visit_name";
    public static final String VISIT_ITEM_ADDRESS = "tsi2.GeoRedDemo.visit_address";
    public static final String VISIT_ITEM_DATE = "tsi2.GeoRedDemo.visit_date";
    
    // extras de intents

    public static final String EXTRA_VISIT_ID = "tsi2.GeoRedDemo.visit_id";
    
    // constructor

    @Override
    public void onCreate (Bundle savedInstanceState) {
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
            itemStringMap.put (VISIT_ITEM_NAME, "Sitio " + idx);
            itemStringMap.put (VISIT_ITEM_ADDRESS, "Calle " + idx + " y " + (((idx + 5) % 8) + 5));
            itemStringMap.put (VISIT_ITEM_DATE, "2012 / 10 / " + idx);
 
            // adding HashList to ArrayList
            itemsStringList.add (itemStringMap);

            // crear item
            HashMap <String, Integer> itemIntMap = new HashMap <String, Integer> ();
            itemIntMap.put (VISIT_ITEM_ID, idx);
 
            // adding HashList to ArrayList
            itemsIntList.add (itemIntMap);
        }
 
        // poblar lista de items
        VisitsListAdapter adapter = new VisitsListAdapter (this, itemsStringList, itemsIntList);
        ListView listView = (ListView) findViewById (R.id.listView_list);
        listView.setAdapter (adapter);
        
        // cliquear línea -> iniciar actividad de Ver datos
        listView.setOnItemClickListener (new OnItemClickListener() {

        	public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
        		
            	// crear intent de la actividad Ver datos de una visita.
            	Intent intent_visit_detail = new Intent (parent.getContext (), VisitDetailActivity.class);
            	
            	// agregar id de la visita al intent
            	String visitId = ((TextView) view.findViewById (R.id.visit_id)).getText().toString();
            	intent_visit_detail.putExtra (EXTRA_VISIT_ID, visitId);
            	
            	// ejecutar intent.
            	startActivity (intent_visit_detail);
        		
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
    
    // cliquear botón -> iniciar actividad de Publicar comentario 
    
    public void button_visit_item_onClick (View view) {
		// nada
    	/*
		AlertDialog alertDialog = new AlertDialog.Builder (VisitsListActivity.this).create ();
		
		alertDialog.setTitle ("Cliqueaste en el id " + ((TextView) ((View) view.getParent ()).findViewById (R.id.visit_id)).getText ().toString ());
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
			}
		});
		alertDialog.show();
		*/

    	// crear intent de la actividad Publicar comentario de una visita.
    	Intent intent_visit_comment = new Intent (this, VisitCommentActivity.class);
    	
    	// agregar id de la visita al intent
    	String visitId = ((TextView) ((View) view.getParent ()).findViewById (R.id.visit_id)).getText().toString();
    	intent_visit_comment.putExtra (EXTRA_VISIT_ID, visitId);
    	
    	// ejecutar intent.
    	startActivity (intent_visit_comment);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
        	// button home
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask (this);
                return true;
        }
        return super.onOptionsItemSelected (item);
    }

}
