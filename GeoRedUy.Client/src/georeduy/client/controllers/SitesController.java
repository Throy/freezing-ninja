// VisitsController

// controlador para los casos de uso de visitas:
// visitar sitios, ver datos de visita y sitio, etc.

package georeduy.client.controllers;

// imports

import georeduy.client.model.Site;
import georeduy.client.model.User;
import georeduy.client.util.GeoRedClient;
import georeduy.client.util.OnCompletedCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SitesController
{
	// *******************
	// instancia singleton
	// *******************
	
	private static SitesController _instance = null;
	
	// *************
	// constructores
	// *************
	
	private SitesController () {
		
	}
	
	public static SitesController getInstance() {
		if (_instance == null) {
			_instance = new SitesController ();
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
	
	public void getSitesByPosition (int latitude, int longitude, OnCompletedCallback callback) {
		Map<String, String> params = new HashMap <String, String>();
		params.put ("latitude", Integer.toString(latitude));
		params.put ("longitude", Integer.toString(longitude));
		
    	GeoRedClient.GetAsync("/Sites/GetByLocation", params, callback);
	}
	
	
	// obtener datos de una visita del usuario.
	
	public Site getSite (int siteId) {
		// *** LLAMAR A LA BASE DE DATOS ***
		
		return new Site ();
	}
	
	// visitar sitio.
	
	public void visitSite (int siteId) {
		// *** LLAMAR A LA BASE DE DATOS ***
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
