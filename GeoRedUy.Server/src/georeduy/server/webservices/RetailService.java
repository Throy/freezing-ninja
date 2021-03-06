package georeduy.server.webservices;

import georeduy.server.logic.controllers.RetailController;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.RetailStore;
import georeduy.server.logic.model.Retailer;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.User;

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

import com.google.gson.Gson;

@Path("/Retail")
public class RetailService {

	@POST()
	@Path("NewRetailer")
	public Response NewRetailer(String retailerInfo,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}
		
		Response response;
		
		try {
			Gson gson = new Gson();
			String json = retailerInfo.split("=")[1];
			Retailer retailer = gson.fromJson(json, Retailer.class);
			RetailController.getInstance().NewRetailer(retailer);
			
			response = Response.status(200).entity(GeoRedConstants.RETAILER_SUCCESSFULY_ADDED).build();
	    }
	    catch (Exception e)
	    {
	    	response = Response.status(500).entity(e.getMessage()).build();
	    }
		
		return response;
	}
	
	@GET()
	@Produces("text/plain")
	@Path("GetRetailers")
	public Response GetRetailers(@QueryParam("from") Integer from,
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
		List<Retailer> retailers = RetailController.getInstance().GetRetailers(from, count);
		return Response.status(200).entity(gson.toJson(retailers)).build();
	}
	
	@POST()
	@Path("NewStore")
	public Response NewStore(String storeInfo,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.RETAIL_MANAGER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}
		
		Response response;
		
		try {
			Gson gson = new Gson();
			String json = storeInfo.split("=")[1];
			RetailStore store = gson.fromJson(json, RetailStore.class);
			RetailController.getInstance().NewStore(store, User.Current().getRetailId());
			
			response = Response.status(200).entity(GeoRedConstants.STORE_SUCCESSFULY_ADDED).build();
	    }
	    catch (Exception e)
	    {
	    	response = Response.status(500).entity(e.getMessage()).build();
	    }
		
		return response;
	}
	
	// obtener locales de la empresa del adminsitrador.
	@GET()
	@Produces("text/plain")
	@Path("GetStores")
	public Response GetStores(@QueryParam("from") Integer from,
			@QueryParam("count") Integer count,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.RETAIL_MANAGER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}

		if (count == null)
			count = 15;
		if (from == null)
			from = 0;
		
		Gson gson = new Gson();
		List<RetailStore> stores = RetailController.getInstance().GetStoresRetailer(from, count);
		return Response.status(200).entity(gson.toJson(stores)).build();
	}
	
	// obtener todos los locales.
	@GET()
	@Produces("text/plain")
	@Path("GetStoresFull")
	public Response GetStores(@Context SecurityContext context) {
		// si no es un usuario registrado, devolver un error.
		if (! context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}
		
		// obtener locales
		try {
			Gson gson = new Gson();
			List<RetailStore> stores = RetailController.getInstance().GetStores();
			return Response.status(200).entity(gson.toJson(stores)).build();
	    }
		
		// si ocurre un error, devolverlo.
	    catch (Exception e)
	    {
	    	return Response.status(500).entity(e.getMessage()).build();
	    }
	}
	
	@GET()
	@Produces("text/plain")
	@Path("GetStore")
	public Response GetStore(@QueryParam("id") String id,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}

		Gson gson = new Gson();
		RetailStore stores = RetailController.getInstance().GetStore(id);
		return Response.status(200).entity(gson.toJson(stores)).build();
	}
	
	@GET()
	@Produces("text/plain")
	@Path("GetByLocation")
	public Response GetByLocation(@QueryParam("bottomLeftLatitude") Integer bottomLeftLatitude, @QueryParam("bottomLeftLongitude") Integer bottomLeftLongitud, @QueryParam("topRightLatitude") Integer topRightLatitude,@QueryParam("topRightLongitude") Integer topRightLongitude,@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}
		
		Gson gson = new Gson();
		List<RetailStore> stores = RetailController.getInstance().GetByPosition(bottomLeftLatitude, bottomLeftLongitud, topRightLatitude, topRightLongitude);
		String hola = gson.toJson(stores);
		return Response.status(200).entity(gson.toJson(stores)).build();
	}
}
