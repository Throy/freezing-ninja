// ConfigureNotificationsTypesActivity

// actividad para el caso de uso Configurar tipos de notificaiones.
// utiliza el layout configure_notifications_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.NotificationsController;
import georeduy.client.lists.NotificationsTypesListAdapter;
import georeduy.client.model.Product;
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

public class ConfigureNotificationsTypesActivity extends Activity {
	// tipos de configuración de notificaciones
	
	private static final String [] notitypeName =
		{"Visitas de contactos",
		"Comentarios de contactos",
		"Evaluaciones de contactos",
		"Sitios cercanos",
		"Productos cercanos",
		"Eventos cercanos"};
	
	private static final String [] notitypeDescription =
		{"Visitas que realizaron contactos",
		"Comentarios de visitas que realizaron contactos",
		"Evaluaciones de compras que realizaron contactos",
		"Sitios cercanos",
		"Productos cercanos",
		"Eventos cercanos"};
	
	// datos de los items
	
    public static final String NOTITYPE_ITEM_ID = "tsi2.GeoRedDemo.notitype_id";
    public static final String NOTITYPE_ITEM_NAME = "tsi2.GeoRedDemo.notitype_name";
    public static final String NOTITYPE_ITEM_DESCRIPTION = "tsi2.GeoRedDemo.notitype_description";
    
    // adaptador de lista de notitipos
    
    private static NotificationsTypesListAdapter adapter;
    
    // lista de notitipos
    
    private static UserNotificationsTypes notitypes;
    
    // id del usuario
    
    private static String userId;
    
    // constructor

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.configure_notifications_activity);
        
        // poblar lista de items
        notitypes = null;
        NotificationsController.getInstance ().getNotificationsTypesConfiguration (new OnCompletedCallback() {
			
			@Override
			public void onCompleted (String response, String error)
			{
				if (error == null) {

					// obtener notitypes
			        Gson gson = new Gson();
			        notitypes = gson.fromJson (response, UserNotificationsTypes.class);
		        
			        // inicializar hashtags
			        
			        ArrayList <HashMap <String, String>> itemsStringList = new ArrayList <HashMap <String, String>> ();
			        ArrayList <HashMap <String, Integer>> itemsIntList = new ArrayList <HashMap <String, Integer>> ();
			        ArrayList <Boolean> itemsIsCheckedList = new ArrayList <Boolean> ();

            		itemsIsCheckedList.add (notitypes.isNotitype1_contactsVisits ());
            		itemsIsCheckedList.add (notitypes.isNotitype2_contactsComments ());
            		itemsIsCheckedList.add (notitypes.isNotitype3_contactsReviews ());
            		itemsIsCheckedList.add (notitypes.isNotitype4_sites ());
            		itemsIsCheckedList.add (notitypes.isNotitype5_products ());
            		itemsIsCheckedList.add (notitypes.isNotitype6_events ());
			        
			        for (int idx = 0; idx < 6; idx += 1) {
			            // crear item
			            HashMap <String, String> itemStringMap = new HashMap <String, String> ();
			            itemStringMap.put (NOTITYPE_ITEM_ID, "" + idx);
			            itemStringMap.put (NOTITYPE_ITEM_NAME, notitypeName [idx]);
			            itemStringMap.put (NOTITYPE_ITEM_DESCRIPTION, notitypeDescription [idx]);
			 
			            // adding HashList to ArrayList
			            itemsStringList.add (itemStringMap);
	
			            // crear item
			            HashMap <String, Integer> itemIntMap = new HashMap <String, Integer> ();
			 
			            // adding HashList to ArrayList
			            itemsIntList.add (itemIntMap);
			        }
			        
			        // poblar lista de notitipos
			        adapter = new NotificationsTypesListAdapter (ConfigureNotificationsTypesActivity.this, itemsStringList, itemsIntList, itemsIsCheckedList);
			        ListView listView = (ListView) findViewById (R.id.listView_list);
			        listView.setAdapter (adapter);
			        
			        // cliquear línea -> iniciar actividad de Ver datos
			        listView.setOnItemClickListener (new OnItemClickListener() {
	
			        	public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
			        		// cambiar configuración del tipo de notificación
	
					        // ListView listView = (ListView) findViewById (R.id.listView_list);
			            	boolean newIsChecked = ! adapter.isChecked (position);
					        adapter.setChecked (position, newIsChecked);
			            	((CheckBox) view.findViewById (R.id.checkbox)).setChecked (newIsChecked);
			        	}
			        });
            	}
			
				else {
					CommonUtilities.showAlertMessage (ConfigureNotificationsTypesActivity.this, "Error CNTypes onCr", "Hubo un error:\n" + error);
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
				
				// enviar configuración al servidor de aplicación
        		notitypes.setNotitype1_contactsVisits (adapter.isChecked (0));
        		notitypes.setNotitype2_contactsComments (adapter.isChecked (1));
        		notitypes.setNotitype3_contactsReviews (adapter.isChecked (2));
        		notitypes.setNotitype4_sites (adapter.isChecked (3));
        		notitypes.setNotitype5_products (adapter.isChecked (4));
        		notitypes.setNotitype6_events (adapter.isChecked (5));

				NotificationsController.getInstance ().setNotificationsTypesConfiguration (notitypes, new OnCompletedCallback() {
					
					@Override
					public void onCompleted (String response, String error)
					{
						if (error == null) {
							// mostrar confirmación
							AlertDialog alertDialog = new AlertDialog.Builder (ConfigureNotificationsTypesActivity.this).create ();
							alertDialog.setTitle ("Confirmación");
							alertDialog.setMessage ("Actualizaste la configuración de tipos de notificaciones.");
							
							alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
								public void onClick (DialogInterface dialog, int which) {
									// cerrar la actividad.
									finish ();
								}
							});
							
							alertDialog.show();
						}
						
						else {
							CommonUtilities.showAlertMessage (ConfigureNotificationsTypesActivity.this, "Error CNTypes bso", "Hubo un error:\n" + error);
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
