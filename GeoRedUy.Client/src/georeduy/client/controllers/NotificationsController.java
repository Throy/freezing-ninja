// NotificacionesController

// controlador para los casos de uso de notificaciones:
// configurar, listar, etc.

package georeduy.client.controllers;

// imports

import georeduy.client.model.User;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class NotificationsController
{
	// *******************
	// instancia singleton
	// *******************
	
	private static NotificationsController _instance = null;
	
	// *************
	// constructores
	// *************
	
	private NotificationsController () {
		
	}
	
	public static NotificationsController getInstance() {
		if (_instance == null) {
			_instance = new NotificationsController ();
		}
		
		return _instance;
	}

	// *******
	// métodos
	// *******
	
	// obtener datos.
	
	public void getSomething () {
	}
}
