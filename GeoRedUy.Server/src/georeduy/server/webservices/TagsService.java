package georeduy.server.webservices;

import georeduy.server.logic.controllers.SitesController;
import georeduy.server.logic.controllers.TagsEventsController;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.Tag;

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

@Path("/Tags")
public class TagsService {

	@POST()
	@Path("New")
	public Response New(String tagInfo,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}
		
		Response response;
		
		try {
			Gson gson = new Gson();
			Tag tag = gson.fromJson(tagInfo.split("=")[1], Tag.class);
			TagsEventsController.getInstance().NewTag(tag);
			
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
		List<Tag> tags = TagsEventsController.getInstance().Get(from, count);
		return Response.status(200).entity(gson.toJson(tags)).build();
	}
}
