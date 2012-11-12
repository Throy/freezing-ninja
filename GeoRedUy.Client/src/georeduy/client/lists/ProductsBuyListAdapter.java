// ProductsBuyListAdapter

// adaptador para lista de productos.
// lo usa la actividad Comprar productos.

package georeduy.client.lists;

// imports

import georeduy.client.activities.ProductsBuyListActivity;
import georeduy.client.activities.ProductsListActivity;
import georeduy.client.activities.R;
import georeduy.client.controllers.ProductsController;
import georeduy.client.edittexts.ProductBuyList_TextUnitsWatcher;
import georeduy.client.util.CommonUtilities;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import android.widget.ImageView;

// fuente: http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/

public class ProductsBuyListAdapter extends BaseAdapter {
	
	// atributos

    private Activity _activity;
    private ArrayList <HashMap <String, String>> _itemsString;
    private ArrayList <HashMap <String, Integer>> _itemsInt;
    private static LayoutInflater _inflater = null;
    //public ImageLoader imageLoader;
    
    // constructor
 
    public ProductsBuyListAdapter (Activity activity,
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
            vi = _inflater.inflate (R.layout.products_buy_list_item, null);
        }
 
        // initialize views
        TextView viewProductId = (TextView) vi.findViewById (R.id.product_id);
        TextView viewName = (TextView) vi.findViewById (R.id.name);
        TextView viewPriceUnit = (TextView) vi.findViewById (R.id.priceunit);
        TextView viewPrice = (TextView) vi.findViewById (R.id.price);
        EditText viewUnits = (EditText) vi.findViewById (R.id.units);
        //ImageView thumb_image=(ImageView) vi.findViewById (R.id.list_image);
 
        // get item string
        HashMap <String, String> itemString = new HashMap <String, String>();
        itemString = _itemsString.get (position);
 
        // set values of the views
        String productId = itemString.get (ProductsListActivity.PRODUCT_ITEM_ID);
        viewName.setText (itemString.get (ProductsListActivity.PRODUCT_ITEM_NAME));
        viewPrice.setText (CommonUtilities.stringToPrice (itemString.get (ProductsListActivity.PRODUCT_ITEM_PRICE)));
        viewPriceUnit.setText (itemString.get (ProductsListActivity.PRODUCT_ITEM_PRICE).toString ());
        //imageLoader.DisplayImage(item.get (ProductsListActivity.ITEM_THUMB), thumb_image);

        // get item int
        HashMap <String, Integer> itemInt = new HashMap <String, Integer>();
        itemInt = _itemsInt.get (position);
 
        // set values of the views
        // *** setTag para guardar cualquier objeto ***
        viewProductId.setText (productId);
        viewUnits.setText (itemInt.get (ProductsListActivity.PRODUCT_ITEM_UNITS).toString ());

		// agregar listener al campo viewUnits para actualizar el precio total.
		viewUnits.addTextChangedListener (new ProductBuyList_TextUnitsWatcher (productId, viewUnits));
        
        // return view
        return vi;
    }
}
