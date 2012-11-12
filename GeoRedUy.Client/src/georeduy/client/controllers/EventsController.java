// VisitsController

// controlador para los casos de uso de visitas:
// visitar sitios, ver datos de visita y sitio, etc.

package georeduy.client.controllers;

// imports

import georeduy.client.model.Comment;
import georeduy.client.model.Event;
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

public class EventsController
{
	// *******************
	// instancia singleton
	// *******************
	
	private static EventsController _instance = null;
	
	// *************
	// constructores
	// *************
	
	private EventsController () {
		
	}
	
	public static EventsController getInstance() {
		if (_instance == null) {
			_instance = new EventsController ();
		}
		
		return _instance;
	}

	// *******
	// métodos
	// *******
	
	// sitios
	
	// *** hay que ver cuáles se precisan y cuáles no. ***
	
	// obtener datos de todos los sitios.
	
	public void getEvents (OnCompletedCallback callback) {
		Map<String, String> params = new HashMap <String, String>();
    	GeoRedClient.GetAsync("/Events/Get", params, callback);
	}
	
	// obtener datos de todos los sitios, por página.
	
	public void getEvents (Integer from, OnCompletedCallback callback) {
		Map<String, String> params = new HashMap <String, String>();
		params.put ("from", Integer.toString (from));
		params.put ("count", Integer.toString (10));
    	GeoRedClient.GetAsync("/Events/Get", params, callback);
	}
	
	// obtener datos de sitios cercanos.
	// *** falta definir qué ubicaciones valen para la búsqueda. ***
	
	public void getEventsByPosition (int latitude, int longitude, OnCompletedCallback callback) {
		Map<String, String> params = new HashMap <String, String>();
		params.put ("latitude", Integer.toString(latitude));
		params.put ("longitude", Integer.toString(longitude));
		
    	GeoRedClient.GetAsync("/Events/GetByLocation", params, callback);
	}
	
	// obtener datos de un sitio.
	// devuelve un Event.
	
	public void getEvent (String eventId, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		
		// agregar parámetros
		params.put ("eventId", eventId);
		
    	GeoRedClient.GetAsync("/Events/GetById", params, callback);	
	}
	
		
}
