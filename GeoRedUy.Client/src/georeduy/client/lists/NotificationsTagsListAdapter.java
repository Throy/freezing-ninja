// NotificationsTagsListAdapter

// adaptador para lista de etiquetas de notificaciones.
// lo usa la actividad ConfigureNotificationTagsActivity.

package georeduy.client.lists;

// imports

import georeduy.client.activities.ConfigureNotificationsTagsActivity;
import georeduy.client.activities.R;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

// fuente: http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/

public class NotificationsTagsListAdapter extends BaseAdapter {
	
	// atributos

	// actividad que utiliza el adaptador
    private Activity _activity;
    
    // datos string de los elementos de la lsita
    private ArrayList <HashMap <String, String>> _itemsString;

    // datos int de los elementos de la lsita
    private ArrayList <HashMap <String, Integer>> _itemsInt;
    
    // estado de cliqueado los elementos de la lsita
    private ArrayList <Boolean> _itemsIsChecked;
    
    private static LayoutInflater _inflater = null;
    
    // constructor
 
    public NotificationsTagsListAdapter (Activity activity,
    		ArrayList <HashMap <String, String>> itemsString,
    		ArrayList <HashMap <String, Integer>> itemsInt,
    		ArrayList <Boolean> itemsIsChecked) {
        _activity = activity;
        _itemsString = itemsString;
        _itemsInt = itemsInt;
        _itemsIsChecked = itemsIsChecked;
        _inflater = (LayoutInflater) _activity.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
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
            vi = _inflater.inflate (R.layout.notitags_list_item, null);
        }
 
        // initialize views
        TextView viewNotitagId = (TextView) vi.findViewById (R.id.notitag_id);
        TextView viewName = (TextView) vi.findViewById (R.id.name);
        TextView viewDescription = (TextView) vi.findViewById (R.id.description);
        CheckBox checkbox = (CheckBox) vi.findViewById (R.id.checkbox);
 
        // get item string
        HashMap <String, String> itemString = _itemsString.get (position);
 
        // set values of the views
        viewNotitagId.setText (itemString.get (ConfigureNotificationsTagsActivity.NOTITAG_ITEM_ID));
        viewName.setText (itemString.get (ConfigureNotificationsTagsActivity.NOTITAG_ITEM_NAME));
        viewDescription.setText (itemString.get (ConfigureNotificationsTagsActivity.NOTITAG_ITEM_DESCRIPTION));
        
        // get item int
        HashMap <String, Integer> itemInt = _itemsInt.get (position);
 
        // set values of the views
        // *** setTag para guardar cualquier objeto ***
 
        // set value of the checkbox
        checkbox.setChecked (_itemsIsChecked.get (position));
        
        // return view
        return vi;
    }
    
    // asigna el valor de marcado del ítem.
    
    public void setChecked (int position, boolean isChecked) {
    	_itemsIsChecked.set (position, isChecked);
    }
    
    // devuelve el valor de marcado del ítem.
    
    public boolean isChecked (int position) {
        return _itemsIsChecked.get (position);
    }
}
