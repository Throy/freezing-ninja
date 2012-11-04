// UsersListActivity

// actividad para el caso de uso Agregar contactos.
// utiliza el layout list_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.ClientsController;
import georeduy.client.lists.UsersListAdapter;
import georeduy.client.lists.UsersListAdapter.UserListMode;
import georeduy.client.model.Contact;
import georeduy.client.model.User;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.OnCompletedCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ContactListActivity extends Activity {
	
	// datos de los items
	
    public static final String USER_ITEM_ID = "tsi2.GeoRedDemo.user_id";
    public static final String USER_ITEM_FULLNAME = "tsi2.GeoRedDemo.user_name";
    public static final String USER_ITEM_USERNAME = "tsi2.GeoRedDemo.user_address";
    
    // extras de intents

    public static final String EXTRA_USER_ID = "georeduy.client.user_id";
    
    public static final String EXTRA_USER_NAME = "georeduy.client.user_name";
    
    // constructor

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.list_activity);
        
        final ListView listView = (ListView) findViewById (R.id.listView_list);
 
        refreshList();
        // poblar lista de items
        
        
        // cliquear línea -> iniciar actividad de Ver datos de un usuario
        listView.setOnItemClickListener (new OnItemClickListener() {

        	public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
            	// agregar id del usuario al intent
            	String userId = ((TextView) view.findViewById (R.id.user_id)).getText().toString();
            	if (userId != null && !userId.equals("-1")) {
            		String userName = ((TextView) view.findViewById (R.id.username)).getText().toString();
            		// crear intent de la actividad Ver datos de un usuario.
            		Intent intent_user_detail = new Intent (parent.getContext (), ChatActivity.class);
	            	intent_user_detail.putExtra (EXTRA_USER_ID, userId);	 
	            	intent_user_detail.putExtra (EXTRA_USER_NAME, userName);
	            	// ejecutar intent.
	            	startActivity (intent_user_detail);
            	}
        	}
        });

    }
    
    // cliquear botón -> quitar contacto 
    
    public void button_user_item_rem_onClick (View view) {
    	// obtener el id del usuario
    	String userId = ((TextView) ((View) view.getParent ()).findViewById (R.id.user_id)).getText ().toString ();

    	ClientsController.getInstance().removeContact(userId, new OnCompletedCallback() {
			
			@Override
			public void onCompleted(String response, String error) {
				if (error == null) {
					refreshList();
	        	}
			}
		});	
    }    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contacts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.options_add_contact:
            	Intent myIntent = new Intent(this, ContactAddActivity.class);
        		startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void refreshList() {
    	ClientsController.getInstance().GetContacts(new OnCompletedCallback() {
			
			@Override
			public void onCompleted(String response, String error) {
				if (error == null) {
					Gson gson = new Gson();
		        	Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();				    		
		    		List<Contact> contacts = gson.fromJson(response, listType);
		    		if (contacts != null) {
		    			ListView listView = (ListView) findViewById (R.id.listView_list);
		    			UsersListAdapter adapter = new UsersListAdapter (ContactListActivity.this, contacts, UserListMode.LIST_CONTACTS);
		    	        listView.setAdapter (adapter);
		    		}
				}
			}
		});
    }

}
