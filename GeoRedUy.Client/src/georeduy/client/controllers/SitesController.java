// VisitsController

// controlador para los casos de uso de visitas:
// visitar sitios, ver datos de visita y sitio, etc.

package georeduy.client.controllers;

// imports

import georeduy.client.activities.MapaActivity;
import georeduy.client.model.Comment;
import georeduy.client.model.Site;
import georeduy.client.model.User;
import georeduy.client.model.Visit;
import georeduy.client.util.CommonUtilities;
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
	
	// sitios
	
	// *** hay que ver cuáles se precisan y cuáles no. ***
	
	/*
	
	// obtener datos de todos los sitios.
	
	public void getSites (OnCompletedCallback callback) {
		Map<String, String> params = new HashMap <String, String>();
    	GeoRedClient.GetAsync("/Sites/Get", params, callback);
	}
	
	*/
	
	// obtener datos de todos los sitios, por página.
	
	public void getSites (Integer from, OnCompletedCallback callback) {
		Map<String, String> params = new HashMap <String, String>();
		params.put ("from", Integer.toString (from));
		params.put ("count", Integer.toString (10));
    	GeoRedClient.GetAsync("/Sites/Get", params, callback);
	}
	
	// obtener datos de sitios cercanos.
	// *** falta definir qué ubicaciones valen para la búsqueda. ***
	
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
		
		// agregar parámetros
		params.put ("siteId", siteId);
		
    	GeoRedClient.GetAsync("/Sites/GetById", params, callback);	
	}
	
	// visitar sitio.
	
	public void visitSite (String siteId, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
        Gson gson = new Gson();
		
		// agregar parámetros
		Visit visitInfo = new Visit ();
		visitInfo.setSiteId (siteId);
		params.put ("visitInfo", gson.toJson(visitInfo));
		
    	GeoRedClient.PostAsync("/Sites/Visits/New", params, callback);	
	}
	
	// visitas
	
	// obtener datos de visitas del usuario.
	
	public void getVisits (OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		
    	GeoRedClient.GetAsync("/Sites/Visits/GetByUser", params, callback);
	}
	
	// obtener datos de una visita del usuario.
	
	public void getVisit (String visitId, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		
		// agregar parámetros
		params.put ("visitId", visitId);
		
    	GeoRedClient.GetAsync("/Sites/Visits/GetById", params, callback);	
	}
	
	// publicar comentario de una visita.
	// *** en realidad falta enviar fotos y videos. ***
	
	public void publishVisitComment (String visitId, String text, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
        Gson gson = new Gson();
		
		// agregar parámetros
		Comment comment = new Comment ();
		comment.setText (text);
		comment.setVisitId (visitId);
		params.put ("commentInfo", gson.toJson(comment));
		
    	GeoRedClient.PostAsync("/Sites/Comments/New", params, callback);
	}
	
	// comentarios
	
	// obtener datos de comentarios del usuario.
	
	public void getComments (OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		
    	GeoRedClient.GetAsync("/Sites/Comments/GetByUser", params, callback);
	}
	
	// obtener datos de un comentario del usuario.
	
	public void getComment (String commentId, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		
		// agregar parámetros
		params.put ("commentId", commentId);
		
    	GeoRedClient.GetAsync("/Sites/Comments/GetById", params, callback);	
	}

	// devuelve true si se puede visitar el sitio.
	public boolean visitIsAllowed (Site site)
	{
		int distance = CommonUtilities.distance
				(site.getCoordinates()[0],
				site.getCoordinates()[1], 
				MapaActivity.longitudCurrent / 1E6,
				MapaActivity.latitudCurrent / 1E6);
		
		return (distance <= site.getRadius ());
	}
}
