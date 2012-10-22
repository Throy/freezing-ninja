// ClientsController

// controlador para los casos de uso de usuarios:
// agregar contactos, chatear.

package georeduy.client.controllers;

// imports

import georeduy.client.model.User;

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

	// *** hay que ver cuáles se precisan y cuáles no. ***
	
	// obtener datos de los contactos del usuario.
	// *** en realidad devuelve Collection <User> o algo por el estilo. ***
	
	public void getContacts () {
	}
	
	// obtener datos de los no contactos del usuario.
	// *** en realidad devuelve Collection <User> o algo por el estilo. ***
	
	public void getNoncontacts () {
	}
	
	// obtener datos del usuario.
	
	public User getUser () {
		return new User();
	}

	// agregar usuario a los contactos
	public void addContact (int userId) {
	}

	// quitar usuario de los contactos
	public void removeContact (int userId) {
	}
}
