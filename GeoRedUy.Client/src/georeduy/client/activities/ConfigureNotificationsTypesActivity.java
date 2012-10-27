// ConfigureNotificationsTypes2Activity

// actividad para el caso de uso Configurar tipos de notificaiones.
// utiliza el layout configure_notifications_types2_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.NotificationsController;
import georeduy.client.lists.NotificationsTypesListAdapter;
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

public class ConfigureNotificationsTypesActivity extends Activity {
	
	// datos de los items
	
    public static final String NOTITYPE_ITEM_ID = "tsi2.GeoRedDemo.notitype_id";
    public static final String NOTITYPE_ITEM_NAME = "tsi2.GeoRedDemo.notitype_name";
    public static final String NOTITYPE_ITEM_DESCRIPTION = "tsi2.GeoRedDemo.notitype_description";
    
    // constructor

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.configure_notifications_types_activity);
        
        // inicializar hashtags
        
        ArrayList <HashMap <String, String>> itemsStringList = new ArrayList <HashMap <String, String>> ();
        ArrayList <HashMap <String, Integer>> itemsIntList = new ArrayList <HashMap <String, Integer>> ();
        ArrayList <Boolean> itemsIsCheckedList = new ArrayList <Boolean> ();
        
        // ListArray <NotificationType> listNotitypes = NotificationsController.getInstance ().getNotificationTypes ();

        for (int idx = 0; idx < 6; idx += 1) {
            // crear item
            HashMap <String, String> itemStringMap = new HashMap <String, String> ();
            itemStringMap.put (NOTITYPE_ITEM_NAME, "Notitipo " + idx);
            itemStringMap.put (NOTITYPE_ITEM_DESCRIPTION, "Es un notitipo " + idx);
 
            // adding HashList to ArrayList
            itemsStringList.add (itemStringMap);

            // crear item
            HashMap <String, Integer> itemIntMap = new HashMap <String, Integer> ();
            itemIntMap.put (NOTITYPE_ITEM_ID, idx);
 
            // adding HashList to ArrayList
            itemsIntList.add (itemIntMap);
            
            // asignar valor de marcado.
            itemsIsCheckedList.add (idx, false);
        }
        
        // poblar lista de notitipos
        NotificationsTypesListAdapter adapter = new NotificationsTypesListAdapter (this, itemsStringList, itemsIntList, itemsIsCheckedList);
        ListView listView = (ListView) findViewById (R.id.listView_list);
        listView.setAdapter (adapter);
        
        // cliquear línea -> iniciar actividad de Ver datos
        listView.setOnItemClickListener (new OnItemClickListener() {

        	public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
        		// cambiar configuración del tipo de configuración

		        ListView listView = (ListView) findViewById (R.id.listView_list);
		        NotificationsTypesListAdapter adapter = (NotificationsTypesListAdapter) listView.getAdapter ();
            	boolean newIsChecked = ! adapter.isChecked (position);
		        adapter.setChecked (position, newIsChecked);
            	((CheckBox) view.findViewById (R.id.checkbox)).setChecked (newIsChecked);
        	}
        });
    }
    
    // cliquear Comprar -> iniciar actividad de Comprar productos. 
    
    public void button_save_onClick (View view) {
    	// confirmar cambios en la configuración
    	final AlertDialog alertDialog = new AlertDialog.Builder (this).create ();

		alertDialog.setTitle ("Confirmar cambios");
		alertDialog.setMessage ("¿Seguro que querés confirmar los cambios en la configuración?");
		
		// cancelar la compra
		alertDialog.setButton (DialogInterface.BUTTON_POSITIVE, "Sí", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				// *** LLAMAR AL CONTROLADOR ***

		        ListView listView = (ListView) findViewById (R.id.listView_list);
		        NotificationsTypesListAdapter adapter = (NotificationsTypesListAdapter) listView.getAdapter ();
		        
		        String isChecked0 = "Notitipo 0 está DES\n";
		        String isChecked1 = "Notitipo 1 está DES\n";
		        String isChecked2 = "Notitipo 2 está DES\n";
		        String isChecked3 = "Notitipo 3 está DES\n";
		        String isChecked4 = "Notitipo 4 está DES\n";
		        
		        if (adapter.isChecked (0)) {
		        	isChecked0 = "Notitipo 0 está Activado\n";
		        }
		        if (adapter.isChecked (1)) {
		        	isChecked1 = "Notitipo 1 está Activado\n";
		        }
		        if (adapter.isChecked (2)) {
		        	isChecked2 = "Notitipo 2 está Activado\n";
		        }
		        if (adapter.isChecked (3)) {
		        	isChecked3 = "Notitipo 3 está Activado\n";
		        }
		        if (adapter.isChecked (4)) {
		        	isChecked4 = "Notitipo 4 está Activado\n";
		        }
		        
		    	final AlertDialog alertDialog = new AlertDialog.Builder (ConfigureNotificationsTypesActivity.this).create ();

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
    
    // cliquear Cancelar -> salir del menú.
    
    public void button_cancel_onClick (View view) {
    	// confirmar cancelación de compra
    	final AlertDialog alertDialog = new AlertDialog.Builder (this).create ();

		alertDialog.setTitle ("Cancelar cambios");
		alertDialog.setMessage ("¿Seguro que querés cancelar los cambios en la configuración?");
		
		// cancelar la compra
		alertDialog.setButton (DialogInterface.BUTTON_POSITIVE, "Sí", new DialogInterface.OnClickListener() {
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
