package georeduy.server.webservices;

import georeduy.server.logic.controllers.SessionController;

import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Path("/Session")
public class Session {

	@GET()
	@Produces("text/plain")
	public String LogIn(@QueryParam("userName") String userName, @QueryParam("password") String password) {
	    return SessionController.getInstance().LogIn(userName, password);
	}
	
	@DELETE()
	public void LogOut(@Context SecurityContext context) {
		if (context.getUserPrincipal() instanceof ClientPrincipal)
		{
			ClientPrincipal principal = (ClientPrincipal)context.getUserPrincipal();
	    	SessionController.getInstance().LogOut(principal.getToken());
		}	
	}
}
