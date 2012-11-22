package georeduy.server.webservices;

import georeduy.server.logic.controllers.ProductsController;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Purchase;
import georeduy.server.logic.model.Roles;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.google.gson.Gson;

@Path("/Reports")
public class ReportsService {

	@GET()
	@Produces("text/plain")
	@Path("Purchases")
	public Response Get(@QueryParam("from") Integer from,
			@QueryParam("count") Integer count,
			@QueryParam("fechaDesde") @DefaultValue("0") Long fechaDesde,
			@QueryParam("fechaHasta") @DefaultValue("0") Long fechaHasta,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.ADMIN)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED)
					.build();
		}

		if (count == null)
			count = 15;
		if (from == null)
			from = 0;

		Gson gson = new Gson();

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(fechaDesde);
		Date fDesde = calendar.getTime();
		calendar.setTimeInMillis(fechaHasta);
		Date fHasta = calendar.getTime();

		List<Purchase> purchases = ProductsController.getInstance()
				.getPurchasesByPeriod(fDesde, fHasta);
		return Response.status(200).entity(gson.toJson(purchases)).build();
	}
}
