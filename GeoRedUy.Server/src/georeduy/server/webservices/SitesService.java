package georeduy.server.webservices;

import georeduy.server.logic.controllers.SitesController;
import georeduy.server.logic.model.Comment;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.Tag;
import georeduy.server.logic.model.Visit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.bson.types.ObjectId;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import sun.misc.BASE64Decoder;

import com.google.gson.Gson;

@Path("/Sites")
public class SitesService {

	// administrar sitios

	@POST()
	@Path("New")
	public Response New(String siteInfo,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED)
					.build();
		}

		Response response;

		try {
			Gson gson = new Gson();
			String json = siteInfo.split("=")[1];
			Site site = gson.fromJson(json, Site.class);
			SitesController.getInstance().NewSite(site);

			response = Response.status(200)
					.entity(GeoRedConstants.SITE_SUCCESSFULY_ADDED).build();
		} catch (Exception e) {
			response = Response.status(500).entity(e.getMessage()).build();
		}

		return response;
	}

	// obtener sitios
	@GET()
	@Produces("text/plain")
	@Path("Get")
	public Response Get(@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED)
					.build();
		}

		// obtener sitios
		try {
			Gson gson = new Gson();
			List<Site> sites = SitesController.getInstance().getSites();
			return Response.status(200).entity(gson.toJson(sites)).build();
		}

		// si salta una excepción, devolver error
		catch (Exception ex) {
			return Response.status(500).entity(ex.getMessage()).build();
		}
	}

	// obtener sitios, por pagina
	@GET()
	@Produces("text/plain")
	@Path("GetPage")
	public Response GetPaged(@QueryParam("from") Integer from,
			@QueryParam("count") Integer count,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED)
					.build();
		}

		if (count == null)
			count = 15;
		if (from == null)
			from = 0;

		Gson gson = new Gson();
		List<Site> sites = SitesController.getInstance().getSites(from, count);
		return Response.status(200).entity(gson.toJson(sites)).build();
	}

	@GET()
	@Produces("text/plain")
	@Path("GetByLocation")
	public Response GetByLocation(@QueryParam("bottomLeftLatitude") Integer bottomLeftLatitude, @QueryParam("bottomLeftLongitude") Integer bottomLeftLongitud, @QueryParam("topRightLatitude") Integer topRightLatitude,@QueryParam("topRightLongitude") Integer topRightLongitude,@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED)
					.build();
		}

		Gson gson = new Gson();
		List<Site> sites = SitesController.getInstance().getSitesByPosition(bottomLeftLatitude, bottomLeftLongitud, topRightLatitude, topRightLongitude);
		
		return Response.status(200).entity(gson.toJson(sites)).build();
	}

	// obtener datos de una visita.
	// recibe un String con el id, y devuelve uno lleno.
	@GET()
	@Produces("text/plain")
	@Path("GetById")
	public Response GetById(@QueryParam("siteId") String siteId,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED)
					.build();
		}

		// obtener visita
		try {
			Gson gson = new Gson();

			Site site = SitesController.getInstance().getById(siteId);

			return Response.status(200).entity(gson.toJson(site)).build();
		}

		// si salta una excepción, devolver error
		catch (Exception ex) {
			return Response.status(500).entity(ex.getMessage()).build();
		}
	}
	
	@GET()
	@Produces("image/jpg")
	@Path("GetImageById")
	public Response GetImageById(@QueryParam("siteId") String siteId,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		/*if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED)
					.build();
		}*/
		
		try {
			byte[] image = SitesController.getInstance().getSiteImage(siteId);
			return Response.status(200).entity(image).build();
		}

		// si salta una excepción, devolver error
		catch (Exception ex) {
			return Response.status(500).entity(ex.getMessage()).build();
		}
	}

	// administrar visitas

	// crear visita
	@POST()
	@Path("Visits/New")
	public Response VisitsNew(String visitInfo,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		// si no es un usuario registrado, devolver error 500 de acceso denegado
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED)
					.build();
		}

		// crear visita
		try {
			Gson gson = new Gson();
			visitInfo = visitInfo.split("=")[1];
			Visit visit = gson.fromJson(visitInfo, Visit.class);
			SitesController.getInstance().newVisit(visit);

			return Response.status(200)
					.entity(GeoRedConstants.SITE_SUCCESSFULY_ADDED).build();
		}

		// si salta una excepción, devolver error
		catch (Exception ex) {
			return Response.status(500).entity(ex.getMessage()).build();
		}
	}

	@GET()
	@Produces("text/plain")
	@Path("Visits/GetById")
	public Response VisitsGetById(@QueryParam("visitId") String visitId,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED)
					.build();
		}

		// obtener visita
		try {
			Gson gson = new Gson();
			Visit visit = SitesController.getInstance().getVisitById(visitId);
			return Response.status(200).entity(gson.toJson(visit)).build();
		}

		// si salta una excepción, devolver error
		catch (Exception ex) {
			return Response.status(500).entity(ex.getMessage()).build();
		}
	}

	// obtener visitas del usuario
	@GET()
	@Produces("text/plain")
	@Path("Visits/GetByUser")
	public Response VisitsGetByUser(@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED)
					.build();
		}

		// obtener visitas del usuario
		try {
			Gson gson = new Gson();
			List<Visit> visits = SitesController.getInstance()
					.getVisitsByUser();
			return Response.status(200).entity(gson.toJson(visits)).build();
		}

		// si salta una excepción, devolver error
		catch (Exception ex) {
			return Response.status(500).entity(ex.getMessage()).build();
		}
	}

	// obtener visitas del usuario, sistema paginado
	@GET()
	@Produces("text/plain")
	@Path("Visits/GetByUserPage")
	public Response VisitsGetByUserPage(
			@QueryParam("pageNumber") Integer pageNumber,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED)
					.build();
		}

		// obtener visitas del usuario
		try {
			Gson gson = new Gson();
			List<Visit> visits = SitesController.getInstance().getVisitsByUser(
					pageNumber);
			return Response.status(200).entity(gson.toJson(visits)).build();
		}

		// si salta una excepción, devolver error
		catch (Exception ex) {
			return Response.status(500).entity(ex.getMessage()).build();
		}
	}

	// administrar comentarios

	// crear comentario. recibe un Comment.
	@POST()
	@Path("Comments/New")
	public Response CommentsNew(String commentInfo,
			@Context HttpServletResponse servletResponse,
			@Context SecurityContext context) {
		// si no es un usuario registrado, devolver error 500 de acceso denegado
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED)
					.build();
		}

		// crear comentario
		try {
			Gson gson = new Gson();
			commentInfo = commentInfo.split("=")[1];
			Comment comment = gson.fromJson(commentInfo, Comment.class);
			SitesController.getInstance().newComment(comment);

			return Response.status(200)
					.entity(GeoRedConstants.COMMENT_SUCCESSFULY_ADDED).build();
		}

		// si salta una excepción, devolver error
		catch (Exception ex) {
			return Response.status(500).entity(ex.getMessage()).build();
		}
	}

	@GET()
	@Produces("text/plain")
	@Path("Comments/GetById")
	public Response CommentsGetById(@QueryParam("commentId") String commentId,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED)
					.build();
		}

		// obtener comentario
		try {
			Gson gson = new Gson();
			Comment comment = SitesController.getInstance().getCommentById(
					commentId);
			return Response.status(200).entity(gson.toJson(comment)).build();
		}

		// si salta una excepción, devolver error
		catch (Exception ex) {
			return Response.status(500).entity(ex.getMessage()).build();
		}
	}

	// obtener comentarios del usuario
	@GET()
	@Produces("text/plain")
	@Path("Comments/GetByUser")
	public Response CommentsGetByUser(@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED)
					.build();
		}

		// obtener comentarios del usuario
		try {
			Gson gson = new Gson();
			List<Comment> comments = SitesController.getInstance()
					.getCommentsByUser();
			return Response.status(200).entity(gson.toJson(comments)).build();
		}

		// si salta una excepción, devolver error
		catch (Exception ex) {
			return Response.status(500).entity(ex.getMessage()).build();
		}
	}

	// obtener comentarios del usuario, sistema paginado
	@GET()
	@Produces("text/plain")
	@Path("Comments/GetByUserPage")
	public Response CommentsGetByUserPage(
			@QueryParam("pageNumber") Integer pageNumber,
			@Context SecurityContext context) {
		if (!context.isUserInRole(Roles.REG_USER)) {
			return Response.status(500).entity(GeoRedConstants.ACCESS_DENIED)
					.build();
		}

		// obtener comentarios del usuario
		try {
			Gson gson = new Gson();
			List<Comment> comments = SitesController.getInstance()
					.getCommentsByUser(pageNumber);
			return Response.status(200).entity(gson.toJson(comments)).build();
		}

		// si salta una excepción, devolver error
		catch (Exception ex) {
			return Response.status(500).entity(ex.getMessage()).build();
		}
	}

	@POST
	@Path("/add")
	@Consumes("multipart/form-data")
	public Response addSite(@MultipartForm AddSiteForm form) {
		Response ret;
		try {
			Site site = new Site();
			site.setName(form.getName());
			site.setAddress(form.getAddress());
			Double[] coordinates = new Double[2];
			coordinates[1] = Double.valueOf(form.getLatitude());
			coordinates[0] = Double.valueOf(form.getLongitude());
			site.setCoordinates(coordinates);
			site.setDescription(form.getDescription());
			site.setImage(form.getImageData());
			site.setRadius(Integer.valueOf(form.getRadius()));
			if (form.getTags() != null && !form.getTags().isEmpty()) {
				site.setName(form.getName());
				String[] tagsStr = form.getTags().trim().split(",");
				for (String tagStr : tagsStr) {
					Tag tag = new Tag();
					tag.setName(tagStr.trim());
					site.addTag(tag);
				}
			}
			SitesController.getInstance().NewSite(site);
			ret = Response.seeOther(
					new URI("../GeoRedUy.BackEnd/site?ListSites")).build();
		} catch (Exception e) {
			e.printStackTrace();
			ret = Response.serverError().entity(e.getMessage()).build();
		}

		return ret;
	}

	public static class AddSiteForm {
		private byte[] imageData;
		private String name;
		private String description;
		private String address;
		private String latitude;
		private String longitude;
		private String tags;
		private String radius;

		public AddSiteForm() {
		}

		public String getName() {
			return name;
		}

		@FormParam("name")
		@PartType("text/plain")
		public void setName(final String name) {
			System.out.println("setting data name from form.");
			this.name = name;
		}

		public byte[] getImageData() {
			return imageData;
		}

		@FormParam("imageData")
		@PartType("application/octet-stream")
		public void setImageData(byte[] imageData) {
			this.imageData = imageData;
		}

		public String getDescription() {
			return description;
		}

		@FormParam("description")
		@PartType("text/plain")
		public void setDescription(String description) {
			this.description = description;
		}

		public String getAddress() {
			return address;
		}

		@FormParam("address")
		@PartType("text/plain")
		public void setAddress(String address) {
			this.address = address;
		}

		public String getLatitude() {
			return latitude;
		}

		@FormParam("latitude")
		@PartType("text/plain")
		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		public String getLongitude() {
			return longitude;
		}

		@FormParam("longitude")
		@PartType("text/plain")
		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}

		public String getTags() {
			return tags;
		}

		@FormParam("tags")
		@PartType("text/plain")
		public void setTags(String tags) {
			this.tags = tags;
		}

		public String getRadius() {
			if (radius == null || radius.trim().equals(""))
				return "10";
			return radius;
		}

		@FormParam("radius")
		@PartType("text/plain")
		public void setRadius(String radius) {
			this.radius = radius;
		}

	}
}
