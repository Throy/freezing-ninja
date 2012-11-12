// ConfigureNotificationsTagsActivity

// actividad para el caso de uso Configurar etiquetas de notificaiones.
// utiliza el layout configure_notifications_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.NotificationsController;
import georeduy.client.lists.NotificationsTagsListAdapter;
import georeduy.client.model.Tag;
import georeduy.client.model.UserNotificationTag;
import georeduy.client.model.UserNotificationsTypes;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.OnCompletedCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    
    // lista de notitipos
    
    private static List <Tag> tags;
    
    // constructor

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.configure_notifications_activity);

        tags = null;
        NotificationsController.getInstance ().getNotificationsTagsConfiguration (new OnCompletedCallback() {
			
			@Override
			public void onCompleted (String response, String error)
			{
				if (error == null) {

					// obtener notitags
			        Gson gson = new Gson();
		        	Type listType = new TypeToken <ArrayList <Tag>>() {}.getType();
					tags = gson.fromJson (response, listType);
			        
			        // inicializar hashtags
			        
			        ArrayList <HashMap <String, String>> itemsStringList = new ArrayList <HashMap <String, String>> ();
			        ArrayList <HashMap <String, Integer>> itemsIntList = new ArrayList <HashMap <String, Integer>> ();
			        ArrayList <Boolean> itemsIsCheckedList = new ArrayList <Boolean> ();

			        for (Tag tag : tags) {
			            // crear item
			            HashMap <String, String> itemStringMap = new HashMap <String, String> ();
			            itemStringMap.put (NOTITAG_ITEM_ID, tag.getId ());
			            itemStringMap.put (NOTITAG_ITEM_NAME, tag.getName ());
			            itemStringMap.put (NOTITAG_ITEM_DESCRIPTION, tag.getDescription ());
			 
			            // adding HashList to ArrayList
			            itemsStringList.add (itemStringMap);
		
			            // crear item
			            HashMap <String, Integer> itemIntMap = new HashMap <String, Integer> ();
			 
			            // adding HashList to ArrayList
			            itemsIntList.add (itemIntMap);
			            
			            // asignar valor de marcado.
			            itemsIsCheckedList.add (tag.isChecked ());
			        }
			        
			        // poblar lista de notiquetas
			        adapter = new NotificationsTagsListAdapter (ConfigureNotificationsTagsActivity.this, itemsStringList, itemsIntList, itemsIsCheckedList);
			        ListView listView = (ListView) findViewById (R.id.listView_list);
			        listView.setAdapter (adapter);
			        
			        // cliquear línea -> iniciar actividad de Ver datos
			        listView.setOnItemClickListener (new OnItemClickListener() {
		
			        	public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
			        		// cambiar configuración de la etiqueta de notificación
		
					        //ListView listView = (ListView) findViewById (R.id.listView_list);
			            	boolean newIsChecked = ! adapter.isChecked (position);
					        adapter.setChecked (position, newIsChecked);
			            	((CheckBox) view.findViewById (R.id.checkbox)).setChecked (newIsChecked);
			        	}
			        });
            	}
			
				else {
					CommonUtilities.showAlertMessage (ConfigureNotificationsTagsActivity.this, "Error CNTags onCr", "Hubo un error:\n" + error);
				}
			}
		});

    }

    // cliquear Guardar -> confirmar cambios. 
    
    public void button_save_onClick (View view) {
    	// confirmar cambios en la configuración
    	final AlertDialog alertDialog = new AlertDialog.Builder (this).create ();

		alertDialog.setTitle ("Confirmar cambios");
		alertDialog.setMessage ("¿Seguro que querés confirmar los cambios en la configuración?");
		
		// cancelar la compra
		alertDialog.setButton (DialogInterface.BUTTON_POSITIVE, "Sí", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
				
				// generar notiTags
		        
		        List <Tag> notitags = new ArrayList <Tag>();
		        for (int idx = 0; idx < adapter.getSize (); idx += 1) {
		        	if (adapter.isChecked (idx)) {
		        		Tag tag = new Tag();
		        		tag.setId (adapter.getId (idx));
		        		notitags.add(tag);
		        	}
		        }

				NotificationsController.getInstance ().setNotificationsTagsConfiguration (notitags, new OnCompletedCallback() {
					
					@Override
					public void onCompleted (String response, String error)
					{
						// todo bien, cerrar menú
						if (error == null) {
							finish ();
						}
						
						else {
							CommonUtilities.showAlertMessage (ConfigureNotificationsTagsActivity.this, "Error CNTags bso", "Hubo un error:\n" + error);
							//finish();
						}
					}});
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
