package georeduy.server.logic.controllers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import georeduy.server.dao.CommentDaoImpl;
import georeduy.server.dao.ContactDaoImpl;
import georeduy.server.dao.ICommentDao;
import georeduy.server.dao.IContactDao;
import georeduy.server.dao.ISiteDao;
import georeduy.server.dao.ITagDao;
import georeduy.server.dao.IUserDao;
import georeduy.server.dao.IVisitDao;
import georeduy.server.dao.SiteDaoImpl;
import georeduy.server.dao.TagDaoImpl;
import georeduy.server.dao.UserDaoImpl;
import georeduy.server.dao.VisitDaoImpl;
import georeduy.server.logic.model.Comment;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.Tag;
import georeduy.server.logic.model.User;
import georeduy.server.logic.model.Visit;
import georeduy.server.util.Filter;
import georeduy.server.util.Util;

public class SitesController {
	private static SitesController s_instance = null;

	// objetos dao
	private ICommentDao commentDao = new CommentDaoImpl();
	private ISiteDao siteDao = new SiteDaoImpl();
	private ITagDao tagDao = new TagDaoImpl();
	private IUserDao userDao = new UserDaoImpl();
	private IVisitDao visitDao = new VisitDaoImpl();
	private IContactDao contactsDao = new ContactDaoImpl();

	// constructores

	public SitesController() {
	}

	public static SitesController getInstance() {
		if (s_instance == null) {
			s_instance = new SitesController();
		}

		return s_instance;
	}

	// adminsitrar sitios

	public void NewSite(Site site) throws Exception {
		if (siteDao.findByName(site.getName()) == null) {
			List<Tag> realTags = new ArrayList<Tag>();
			for (Tag tag : site.getTags()) {
				Tag dbTag = tagDao.findByName(tag.getName().trim());
				if (dbTag == null)
					throw new Exception(GeoRedConstants.TAG_DOES_NOT_EXIST
							+ ":" + tag.getName().trim());
				realTags.add(dbTag);
			}
			site.setTags(realTags);
			
			if (site.getImage() != null) {
				BufferedImage image = Util.resize(Util.byteToBufferImage(site.getImage()), 80, 80);
				site.setImage(Util.toByte(image));
			}
			
			siteDao.saveSite(site);
			
			// Image is to long to broadcast
			site.setImage(null);
			
			final Site siteF = site;
			NotificationsController.getInstance().BroadCast(site, new Filter() {

				@Override
				public boolean filter(String userId) {
					User user = SessionController.getInstance().GetUserById(
							userId);
					if (Util.distanceHaversine(siteF.getCoordinates()[0],
							siteF.getCoordinates()[1],
							user.getCoordinates()[0], user.getCoordinates()[1]) <= Util.BROADCAST_RANGE) {
						return false;
					}
					return true;
				}
			});

		} else {
			throw new Exception(GeoRedConstants.SITE_NAME_EXISTS);
		}
	}

	// obtener sitios
	public List<Site> getSites() {
		return siteDao.getSites();
	}

	// obtener sitios, por página
	public List<Site> getSites(int from, int count) {
		return siteDao.getSites(from, count);
	}

	// obtener sitios cercanos
	public List<Site> getSitesByPosition(int latitude, int longitud) {
		/*
		 * List<Site> lista = new ArrayList<Site>(); Site sitio1 = new Site();
		 * sitio1.coordinates[0] = latitude/1e6; sitio1.coordinates[1] =
		 * longitud/1e6; sitio1.setName("prueba1"); Site sitio2 = new Site();
		 * sitio2.coordinates[0] = latitude/1e6; sitio2.coordinates[1] =
		 * longitud/1e6; sitio2.setName("prueba2"); lista.add(sitio1);
		 * lista.add(sitio2);
		 */

		// TODO: revisar si se mantiene que la posicion pasada es la ultima
		// Actualizo la ultima posicion conocida del usuario
		Double[] coordinates = new Double[2];
		coordinates[1] = latitude / 1e6;
		coordinates[0] = longitud / 1e6;
		SessionController.getInstance().GetUserById(User.Current().getId())
				.setCoordinates(coordinates);

		return siteDao.getNearSites(latitude / 1e6, longitud / 1e6, 0.01);
	}

