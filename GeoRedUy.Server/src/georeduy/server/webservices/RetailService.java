package georeduy.server.webservices;

import georeduy.server.logic.controllers.RetailController;
import georeduy.server.logic.controllers.SitesController;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Product;
import georeduy.server.logic.model.RetailStore;
import georeduy.server.logic.model.Retailer;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.Site;

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
			RetailController.getInstance().NewStore(store);
			
			response = Response.status(200).entity(GeoRedConstants.STORE_SUCCESSFULY_ADDED).build();
	    }
	    catch (Exception e)
	    {
	    	response = Response.status(500).entity(e.getMessage()).build();
	    }
		
		return response;
	}
	
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
		List<RetailStore> stores = RetailController.getInstance().GetStores(from, count);
		return Response.status(200).entity(gson.toJson(stores)).build();
	}
	
	@POST()
	@Path("NewProduct")
	public Response NewProduct(String productInfo,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.RETAIL_MANAGER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}
		
		Response response;
		
		try {
			Gson gson = new Gson();
			String json = productInfo.split("=")[1];
			Product product = gson.fromJson(json, Product.class);
			RetailController.getInstance().NewProduct(product);
			
			response = Response.status(200).entity(GeoRedConstants.PRODUCT_SUCCESSFULY_ADDED).build();
	    }
	    catch (Exception e)
	    {
	    	response = Response.status(500).entity(e.getMessage()).build();
	    }
		
		return response;
	}
	
	@GET()
	@Produces("text/plain")
	@Path("GetProducts")
	public Response GetProducts(@QueryParam("from") Integer from,
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
		List<Product> products = RetailController.getInstance().GetProducts(from, count);
		return Response.status(200).entity(gson.toJson(products)).build();
	}
	
	@GET()
	@Produces("text/plain")
	@Path("GetStore")
	public Response GetStore(@QueryParam("id") String id,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.RETAIL_MANAGER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}

		Gson gson = new Gson();
		RetailStore stores = RetailController.getInstance().GetStore(id);
		return Response.status(200).entity(gson.toJson(stores)).build();
	}
	
	@GET()
	@Produces("text/plain")
	@Path("AddStoreProduct")
	public Response AddStoreProduct(@QueryParam("productName") String productName,
			@QueryParam("storeId") String storeId,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.RETAIL_MANAGER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}
		
		Response response;
		
		try {
			RetailController.getInstance().AddStoreProduct(storeId, productName);
			response = Response.status(200).entity(GeoRedConstants.PRODUCT_SUCCESSFULY_ADDED).build();
		}
		catch (Exception e)
	    {
	    	response = Response.status(500).entity(e.getMessage()).build();
	    }
		
		return response;
	}
	
	@GET()
	@Produces("text/plain")
	@Path("GetStoreProducts")
	public Response GetStoreProducts(@QueryParam("from") Integer from,
			@QueryParam("count") Integer count,
			@QueryParam("id") String id,
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
		List<Product> products = RetailController.getInstance().GetStoreProducts(from, count, id);
		return Response.status(200).entity(gson.toJson(products)).build();
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
		String hola = gson.toJson(sites);
		return Response.status(200).entity(gson.toJson(sites)).build();
	}
}
