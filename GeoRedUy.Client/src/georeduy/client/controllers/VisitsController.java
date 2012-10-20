// VisitsController

// controlador para los casos de uso de visitas:
// visitar sitios, ver datos de visita y sitio, etc.

package georeduy.client.controllers;

// imports

import georeduy.client.model.User;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class VisitsController
{
	// *******************
	// instancia singleton
	// *******************
	
	private static VisitsController _instance = null;
	
	// *************
	// constructores
	// *************
	
	private VisitsController () {
		
	}
	
	public static VisitsController getInstance() {
		if (_instance == null) {
			_instance = new VisitsController ();
		}
		
		return _instance;
	}

	// *******
	// métodos
	// *******
	
	// *** hay que ver cuáles se precisan y cuáles no. ***
	
	// obtener datos de sitios.
	// *** en realidad devuelve Collection <Site> o algo por el estilo. ***
	// *** falta definir qué ubicaciones valen para la búsqueda. ***
	
	public void getSites () {
	}
	
	// visitar sitio.
	
	public void visitSite (int siteId) {
	}
	
	// obtener datos visitas del usuario.
	
	public void getVisits () {
		// *** en realidad devuelve Collection <User> o algo por el estilo. ***
	}
	
	// obtener datos de una visita del usuario.
	// *** en realidad devuelve Collection <Visit> o algo por el estilo. ***
	
	public void getVisit (int visitId) {
	}
	
	// publicar comentario de una visita.
	// *** en realdiad falta enviar fotos y videos. ***
	
	public void publishVisitComment (int visitId, String message) {
	}
}