	// obtener datos de un sitio.
	public Site getById(String siteId) {
		return siteDao.find(new ObjectId(siteId));
	}
	
	public byte[] getSiteImage(String siteId) {
		return siteDao.getSiteImage(siteId);
	}

	// administrar visitas

	// crear visita
	public void newVisit(Visit visit) throws Exception {
		// comprobar existencia del sitio
		Site realSite = siteDao.find(new ObjectId(visit.getSiteId()));
		if (realSite == null) {
			throw new Exception(GeoRedConstants.SITE_DOES_NOT_EXIST); // + ":" +
																		// visit.getUserId
																		// ().trim());
		}

		// agregar usuario
		visit.setUserId(User.Current().getId());

		// agregar fecha
		Date currentDate = new Date();
		currentDate.setSeconds(0);
		visit.setDate(currentDate);
		/*
		 * Calendar currentCal = Calendar.getInstance(); currentCal.clear();
		 * currentCal.setTime (currentDate); currentCal.set (Calendar.SECOND,
		 * 0); currentCal.set (Calendar.MILLISECOND, 0); visit.setDate
		 * (currentCal.getTime ());
		 */

		// crear visita
		visitDao.saveVisit(visit);

		visitDao.resolveReferences(visit);
		// Notificar la visita a los contactos del usuario

		final Visit visitF = visit;
		NotificationsController.getInstance().BroadCast(visit, new Filter() {

			@Override
			public boolean filter(String userId) {
				User user = SessionController.getInstance().GetUserById(userId);
				User visitUser = SessionController.getInstance().GetUserById(
						visitF.getUserId()); // userDao.find(new
												// ObjectId(visit.getUserId()));
				if (contactsDao.userHasContact(userId, visitF.getUserId())
						&& Util.distanceHaversine(
								visitUser.getCoordinates()[0],
								visitUser.getCoordinates()[1],
								user.getCoordinates()[0],
								user.getCoordinates()[1]) <= Util.BROADCAST_RANGE) {
					return false;
				}
				return true;
			}
		});
	}

	// obtener datos de una visita.
	public Visit getVisitById(String visitId) {
		return visitDao.find(new ObjectId(visitId));
	}

	// obtener visitas del usuario
	public List<Visit> getVisitsByUser() {
		return visitDao.findByUser(User.Current().getId());
	}

	// obtener visitas del usuario, sistema paginado
	public List<Visit> getVisitsByUser(int from) {
		return visitDao.findByUser(User.Current().getId(), from, 10);
	}

	// administrar comentarios

	// crear comentario
	public void newComment(Comment comment) throws Exception {
		// comprobar existencia de la visita
		Visit realVisit = visitDao.find(new ObjectId(comment.getVisitId()));
		if (realVisit == null) {
			throw new Exception(GeoRedConstants.VISIT_DOES_NOT_EXIST); // + ":"
																		// +
																		// visit.getUserId
																		// ().trim());
		}

		// agregar fecha actual
		Date currentDate = new Date();
		currentDate.setSeconds(0);
		comment.setDate(currentDate);
		/*
		 * Calendar currentCal = Calendar.getInstance(); currentCal.clear();
		 * currentCal.setTime (currentDate); currentCal.set (Calendar.SECOND,
		 * 0); currentCal.set (Calendar.MILLISECOND, 0); visit.setDate
		 * (currentCal.getTime ());
		 */

		// crear comentario
		commentDao.saveComment(comment);

		// agregar comentario a la visita
		visitDao.addVisitComment(new ObjectId(comment.getVisitId()), comment);

	}

	// obtener datos de un comentario.
	public Comment getCommentById(String commentId) {
		return commentDao.find(new ObjectId(commentId));
	}

	// obtener comentarios del usuario
	public List<Comment> getCommentsByUser() {
		return commentDao.findByUser(User.Current().getId());
	}

	// obtener comentarios del usuario, sistema paginado
	public List<Comment> getCommentsByUser(int from) {
		return commentDao.findByUser(User.Current().getId(), from, 10);
	}
}
