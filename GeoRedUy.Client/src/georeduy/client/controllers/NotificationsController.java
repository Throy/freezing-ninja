// NotificacionesController

// controlador para los casos de uso de notificaciones:
// configurar, listar, etc.

package georeduy.client.controllers;

// imports

import georeduy.client.model.Site;

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
	
	public void handleNotification(Site site) {
		String siteName = site.getName();
	}
}
