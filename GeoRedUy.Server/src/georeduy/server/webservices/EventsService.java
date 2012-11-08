package georeduy.server.webservices;

import georeduy.server.logic.controllers.EventsController;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.Event;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.bson.types.ObjectId;

import com.google.gson.Gson;

@Path("/Events")
public class EventsService {
	
	// administrar sitios

	@POST()
	@Path("New")
	public Response New(String eventInfo,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}
		
		Response response;
		
		try {
			Gson gson = new Gson();
			String json = eventInfo.split("=")[1];
			Event event = gson.fromJson(json, Event.class);
			EventsController.getInstance().NewEvent(event);
			
			response = Response.status(200).entity(GeoRedConstants.EVENT_SUCCESSFULY_ADDED).build();
	    }
	    catch (Exception e)
	    {
	    	response = Response.status(500).entity(e.getMessage()).build();
	    }
		
		return response;
	}

	// obtener sitios
	@GET()
	@Produces("text/plain")
	@Path("Get")
	public Response Get (@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}

		// obtener sitios
		try {
			Gson gson = new Gson();
			List <Event> events = EventsController.getInstance().getEvents ();
			return Response.status(200).entity (gson.toJson (events)).build();
	    }
		
		// si salta una excepción, devolver error
	    catch (Exception ex)
	    {
	    	return Response.status(500).entity (ex.getMessage()).build();
	    }
	}

	// obtener sitios, por pagina
	@GET()
	@Produces("text/plain")
	@Path("GetPage")
	public Response GetPaged(@QueryParam("from") Integer from,
			@QueryParam("count") Integer count,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity (GeoRedConstants.ACCESS_DENIED).build();
		}

		if (count == null)
			count = 15;
		if (from == null)
			from = 0;
		
		Gson gson = new Gson();
		List <Event> events = EventsController.getInstance().getEvents (from, count);
		return Response.status(200).entity (gson.toJson (events)).build();
	}
	
	@GET()
	@Produces("text/plain")
	@Path("GetByLocation")
	public Response GetByLocation(@QueryParam("latitude") Integer latitude, @QueryParam("longitude") Integer longitud, @Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}
		
		Gson gson = new Gson();
		List <Event> events = EventsController.getInstance().getEventsByPosition (latitude, longitud);
		return Response.status(200).entity(gson.toJson(events)).build();
	}
	
	// obtener datos de una visita.
	// recibe un String con el id, y devuelve uno lleno.
	@GET()
	@Produces("text/plain")
	@Path("GetById")
	public Response GetById  (@QueryParam("eventId") String eventId, 
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (! context.isUserInRole (Roles.REG_USER)) {
			return Response.status(500).entity (GeoRedConstants.ACCESS_DENIED).build();
		}

		// obtener visita
		try {
			Gson gson = new Gson();
			
			Event event = EventsController.getInstance().getById (eventId);
			
			return Response.status(200).entity (gson.toJson(event)).build();
	    }
		
		// si salta una excepción, devolver error
	    catch (Exception ex)
	    {
	    	return Response.status(500).entity (ex.getMessage()).build();
	    }
	}
}
