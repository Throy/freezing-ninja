// UsersListActivity

// actividad para el caso de uso Agregar contactos.
// utiliza el layout list_activity.

package georeduy.client.activities;

// imports

import georeduy.client.lists.UsersListAdapter;

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

public class UsersListActivity extends Activity {
	
	// datos de los items
	
    public static final String USER_ITEM_ID = "tsi2.GeoRedDemo.user_id";
    public static final String USER_ITEM_FULLNAME = "tsi2.GeoRedDemo.user_name";
    public static final String USER_ITEM_USERNAME = "tsi2.GeoRedDemo.user_address";
    
    // extras de intents

    public static final String EXTRA_USER_ID = "tsi2.GeoRedDemo.user_id";
    
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
            
            // inventar nombre
            Character aLower = 'a';
            Character aUpper = 'A';
            int aLowerValue = aLower + idx;
            int aUpperValue = aUpper + idx;
            char charLower = (char) aLowerValue;
            char charUpper = (char) aUpperValue;

            itemStringMap.put (USER_ITEM_USERNAME, "" + charUpper + charLower + charLower);
            itemStringMap.put (USER_ITEM_FULLNAME, "" + charUpper + charLower + " " + charUpper + charLower);
 
            // adding HashList to ArrayList
            itemsStringList.add (itemStringMap);

            // crear item
            HashMap <String, Integer> itemIntMap = new HashMap <String, Integer> ();
            itemIntMap.put (USER_ITEM_ID, idx);
 
            // adding HashList to ArrayList
            itemsIntList.add (itemIntMap);
        }
 
        // poblar lista de items
        UsersListAdapter adapter = new UsersListAdapter (this, itemsStringList, itemsIntList);
        ListView listView = (ListView) findViewById (R.id.listView_list);
        listView.setAdapter (adapter);
        
        // cliquear línea -> iniciar actividad de Ver datos de un usuario
        listView.setOnItemClickListener (new OnItemClickListener() {

        	public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
            	// crear intent de la actividad Ver datos de un usuario.
            	Intent intent_user_detail = new Intent (parent.getContext (), UserDetailActivity.class);
            	
            	// agregar id del usuario al intent
            	String userId = ((TextView) view.findViewById (R.id.user_id)).getText().toString();
            	intent_user_detail.putExtra (EXTRA_USER_ID, userId);
            	
            	// ejecutar intent.
            	startActivity (intent_user_detail);
        	}
        });

    }
    
    // cliquear botón -> agregar contacto 
    
    public void button_user_item_add_onClick (View view) {
		// nada
		AlertDialog alertDialog = new AlertDialog.Builder (UsersListActivity.this).create ();
		
		alertDialog.setTitle ("Cliqueaste en agregar id " + ((TextView) ((View) view.getParent ()).findViewById (R.id.user_id)).getText ().toString ());
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
			}
		});
		alertDialog.show();
    }
    
    // cliquear botón -> quitar contacto 
    
    public void button_user_item_rem_onClick (View view) {
		// nada
		AlertDialog alertDialog = new AlertDialog.Builder (UsersListActivity.this).create ();
		
		alertDialog.setTitle ("Cliqueaste en quitar id " + ((TextView) ((View) view.getParent ()).findViewById (R.id.user_id)).getText ().toString ());
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
			}
		});
		alertDialog.show();
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
