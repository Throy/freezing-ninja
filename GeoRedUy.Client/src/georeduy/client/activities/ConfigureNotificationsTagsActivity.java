// ConfigureNotificationsTagsActivity

// actividad para el caso de uso Configurar etiquetas de notificaiones.
// utiliza el layout configure_notifications_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.NotificationsController;
import georeduy.client.lists.NotificationsTagsListAdapter;
import georeduy.client.util.CommonUtilities;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ConfigureNotificationsTagsActivity extends Activity {
	
	// datos de los items
	
    public static final String NOTITAG_ITEM_ID = "tsi2.GeoRedDemo.notitag_id";
    public static final String NOTITAG_ITEM_NAME = "tsi2.GeoRedDemo.notitag_name";
    public static final String NOTITAG_ITEM_DESCRIPTION = "tsi2.GeoRedDemo.notitag_description";
    
    // adaptador de lista de notiquetas
    
    private static NotificationsTagsListAdapter adapter;
    
    // constructor

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.configure_notifications_activity);
        
        // inicializar hashtags
        
        ArrayList <HashMap <String, String>> itemsStringList = new ArrayList <HashMap <String, String>> ();
        ArrayList <HashMap <String, Integer>> itemsIntList = new ArrayList <HashMap <String, Integer>> ();
        ArrayList <Boolean> itemsIsCheckedList = new ArrayList <Boolean> ();
        
        // ListArray <NotificationType> listNotitypes = NotificationsController.getInstance ().getNotificationTags ();

        for (int idx = 0; idx < 6; idx += 1) {
            // crear item
            HashMap <String, String> itemStringMap = new HashMap <String, String> ();
            itemStringMap.put (NOTITAG_ITEM_NAME, "Notiqueta " + idx);
            itemStringMap.put (NOTITAG_ITEM_DESCRIPTION, "Es una notiqueta " + idx);
 
            // adding HashList to ArrayList
            itemsStringList.add (itemStringMap);

            // crear item
            HashMap <String, Integer> itemIntMap = new HashMap <String, Integer> ();
            itemIntMap.put (NOTITAG_ITEM_ID, idx);
 
            // adding HashList to ArrayList
            itemsIntList.add (itemIntMap);
            
            // asignar valor de marcado.
            itemsIsCheckedList.add (idx, false);
        }
        
        // poblar lista de notiquetas
        adapter = new NotificationsTagsListAdapter (this, itemsStringList, itemsIntList, itemsIsCheckedList);
        ListView listView = (ListView) findViewById (R.id.listView_list);
        listView.setAdapter (adapter);
        
        // cliquear l�nea -> iniciar actividad de Ver datos
        listView.setOnItemClickListener (new OnItemClickListener() {

        	public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
        		// cambiar configuraci�n de la etiqueta de notificaci�n

		        ListView listView = (ListView) findViewById (R.id.listView_list);
            	boolean newIsChecked = ! adapter.isChecked (position);
		        adapter.setChecked (position, newIsChecked);
            	((CheckBox) view.findViewById (R.id.checkbox)).setChecked (newIsChecked);
        	}
        });
    }

    // cliquear Guardar -> confirmar cambios. 
    
    public void button_save_onClick (View view) {
    	// confirmar cambios en la configuraci�n
    	final AlertDialog alertDialog = new AlertDialog.Builder (this).create ();

		alertDialog.setTitle ("Confirmar cambios");
		alertDialog.setMessage ("�Seguro que quer�s confirmar los cambios en la configuraci�n?");
		
		// cancelar la compra
		alertDialog.setButton (DialogInterface.BUTTON_POSITIVE, "S�", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				// *** LLAMAR AL CONTROLADOR ***
		        
		        String isChecked0 = "Notiqueta 0 est� DES\n";
		        String isChecked1 = "Notiqueta 1 est� DES\n";
		        String isChecked2 = "Notiqueta 2 est� DES\n";
		        String isChecked3 = "Notiqueta 3 est� DES\n";
		        String isChecked4 = "Notiqueta 4 est� DES\n";
		        
		        if (adapter.isChecked (0)) {
		        	isChecked0 = "Notiqueta 0 est� Activado\n";
		        }
		        if (adapter.isChecked (1)) {
		        	isChecked1 = "Notiqueta 1 est� Activado\n";
		        }
		        if (adapter.isChecked (2)) {
		        	isChecked2 = "Notiqueta 2 est� Activado\n";
		        }
		        if (adapter.isChecked (3)) {
		        	isChecked3 = "Notiqueta 3 est� Activado\n";
		        }
		        if (adapter.isChecked (4)) {
		        	isChecked4 = "Notiqueta 4 est� Activado\n";
		        }
		        
		    	final AlertDialog alertDialog = new AlertDialog.Builder (ConfigureNotificationsTagsActivity.this).create ();

				alertDialog.setTitle ("Mensaje");
				alertDialog.setMessage (isChecked0 + isChecked1 + isChecked2 + isChecked3 + isChecked4);
				
				alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
					public void onClick (DialogInterface dialog, int which) {
						finish();
					}
				});
				alertDialog.show();
			}
		});
		
		// seguir comprando
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				alertDialog.cancel();
			}
		});
		alertDialog.show();
    }
    
    // cliquear Cancelar -> salir del men�.
    
    public void button_cancel_onClick (View view) {
    	// confirmar cancelaci�n de compra
    	final AlertDialog alertDialog = new AlertDialog.Builder (this).create ();

		alertDialog.setTitle ("Cancelar cambios");
		alertDialog.setMessage ("�Seguro que quer�s cancelar los cambios en la configuraci�n?");
		
		// cancelar la compra
		alertDialog.setButton (DialogInterface.BUTTON_POSITIVE, "S�", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				finish();
			}
		});
		
		// seguir comprando
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				alertDialog.cancel();
			}
		});
		alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
        	// button home
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask (this);
                return true;
        }
        return super.onOptionsItemSelected (item);
    }
}