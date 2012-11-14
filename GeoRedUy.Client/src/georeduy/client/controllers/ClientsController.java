// ClientsController

// controlador para los casos de uso de usuarios:
// agregar contactos, chatear.

package georeduy.client.controllers;

// imports

import georeduy.client.activities.ContactListActivity;
import georeduy.client.activities.R;
import georeduy.client.lists.UsersListAdapter;
import georeduy.client.lists.UsersListAdapter.UserListMode;
import georeduy.client.model.Contact;
import georeduy.client.model.Invitation;
import georeduy.client.model.User;
import georeduy.client.util.GeoRedClient;
import georeduy.client.util.OnCompletedCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClientsController
{
	// *******************
	// instancia singleton
	// *******************
	
	private static ClientsController _instance = null;
	
	private List<Contact> _contacts = null;
	
	// *************
	// constructores
	// *************
	
	private ClientsController () {
		
	}
	
	public static ClientsController getInstance() {
		if (_instance == null) {
			_instance = new ClientsController ();
		}
		
		return _instance;
	}

	// *******
	// métodos
	// *******
	
	// obtener datos de los contactos del usuario.
	public void GetContacts(OnCompletedCallback callback) {
		Map<String, String> params = new HashMap <String, String>();
		params.put ("from", "0");
		params.put ("count", "10");
		
    	GeoRedClient.GetAsync("/Contacts/Get", params, callback);
	}
	
	// Obtener los contactos desde cache, si nunca se pideron se piden al servidor.
	public List<Contact> GetContacts() {
		if (_contacts == null) {
			GetContacts(new OnCompletedCallback() {
				
				@Override
				public void onCompleted(String response, String error) {
					if (error == null) {
						Gson gson = new Gson();
			        	Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();				    		
			    		List<Contact> contacts = gson.fromJson(response, listType);
			    		if (contacts != null) {
			    			_contacts = contacts;
			    		}
					}
				}
			});
		}
		
		return _contacts;
	}
	
	public void SearchUsers(String query, OnCompletedCallback callback) {
		Map<String, String> params = new HashMap <String, String>();
		params.put ("query", query);
		params.put ("from", "0");
		params.put ("count", "10");
		
    	GeoRedClient.GetAsync("/Contacts/Search", params, callback);
	}
	
	public void AddContact(String contactUserId, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
        Gson gson = new Gson();
		
		// agregar parámetros
		Contact contact = new Contact();
		contact.setContactUserId(contactUserId);
		
		params.put ("contactInfo", gson.toJson(contact));
		
    	GeoRedClient.PostAsync("/Contacts/AddContact", params, callback);	
	}

	// quitar usuario de los contactos
	public void removeContact (String contactUserId, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
        Gson gson = new Gson();
		
		// agregar parámetros
		Contact contact = new Contact();
		contact.setContactUserId(contactUserId);
		
		params.put ("contactInfo", gson.toJson(contact));
		
    	GeoRedClient.PostAsync("/Contacts/RemoveContact", params, callback);	
	}

	// envía una invitación a un usuario
	public void sendInvitation (String userEmail, String userName, String message, OnCompletedCallback callback)
	{
		Map <String, String> params = new HashMap <String, String>();
        Gson gson = new Gson();

		// agregar parámetros
		Invitation invitation = new Invitation();
		invitation.setEmail (userEmail);
		invitation.setUsername (userName);
		invitation.setMessage (message);

		params.put ("invitationInfo", gson.toJson (invitation));

		GeoRedClient.PostAsync ("/Contacts/SendInvitation", params, callback);   
	}
}
