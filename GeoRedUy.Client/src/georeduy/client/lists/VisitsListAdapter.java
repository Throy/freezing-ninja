// VisitsListAdapter

// adaptador para lista de visitas.
// lo usan las actividades con lista de visitas.

package georeduy.client.lists;

// imports

import georeduy.client.activities.R;
import georeduy.client.activities.VisitsListActivity;

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

public class VisitsListAdapter extends BaseAdapter {
	
	// atributos

    private Activity _activity;
    private ArrayList <HashMap <String, String>> _itemsString;
    private ArrayList <HashMap <String, Integer>> _itemsInt;
    private static LayoutInflater _inflater = null;
    //public ImageLoader imageLoader;
    
    // constructor
 
    public VisitsListAdapter (Activity activity,
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
            vi = _inflater.inflate (R.layout.visits_list_item, null);
        }
 
        // initialize views
        TextView visitId = (TextView) vi.findViewById (R.id.visit_id);
        TextView name = (TextView) vi.findViewById (R.id.name);
        TextView address = (TextView) vi.findViewById (R.id.address);
        TextView date = (TextView) vi.findViewById (R.id.date);
        //ImageView thumb_image=(ImageView) vi.findViewById (R.id.list_image);
 
        // get item string
        HashMap <String, String> itemString = new HashMap <String, String>();
        itemString = _itemsString.get (position);
 
        // set values of the views
        name.setText (itemString.get (VisitsListActivity.VISIT_ITEM_NAME));
        address.setText (itemString.get (VisitsListActivity.VISIT_ITEM_ADDRESS));
        date.setText (itemString.get (VisitsListActivity.VISIT_ITEM_DATE));
        //imageLoader.DisplayImage(item.get (ListActivity.ITEM_THUMB), thumb_image);

        // get item int
        HashMap <String, Integer> itemInt = new HashMap <String, Integer>();
        itemInt = _itemsInt.get (position);
 
        // set values of the views
        // *** setTag para guardar cualquier objeto ***
        visitId.setText (itemInt.get (VisitsListActivity.VISIT_ITEM_ID).toString ());
        
        // return view
        return vi;
    }
}
