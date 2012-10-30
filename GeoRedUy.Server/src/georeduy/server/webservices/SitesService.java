package georeduy.server.webservices;

import georeduy.server.logic.controllers.SitesController;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.Visit;

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

@Path("/Sites")
public class SitesService {
	
	// administrar sitios

	@POST()
	@Path("New")
	public Response New(String siteInfo,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}
		
		Response response;
		
		try {
			Gson gson = new Gson();
			String json = siteInfo.split("=")[1];
			Site site = gson.fromJson(json, Site.class);
			SitesController.getInstance().NewSite(site);
			
			response = Response.status(200).entity(GeoRedConstants.SITE_SUCCESSFULY_ADDED).build();
	    }
	    catch (Exception e)
	    {
	    	response = Response.status(500).entity(e.getMessage()).build();
	    }
		
		return response;
	}
	
	@GET()
	@Produces("text/plain")
	@Path("Get")
	public Response Get(@QueryParam("from") Integer from,
			@QueryParam("count") Integer count,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}

		if (count == null)
			count = 15;
		if (from == null)
			from = 0;
		
		Gson gson = new Gson();
		List<Site> sites = SitesController.getInstance().Get(from, count);
		return Response.status(200).entity(gson.toJson(sites)).build();
	}
	
	@GET()
	@Produces("text/plain")
	@Path("GetByLocation")
	public Response GetByLocation(@QueryParam("latitude") Integer latitude, @QueryParam("longitude") Integer longitud, @Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}
		
		Gson gson = new Gson();
		List<Site> sites = SitesController.getInstance().GetByPosition(latitude, longitud);
		return Response.status(200).entity(gson.toJson(sites)).build();
	}
	
	// obtener datos de una visita.
	// recibe un String con el id, y devuelve uno lleno.
	@GET()
	@Produces("text/plain")
	@Path("GetById")
	public Response GetById  (@QueryParam("siteId") String siteId, 
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (! context.isUserInRole (Roles.REG_USER)) {
			return Response.status(500).entity (GeoRedConstants.ACCESS_DENIED).build();
		}

		// obtener visita
		try {
			Gson gson = new Gson();
			
			Site site = SitesController.getInstance().getById (siteId);
			
			return Response.status(200).entity (gson.toJson(site)).build();
	    }
		
		// si salta una excepción, devolver error
	    catch (Exception ex)
	    {
	    	return Response.status(500).entity (ex.getMessage()).build();
	    }
	}
	
	// administrar visitas

	// crear visita
	@POST()
	@Path("Visits/New")
	public Response VisitsNew (String visitInfo,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		// si no es un usuario registrado, devolver error 500 de acceso denegado
		if (! context.isUserInRole (Roles.REG_USER)) {
			return Response.status(500).entity (GeoRedConstants.ACCESS_DENIED).build();
		}
		
		// crear visita
		try {
			Gson gson = new Gson();
			visitInfo = visitInfo.split("=")[1];
			Visit visit = gson.fromJson (visitInfo, Visit.class);
			SitesController.getInstance().newVisit (visit);
			
			return Response.status(200).entity (GeoRedConstants.SITE_SUCCESSFULY_ADDED).build();
	    }
		
		// si salta una excepción, devolver error
	    catch (Exception ex)
	    {
	    	return Response.status(500).entity (ex.getMessage()).build();
	    }
	}
	
	@GET()
	@Produces("text/plain")
	@Path("Visits/GetById")
	public Response VisitsGetById (@QueryParam("visitId") String visitId, 
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}
		
		// obtener visita
		try {
			Gson gson = new Gson();
			Visit visit = SitesController.getInstance().getVisitById (visitId);
			return Response.status(200).entity(gson.toJson(visit)).build();
	    }
		
		// si salta una excepción, devolver error
	    catch (Exception ex)
	    {
	    	return Response.status(500).entity (ex.getMessage()).build();
	    }
	}

	// obtener visitas del usuario
	@GET()
	@Produces("text/plain")
	@Path("Visits/GetByUser")
	public Response VisitsGetByUser (@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}

		// obtener visitas del usuario
		try {
			Gson gson = new Gson();
			List <Visit> visits = SitesController.getInstance().getVisitsByUser();
			return Response.status(200).entity (gson.toJson(visits)).build();
	    }
		
		// si salta una excepción, devolver error
	    catch (Exception ex)
	    {
	    	return Response.status(500).entity (ex.getMessage()).build();
	    }
	}

	// obtener visitas del usuario, sistema paginado
	@GET()
	@Produces("text/plain")
	@Path("Visits/GetByUserPage")
	public Response VisitsGetByUserPage (@QueryParam("pageNumber") Integer pageNumber, @Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}

		// obtener visitas del usuario
		try {
			Gson gson = new Gson();
			List <Visit> visits = SitesController.getInstance().getVisitsByUser (pageNumber);
			return Response.status(200).entity(gson.toJson (visits)).build();
	    }
		
		// si salta una excepción, devolver error
	    catch (Exception ex)
	    {
	    	return Response.status(500).entity (ex.getMessage()).build();
	    }
	}
}
