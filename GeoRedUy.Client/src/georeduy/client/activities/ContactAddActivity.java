// UsersListActivity

// actividad para el caso de uso Agregar contactos.
// utiliza el layout list_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.ClientsController;
import georeduy.client.lists.UsersListAdapter;
import georeduy.client.lists.UsersListAdapter.UserListMode;
import georeduy.client.model.User;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.OnCompletedCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ContactAddActivity extends Activity {
    
    // constructor

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.contact_add_activity);
    }
    
    // cliquear botón -> agregar contacto     
    public void button_user_item_add_onClick (View view) {
    	// obtener el id del usuario
    	final String userId = ((TextView) ((View) view.getParent ()).findViewById (R.id.user_id)).getText ().toString ();
    	
		// intentar agregar el contacto
		ClientsController.getInstance().AddContact(userId, new OnCompletedCallback() {
			
			@Override
			public void onCompleted(String response, String error) {
				if (error == null) {
					finish();
	        	} 
			}
		});		
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
    
    public void button_submit_onClick(View view) {
    	String query = ((TextView) findViewById (R.id.contact_search_edit)).getText ().toString ();
    	doSearch(query);
    }
    
    public void doSearch(String query) {
    	ClientsController.getInstance().SearchUsers(query, 
		        new OnCompletedCallback() {

			        @Override
			        public void onCompleted(String response, String error) {
			        	if (error == null)  {
				        	Gson gson = new Gson();
				        	Type listType = new TypeToken<ArrayList<User>>() {}.getType();				    		
				    		List<User> users = gson.fromJson(response, listType);
				    		if (users != null) {
				    			ListView listView = (ListView) findViewById (R.id.listView_list);
				    			UsersListAdapter adapter = new UsersListAdapter (ContactAddActivity.this, UserListMode.ADD_CONTACTS, users);
				    	        listView.setAdapter (adapter);
				    		}
			    		}
			        }
		        });
    }

}
