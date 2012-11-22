// UsersListAdapter

// adaptador para lista de usuarios.
// lo usan las actividades con lista de usuarios.

package georeduy.client.lists;

// imports

import georeduy.client.activities.R;
import georeduy.client.activities.ContactListActivity;
import georeduy.client.model.Contact;
import georeduy.client.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.ImageView;

// fuente: http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/

public class UsersListAdapter extends BaseAdapter {
	
	public enum UserListMode {
		ADD_CONTACTS,
		LIST_CONTACTS
	};
	
	// atributos

    private Activity _activity;
    private List<User> _users = new ArrayList<User>();
    private static LayoutInflater _inflater = null;
    private UserListMode _mode;
    //public ImageLoader imageLoader;
    
    // constructor
 
    public UsersListAdapter (Activity activity, UserListMode mode, List<User> users) {
        _activity = activity;
        _users = users;
        _mode = mode;
        _inflater = (LayoutInflater) _activity.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }
    
    public UsersListAdapter (Activity activity, List<Contact> contacts, UserListMode mode) {
        _activity = activity;
        if (contacts != null) {
	        for (Contact contact : contacts) {
	        	_users.add(contact.getContactUser());
	        }
        }
        _mode = mode;
        _inflater = (LayoutInflater) _activity.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return (_users.size() > 0) ? _users.size() : 1;
    }
 
    public Object getItem (int position) {
        return position;
    }
 
    public long getItemId (int position) {
        return position;
    }
 
    public View getView (int position, View convertView, ViewGroup parent) {
    	// initialize view
        View vi = convertView;
        if (convertView == null) {
            vi = _inflater.inflate (R.layout.users_list_item, null);
        }
        
     // initialize views
        TextView userId = (TextView) vi.findViewById (R.id.user_id);
        TextView username = (TextView) vi.findViewById (R.id.username);
        TextView fullname = (TextView) vi.findViewById (R.id.fullname);
        
        if (_users.size() == 0) {
        	switch (_mode) {
		        case ADD_CONTACTS:
		        	username.setText ("No contacts found.");
		        	break;
		        case LIST_CONTACTS:
		        	username.setText ("Contact list empty.");
		        	break;
        	}
        	
        	Button buttonAdd = (Button) vi.findViewById (R.id.button_user_item_add);
            Button buttonRem = (Button) vi.findViewById (R.id.button_user_item_rem);
            
        	buttonAdd.setVisibility (View.INVISIBLE);
            buttonRem.setVisibility (View.INVISIBLE);
            
            userId.setText ("-1");
    	            
        	return vi;
        }
 
        
        //ImageView thumb_image=(ImageView) vi.findViewById (R.id.list_image);
 
        User user = _users.get(position);
        
        // set values of the views
        username.setText (user.getUserName());
        fullname.setText (user.getUserData().getName() + " "+ user.getUserData().getLastName());
        //imageLoader.DisplayImage(item.get (ListActivity.ITEM_THUMB), thumb_image);
 
        // set values of the views
        userId.setText (user.getId());
        
        // set button visibilities
        Button buttonAdd = (Button) vi.findViewById (R.id.button_user_item_add);
        Button buttonRem = (Button) vi.findViewById (R.id.button_user_item_rem);
        
        switch (_mode) {
	        case ADD_CONTACTS:
	        	buttonAdd.setVisibility (View.VISIBLE);
	            buttonRem.setVisibility (View.INVISIBLE);
	        	break;
	        case LIST_CONTACTS:
	        	buttonAdd.setVisibility (View.INVISIBLE);
	            buttonRem.setVisibility (View.VISIBLE);
	        	break;
        }
        
        // return view
        return vi;
    }
}
