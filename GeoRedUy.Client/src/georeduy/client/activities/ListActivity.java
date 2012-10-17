package georeduy.client.activities;

import georeduy.client.lists.ListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends Activity {
	// datos de los items
	public static final String ITEM_NAME = "name";
	public static final String ITEM_DESCRIPTION = "description";
	public static final String ITEM_PRICE = "price";
	public static final String ITEM_DATE = "date";
    
    // constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.list_activity);
        
        /*
        ListItem[] itemsList = {item};
        
        // poblar lista
        ArrayAdapter adapter = new ArrayAdapter<ListItem>(this, 
                android.R.layout.simple_expandable_list_item_1, itemsList); 		// toma los myStringArray [idx].toString()
        ListView listView = (ListView) findViewById (R.id.listView_list);
        listView.setAdapter(adapter);
        */
        
        // poblar lista 2
        
        ArrayList <HashMap <String, String>> itemsList = new ArrayList <HashMap <String, String>> ();
        for (int i = 0; i < 3; i += 1) {
            // crear item
            HashMap <String, String> map = new HashMap<String, String> ();
            map.put (ITEM_NAME, "product " + i);
            map.put (ITEM_DESCRIPTION, "descript " + i);
            map.put (ITEM_PRICE, "$ " + i * 10);
            map.put (ITEM_DATE, "2012 / 10 / " + i);
 
            // adding HashList to ArrayList
            itemsList.add(map);
        }
 
        // poblar lista de items
        ListAdapter adapter = new ListAdapter (this, itemsList);
        ListView listView = (ListView) findViewById (R.id.listView_list);
        listView.setAdapter (adapter);
        
        // insertar listener
        listView.setOnItemClickListener(new OnItemClickListener() {

        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		//AlertDialog dialog = new AlertDialog (this);
        		AlertDialog alertDialog = new AlertDialog.Builder (ListActivity.this).create ();
        		alertDialog.setTitle ("Message");
        		
        		String productName = ((TextView) view.findViewById (R.id.name)).getText().toString();
        		alertDialog.setMessage ("What do you think about " + productName + "?");

        		alertDialog.setButton (DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
        			public void onClick (DialogInterface dialog, int which) {
        				// nada
                		AlertDialog alertDialog = new AlertDialog.Builder (ListActivity.this).create ();
                		alertDialog.setTitle ("Bueno, ok");
                		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
                			public void onClick (DialogInterface dialog, int which) {
                			}
                		});
                		alertDialog.show();
        			}
        		});
        		
        		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
        			public void onClick (DialogInterface dialog, int which) {
        				// nada
                		AlertDialog alertDialog = new AlertDialog.Builder (ListActivity.this).create ();
                		alertDialog.setTitle ("Malo, cancel");
                		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
                			public void onClick (DialogInterface dialog, int which) {
                			}
                		});
                		alertDialog.show();
        			}
        		});
        		alertDialog.show();
        	}
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
