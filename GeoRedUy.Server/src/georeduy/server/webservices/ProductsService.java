package georeduy.server.webservices;

import georeduy.server.logic.controllers.ProductsController;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Product;
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

@Path("/Products")
public class ProductsService {

	// crear producto nuevo
	@POST()
	@Path("New")
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
			ProductsController.getInstance().newProduct (product, User.Current ().getRetailId ());
			
			response = Response.status(200).entity(GeoRedConstants.PRODUCT_SUCCESSFULY_ADDED).build();
	    }
	    catch (Exception e)
	    {
	    	response = Response.status(500).entity(e.getMessage()).build();
	    }
		
		return response;
	}
	
	// obtener productos de una empresa
	@GET()
	@Produces("text/plain")
	@Path("GetByRetailer")
	public Response GetProducts(@QueryParam("from") Integer from,
			@QueryParam("count") Integer count,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.RETAIL_MANAGER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}

		if (count == null) {
			count = 15;
		}
		if (from == null) {
			from = 0;
		}

		Gson gson = new Gson();
		List<Product> products = ProductsController.getInstance().getProducts (from, count, User.Current().getRetailId());
		return Response.status(200).entity(gson.toJson(products)).build();
	}
	
	// agregar producto a un local
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
			ProductsController.getInstance().addStoreProduct (storeId, productName);
			response = Response.status(200).entity(GeoRedConstants.PRODUCT_SUCCESSFULY_ADDED).build();
		}
		catch (Exception e)
	    {
	    	response = Response.status(500).entity(e.getMessage()).build();
	    }
		
		return response;
	}

	// obtener productos de un local
	@GET()
	@Produces("text/plain")
	@Path("GetByStore")
	public Response GetStoreProducts(@QueryParam("from") Integer from,
			@QueryParam("count") Integer count,
			@QueryParam("id") String id,
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
		List<Product> products = ProductsController.getInstance().getStoreProducts (from, count, id);
		return Response.status(200).entity(gson.toJson(products)).build();
	}
}
