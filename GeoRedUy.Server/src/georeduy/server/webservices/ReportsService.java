package georeduy.server.webservices;

import georeduy.server.logic.controllers.ProductsController;
import georeduy.server.logic.controllers.SitesController;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Purchase;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.Visit;

import java.util.ArrayList;
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

		System.out.println("---> visits desde -->" + fechaDesde);
		System.out.println("---> visits hasta -->" + fechaHasta);
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
	
	@GET()
	@Produces("text/plain")
	@Path("Visits")
	public Response GetVisits(@QueryParam("from") Integer from,
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

		System.out.println("---> visits desde -->" + fechaDesde);
		System.out.println("---> visits hasta -->" + fechaHasta);
		Gson gson = new Gson();

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(fechaDesde);
		Date fDesde = calendar.getTime();
		calendar.setTimeInMillis(fechaHasta);
		Date fHasta = calendar.getTime();

		List<Visit> visits = ProductsController.getInstance()
				.getVisitsByPeriod(fDesde, fHasta);
		
		
		List<Site> result = new ArrayList<Site>();
		for (Visit v : visits) {
			result.add(SitesController.getInstance().getSiteById(v.getSiteId()));
		}
		
		
		return Response.status(200).entity(gson.toJson(result)).build();
	}	
}
