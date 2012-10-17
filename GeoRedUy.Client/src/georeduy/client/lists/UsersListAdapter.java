// UsersListAdapter

// adaptador para lista de usuarios.
// lo usan las actividades con lista de usuarios.

package georeduy.client.lists;

// imports

import georeduy.client.activities.R;
import georeduy.client.activities.UsersListActivity;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	// atributos

    private Activity _activity;
    private ArrayList <HashMap <String, String>> _itemsString;
    private ArrayList <HashMap <String, Integer>> _itemsInt;
    private static LayoutInflater _inflater = null;
    //public ImageLoader imageLoader;
    
    // constructor
 
    public UsersListAdapter (Activity activity,
    		ArrayList <HashMap <String, String>> itemsString,
    		ArrayList <HashMap <String, Integer>> itemsInt) {
        _activity = activity;
        _itemsString = itemsString;
        _itemsInt = itemsInt;
        _inflater = (LayoutInflater) _activity.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return _itemsString.size();
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
        //ImageView thumb_image=(ImageView) vi.findViewById (R.id.list_image);
 
        // get item string
        HashMap <String, String> itemString = new HashMap <String, String>();
        itemString = _itemsString.get (position);
 
        // set values of the views
        username.setText (itemString.get (UsersListActivity.USER_ITEM_USERNAME));
        fullname.setText (itemString.get (UsersListActivity.USER_ITEM_FULLNAME));
        //imageLoader.DisplayImage(item.get (ListActivity.ITEM_THUMB), thumb_image);

        // get item int
        HashMap <String, Integer> itemInt = new HashMap <String, Integer>();
        itemInt = _itemsInt.get (position);
 
        // set values of the views
        // *** setTag para guardar cualquier objeto ***
        userId.setText (itemInt.get (UsersListActivity.USER_ITEM_ID).toString ());
        
        // set button visibilities
        Button buttonAdd = (Button) vi.findViewById (R.id.button_user_item_add);
        Button buttonRem = (Button) vi.findViewById (R.id.button_user_item_rem);
        
        if (position % 2 == 0) {
        	buttonAdd.setVisibility (View.VISIBLE);
            buttonRem.setVisibility (View.INVISIBLE);
        }
        else{
        	buttonAdd.setVisibility (View.INVISIBLE);
            buttonRem.setVisibility (View.VISIBLE);
        }
        
        // return view
        return vi;
    }
}
