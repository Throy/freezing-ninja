package georeduy.server.webservices;

import georeduy.server.logic.model.Roles;

import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Path("/Notifications")
public class Notifications {

	@GET()
	@Produces("text/plain")
	public String sayHello(@Context SecurityContext context, @QueryParam("name") String name) {
		if (context.isUserInRole(Roles.REG_USER))
			return "Hi4 " + name + " UserName = " + context.getUserPrincipal().getName();
		else
			return "No way!";
	}
}
