// ProductsListAdapter

// adaptador para lista de productos.
// lo usan las actividades con lista de productos.

package georeduy.client.lists;

// imports

import georeduy.client.activities.ProductsListActivity;
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
//import android.widget.ImageView;

// fuente: http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/

public class ProductsListAdapter extends BaseAdapter {
	
	// atributos

    private Activity _activity;
    private ArrayList <HashMap <String, String>> _itemsString;
    private ArrayList <HashMap <String, Integer>> _itemsInt;
    private static LayoutInflater _inflater = null;
    //public ImageLoader imageLoader;
    
    // constructor
 
    public ProductsListAdapter (Activity activity,
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
            vi = _inflater.inflate (R.layout.products_list_item, null);
        }
 
        // initialize views
        TextView viewProductId = (TextView) vi.findViewById (R.id.product_id);
        TextView viewName = (TextView) vi.findViewById (R.id.name);
        TextView viewDescription = (TextView) vi.findViewById (R.id.description);
        TextView viewPrice = (TextView) vi.findViewById (R.id.price);
        TextView viewDate = (TextView) vi.findViewById (R.id.date);
        //ImageView thumb_image=(ImageView) vi.findViewById (R.id.list_image);
 
        // get item string
        HashMap <String, String> itemString = new HashMap <String, String>();
        itemString = _itemsString.get (position);
 
        // set values of the views
        viewProductId.setText (itemString.get (ProductsListActivity.PRODUCT_ITEM_ID).toString ());
        viewName.setText (itemString.get (ProductsListActivity.PRODUCT_ITEM_NAME));
        viewDescription.setText (itemString.get (ProductsListActivity.PRODUCT_ITEM_DESCRIPTION));
        viewPrice.setText ("$ " + itemString.get (ProductsListActivity.PRODUCT_ITEM_PRICE));
        viewDate.setText (itemString.get (ProductsListActivity.PRODUCT_ITEM_DATE));
        //imageLoader.DisplayImage(item.get (ProductsListActivity.ITEM_THUMB), thumb_image);

        // get item int
        HashMap <String, Integer> itemInt = new HashMap <String, Integer>();
        itemInt = _itemsInt.get (position);
 
        // set values of the views
        // *** setTag para guardar cualquier objeto ***
        
        // return view
        return vi;
    }
}
