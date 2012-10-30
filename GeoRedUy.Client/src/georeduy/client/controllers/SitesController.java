// VisitsController

// controlador para los casos de uso de visitas:
// visitar sitios, ver datos de visita y sitio, etc.

package georeduy.client.controllers;

// imports

import georeduy.client.model.Site;
import georeduy.client.model.User;
import georeduy.client.model.Visit;
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
	// m�todos
	// *******
	
	// *** hay que ver cu�les se precisan y cu�les no. ***
	
	// obtener datos de sitios.
	// *** falta definir qu� ubicaciones valen para la b�squeda. ***
	
	public void getSitesByPosition (int latitude, int longitude, OnCompletedCallback callback) {
		Map<String, String> params = new HashMap <String, String>();
		params.put ("latitude", Integer.toString(latitude));
		params.put ("longitude", Integer.toString(longitude));
		
    	GeoRedClient.GetAsync("/Sites/GetByLocation", params, callback);
	}
	
	
	// obtener datos de un sitio.
	// devuelve un Site.
	
	public void getSite (String siteId, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		
		// agregar par�metros
		params.put ("siteId", siteId);
		
    	GeoRedClient.GetAsync("/Sites/GetById", params, callback);	
	}
	
	// visitar sitio.
	
	public void visitSite (String siteId, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
        Gson gson = new Gson();
		
		// agregar par�metros
		Visit visitInfo = new Visit ();
		visitInfo.setSiteId (siteId);
		params.put ("visitInfo", gson.toJson(visitInfo));
		
    	GeoRedClient.PostAsync("/Sites/Visits/New", params, callback);	
	}
	
	// obtener datos de visitas del usuario.
	
	public void getVisits (OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		
    	GeoRedClient.GetAsync("/Sites/Visits/GetByUser", params, callback);
	}
	
	// obtener datos de una visita del usuario.
	
	public void getVisit (String visitId, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		
		// agregar par�metros
		params.put ("visitId", visitId);
		
    	GeoRedClient.GetAsync("/Sites/Visits/GetById", params, callback);	
	}
	
	// publicar comentario de una visita.
	// *** en realdiad falta enviar fotos y videos. ***
	
	public void publishVisitComment (String visitId, String message) {
	}
}
