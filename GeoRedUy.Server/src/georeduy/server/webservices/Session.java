package georeduy.server.webservices;

import georeduy.server.data.User;
import georeduy.server.logic.controllers.SessionController;
import georeduy.server.logic.model.ErrorConstants;
import georeduy.server.logic.model.Roles;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Path("/Session")
public class Session {

	@GET()
	@Produces("text/plain")
	public Response LogIn(@QueryParam("userName") String userName, @QueryParam("password") String password) {
		Response response;
		try {
			String token = SessionController.getInstance().LogIn(userName, password);
			response = Response.status(200).entity(token).build();
	    }
	    catch (Exception e)
	    {
	    	response = Response.status(500).entity(e.getMessage()).build();
	    }
		
		return response;
	}
	
	@GET()
	@Path("LogOut")
	public Response LogOut(@Context SecurityContext context) {
		Response response = Response.status(200).build();
		try {
			if (context.getUserPrincipal() instanceof UserPrincipal)
			{
				UserPrincipal principal = (UserPrincipal)context.getUserPrincipal();
		    	SessionController.getInstance().LogOut(principal.getToken());
			}	
		} catch (Exception e) {
		    response = Response.status(500).entity(e.getMessage()).build();
		}
		
		return response;
	}
	
	@GET()
	@Path("GetUserInfo")
	public Response GetUserInfo(@Context SecurityContext context) {
		Response response;
		if (context.isUserInRole(Roles.REG_USER)) {
			Gson gson = new Gson();
			UserPrincipal principal = (UserPrincipal)context.getUserPrincipal();
	    	response = Response.status(200).entity(gson.toJson(principal.getUser())).build();
		} else {
			response = Response.status(500).entity(ErrorConstants.ACCESS_DENIED).build();
		}
		return response;
	}
	
	@POST()
	@Path("Register")
	@Produces("text/plain")
	public Response Register(String userInfo) {
		Response response;
		try {
			Gson gson = new Gson();
			SessionController.getInstance().Register(gson.fromJson(userInfo.split("=")[1], User.class));
			response = Response.status(200).entity("User successfuly registered!").build();
	    }
	    catch (Exception e)
	    {
	    	response = Response.status(500).entity(e.getMessage()).build();
	    }
		
		return response;
	}
}
