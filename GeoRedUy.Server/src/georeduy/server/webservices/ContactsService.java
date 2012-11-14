package georeduy.server.webservices;

import georeduy.server.logic.controllers.ClientsController;
import georeduy.server.logic.model.Contact;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Invitation;
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

@Path("/Contacts")
public class ContactsService {

	@POST()
	@Path("AddContact")
	public Response AddContact(String contactInfo,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}
		
		Response response;
		
		try {
			Gson gson = new Gson();
			Contact contact = gson.fromJson(contactInfo.split("=")[1], Contact.class);
			ClientsController.getInstance().AddContact(contact);
			
			response = Response.status(200).entity(GeoRedConstants.CONTACT_SUCCESSFULY_ADDED).build();
	    }
	    catch (Exception e)
	    {
	    	response = Response.status(500).entity(e.getMessage()).build();
	    }
		
		return response;
	}
	
	@POST()
	@Path("RemoveContact")
	public Response RemoveContact(String contactInfo,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED).build();
		}
		
		Response response;
		
		try {
			Gson gson = new Gson();
			Contact contact = gson.fromJson(contactInfo.split("=")[1], Contact.class);
			ClientsController.getInstance().RemoveContact(contact);
			
			response = Response.status(200).entity(GeoRedConstants.CONTACT_SUCCESSFULY_REMOVED).build();
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
		List<Contact> contacts = ClientsController.getInstance().Get(from, count);
		return Response.status(200).entity(gson.toJson(contacts)).build();
	}
	
	@GET()
	@Produces("text/plain")
	@Path("Search")
	public Response Search(@QueryParam("query") String query,
			@QueryParam("from") Integer from,
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
		List<User> users = ClientsController.getInstance().SearchUsersByName(query, from, count);
		return Response.status(200).entity(gson.toJson(users)).build();
	}

	// envía una invitación a un usuario
	@POST()
	@Path("SendInvitation")
	public Response SendInvitation(String invitationInfo,
			@Context SecurityContext context) {
		if (! context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity (GeoRedConstants.ACCESS_DENIED).build();
		}

		try {
			Gson gson = new Gson();
			Invitation invitation = gson.fromJson(invitationInfo.split("=")[1], Invitation.class);
			ClientsController.getInstance().sendInvitation (invitation.getEmail (), invitation.getUsername (), invitation.getMessage ());

			return Response.status(200).entity (GeoRedConstants.USER_INVITATION_SENT).build();
		}
		catch (Exception e)
		{
			return Response.status(500).entity (e.getMessage()).build();
		}
	}
}
