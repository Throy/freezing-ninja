// ClientsController

// controlador para los casos de uso de usuarios:
// agregar contactos, chatear.

package georeduy.client.controllers;

// imports

import georeduy.client.model.Contact;
import georeduy.client.model.User;
import georeduy.client.util.GeoRedClient;
import georeduy.client.util.OnCompletedCallback;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class ClientsController
{
	// *******************
	// instancia singleton
	// *******************
	
	private static ClientsController _instance = null;
	
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
}
