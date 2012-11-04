package georeduy.server.webservices;

import georeduy.server.logic.controllers.NotificationsController;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.ChatMessage;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.User;

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

@Path("/Notifications")
public class NotificationsService {
	
    protected void setSuccess(HttpServletResponse resp, int size) {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/plain");
        resp.setContentLength(size);
    }
    
	@GET()
	@Produces("text/plain")
	@Path("SayHello")
	public String SayHello(@Context SecurityContext context, @QueryParam("name") String name) {
		if (context.isUserInRole(Roles.REG_USER))
			return "Hi4 " + name + ", UserName = " + context.getUserPrincipal().getName();
		else
			return "No way!";
	}

	@GET()
	@Produces("text/plain")
	@Path("Register")
	public String Register(@QueryParam("id") String id,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
	    
		if (context.isUserInRole(Roles.REG_USER))
		{
			NotificationsController.getInstance().RegisterDevice(id);
	    
			Response.status(200).build();
		}
		
		return "";
	}
	
	@GET()
	@Produces("text/plain")
	@Path("Unregister")
	public String Unregister(@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		
		if (context.isUserInRole(Roles.REG_USER))
		{
			NotificationsController.getInstance().UnregisterDevice();
	    
			Response.status(200).build();
		};
		
		return "";
	}
	
	@GET()
	@Path("Broadcast")
	public void Broadcast(@Context HttpServletResponse servletResponse) {

		Site site = new Site();
		site.setName("Test GCM site");
		site.setDescription("GCM site desc");
		Double[] coordinates = new Double[2];
		coordinates[0] = -34.892930;
		coordinates[1] = -56.130430;
		site.setCoordinates(coordinates);
		
		NotificationsController.getInstance().BroadCast(site);
    
		Response.status(200).build();
	}
	
	@POST()
	@Path("SendMessage")
	public Response SendMessage(String messageInfo,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}
		
		Response response;
		
		try {
			Gson gson = new Gson();
			ChatMessage message = gson.fromJson(messageInfo.split("=")[1], ChatMessage.class);
			message.setFromUserId(User.Current().getId());
			message.setFromUserName(User.Current().getUserName());
			
			NotificationsController.getInstance().SendToClient(message.getToUserId(), message);
			
			response = Response.status(200).entity(GeoRedConstants.MESSAGE_SENT).build();
	    }
	    catch (Exception e)
	    {
	    	response = Response.status(500).entity(e.getMessage()).build();
	    }
		
		return response;
	}
}
