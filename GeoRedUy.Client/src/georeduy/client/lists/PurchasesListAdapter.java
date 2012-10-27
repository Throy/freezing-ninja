// PurchasesListAdapter

// adaptador para lista de compras.
// lo usan las actividades con lista de compras.

package georeduy.client.lists;

// imports

import georeduy.client.activities.PurchasesListActivity;
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

public class PurchasesListAdapter extends BaseAdapter {
	
	// atributos

    private Activity _activity;
    private ArrayList <HashMap <String, String>> _itemsString;
    private ArrayList <HashMap <String, Integer>> _itemsInt;
    private static LayoutInflater _inflater = null;
    //public ImageLoader imageLoader;
    
    // constructor
 
    public PurchasesListAdapter (Activity activity,
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
            vi = _inflater.inflate (R.layout.purchases_list_item, null);
        }
 
        // initialize views
        TextView viewPurchaseId = (TextView) vi.findViewById (R.id.purchase_id);
        TextView viewName = (TextView) vi.findViewById (R.id.name);
        TextView viewPrice = (TextView) vi.findViewById (R.id.price);
        TextView viewDate = (TextView) vi.findViewById (R.id.date);
        //ImageView thumb_image=(ImageView) vi.findViewById (R.id.list_image);
 
        // get item string
        HashMap <String, String> itemString = new HashMap <String, String>();
        itemString = _itemsString.get (position);
 
        // set values of the views
        viewName.setText (itemString.get (PurchasesListActivity.PURCHASE_ITEM_NAME));
        viewPrice.setText (itemString.get (PurchasesListActivity.PURCHASE_ITEM_PRICE));
        viewDate.setText (itemString.get (PurchasesListActivity.PURCHASE_ITEM_DATE));
        //imageLoader.DisplayImage(item.get (PurchasesListActivity.PURCHASE_ITEM_THUMB), thumb_image);

        // get item int
        HashMap <String, Integer> itemInt = new HashMap <String, Integer>();
        itemInt = _itemsInt.get (position);
 
        // set values of the views
        // *** setTag para guardar cualquier objeto ***
        viewPurchaseId.setText (itemInt.get (PurchasesListActivity.PURCHASE_ITEM_ID).toString ());
        
        // return view
        return vi;
    }
}
