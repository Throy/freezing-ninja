package georeduy.client.lists;

import georeduy.client.activities.ListActivity;
import georeduy.client.activities.R;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;


// fuente: http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/

public class ListAdapter extends BaseAdapter {

    private Activity _activity;
    private ArrayList <HashMap <String, String>> _items;
    private static LayoutInflater _inflater = null;
    //public ImageLoader imageLoader;
 
    public ListAdapter (Activity activity, ArrayList <HashMap <String, String>> items) {
        _activity = activity;
        _items = items;
        _inflater = (LayoutInflater) _activity.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return _items.size();
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
            vi = _inflater.inflate (R.layout._list_item, null);
        }
 
        // initialize views
        TextView name = (TextView) vi.findViewById (R.id.name);
        TextView description = (TextView) vi.findViewById (R.id.description);
        TextView price = (TextView) vi.findViewById (R.id.price);
        TextView date = (TextView) vi.findViewById (R.id.date);
        //ImageView thumb_image=(ImageView) vi.findViewById (R.id.list_image);
 
        // get item
        HashMap <String, String> item = new HashMap <String, String>();
        item = _items.get (position);
 
        // set values of the views
        name.setText (item.get (ListActivity.ITEM_NAME));
        description.setText (item.get (ListActivity.ITEM_DESCRIPTION));
        price.setText (item.get (ListActivity.ITEM_PRICE));
        date.setText (item.get (ListActivity.ITEM_DATE));
        //imageLoader.DisplayImage(item.get (ListActivity.ITEM_THUMB), thumb_image);
        
        // return view
        return vi;
    }
}
