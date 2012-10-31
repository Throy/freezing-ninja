// StoresListAdapter

// adaptador para lista de locales.
// lo usan las actividades con lista de locales.

package georeduy.client.lists;

// imports

import georeduy.client.activities.R;
import georeduy.client.activities.StoresListActivity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
//import android.widget.ImageView;

// fuente: http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/

public class StoresListAdapter extends BaseAdapter {
	
	// atributos

    private Activity _activity;
    private ArrayList <HashMap <String, String>> _itemsString;
    private ArrayList <HashMap <String, Integer>> _itemsInt;
    private static LayoutInflater _inflater = null;
    //public ImageLoader imageLoader;
    
    // constructor
 
    public StoresListAdapter (Activity activity,
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
            vi = _inflater.inflate (R.layout.stores_list_item, null);
        }
 
        // initialize views
        TextView storeId = (TextView) vi.findViewById (R.id.store_id);
        TextView name = (TextView) vi.findViewById (R.id.name);
        TextView description = (TextView) vi.findViewById (R.id.description);
        TextView address = (TextView) vi.findViewById (R.id.address);
        //ImageView thumb_image=(ImageView) vi.findViewById (R.id.list_image);
 
        // get item string
        HashMap <String, String> itemString = new HashMap <String, String>();
        itemString = _itemsString.get (position);
 
        // set values of the views
        storeId.setText (itemString.get (StoresListActivity.STORE_ITEM_ID));
        name.setText (itemString.get (StoresListActivity.STORE_ITEM_NAME));
        description.setText (itemString.get (StoresListActivity.STORE_ITEM_DESCRIPTION));
        address.setText (itemString.get (StoresListActivity.STORE_ITEM_ADDRESS));
        //imageLoader.DisplayImage(item.get (ListActivity.ITEM_THUMB), thumb_image);

        // get item int
        HashMap <String, Integer> itemInt = new HashMap <String, Integer>();
        itemInt = _itemsInt.get (position);
 
        // set values of the views
        // *** setTag para guardar cualquier objeto ***
        
        // return view
        return vi;
    }
}
