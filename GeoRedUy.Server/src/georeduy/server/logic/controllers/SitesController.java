package georeduy.server.logic.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import georeduy.server.dao.CommentDaoImpl;
import georeduy.server.dao.ICommentDao;
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

public class SitesController {
	private static SitesController s_instance = null;

	
	// objetos dao
	private ICommentDao commentDao = new CommentDaoImpl();
	private ISiteDao siteDao = new SiteDaoImpl();
	private ITagDao tagDao = new TagDaoImpl();
	private IUserDao userDao = new UserDaoImpl();
	private IVisitDao visitDao = new VisitDaoImpl();
	
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
        			throw new Exception(GeoRedConstants.TAG_DOES_NOT_EXIST + ":" + tag.getName().trim());
        		realTags.add(dbTag);
        	}
        	site.setTags(realTags);
            siteDao.saveSite(site);
        } else {
        	throw new Exception(GeoRedConstants.SITE_NAME_EXISTS);
        }
	}
	
	// obtener sitios
	public List<Site> getSites () {
		return siteDao.getSites();
	}

	// obtener sitios, por p�gina
	public List<Site> getSites (int from, int count) {
		return siteDao.getSites(from, count);
	}

	// obtener sitios cercanos
	public List<Site> getSitesByPosition(int latitude, int longitud) {
		/*List<Site> lista = new ArrayList<Site>();
		Site sitio1 = new Site();
		sitio1.coordinates[0] = latitude/1e6;
		sitio1.coordinates[1] = longitud/1e6;
		sitio1.setName("prueba1");
		Site sitio2 = new Site();
		sitio2.coordinates[0] = latitude/1e6;
		sitio2.coordinates[1] = longitud/1e6;
		sitio2.setName("prueba2");
		lista.add(sitio1);
		lista.add(sitio2);*/
		return siteDao.getNearSites(latitude/1e6, longitud/1e6, 50);		
	}
	
	// obtener datos de un sitio.
	public Site getById (String siteId) {
		return siteDao.find (new ObjectId (siteId));		
	}
	
	// administrar visitas
	
	// crear visita
	public void newVisit (Visit visit) throws Exception {
		// comprobar existencia del sitio
		Site realSite = siteDao.find (new ObjectId (visit.getSiteId ()));
		if (realSite == null) {
			throw new Exception (GeoRedConstants.SITE_DOES_NOT_EXIST); //+ ":" + visit.getUserId ().trim());
		}
		visit.setRealSite (realSite);
		
		// agregar usuario
		visit.setRealUser (User.Current ());
		visit.setUserId (User.Current ().getId ());
		
		// agregar fecha
		Date currentDate = new Date();
		currentDate.setSeconds (0);
		visit.setDate (currentDate);
		/*
		Calendar currentCal = Calendar.getInstance();
		currentCal.clear();
		currentCal.setTime (currentDate);
		currentCal.set (Calendar.SECOND, 0);
		currentCal.set (Calendar.MILLISECOND, 0);
		visit.setDate (currentCal.getTime ());
		*/
    	
		// crear visita
        visitDao.saveVisit (visit);
	}
	
	// obtener datos de una visita.
	public Visit getVisitById (String visitId) {
		return visitDao.find (new ObjectId (visitId));		
	}

	// obtener visitas del usuario
	public List <Visit> getVisitsByUser () {
    	return visitDao.findByUser (User.Current ().getId ());
	}

	// obtener visitas del usuario, sistema paginado
	public List <Visit> getVisitsByUser (int from) {
    	return visitDao.findByUser (User.Current ().getId (), from, 10);
	}
	
	// administrar comentarios
	
	// crear comentario
	public void newComment (Comment comment) throws Exception {
		// comprobar existencia de la visita
		Visit realVisit = visitDao.find (new ObjectId (comment.getVisitId ()));
		if (realVisit == null) {
			throw new Exception (GeoRedConstants.VISIT_DOES_NOT_EXIST); //+ ":" + visit.getUserId ().trim());
		}
		
		// agregar fecha actual
		Date currentDate = new Date();
		currentDate.setSeconds (0);
		comment.setDate (currentDate);
		/*
		Calendar currentCal = Calendar.getInstance();
		currentCal.clear();
		currentCal.setTime (currentDate);
		currentCal.set (Calendar.SECOND, 0);
		currentCal.set (Calendar.MILLISECOND, 0);
		visit.setDate (currentCal.getTime ());
		*/
    	
		// crear comentario
        commentDao.saveComment (comment);
        
        // agregar comentario a la visita
        visitDao.addVisitComment (new ObjectId (comment.getVisitId ()), comment);
        
	}
	
	// obtener datos de un comentario.
	public Comment getCommentById (String commentId) {
		return commentDao.find (new ObjectId (commentId));		
	}

	// obtener comentarios del usuario
	public List <Comment> getCommentsByUser () {
    	return commentDao.findByUser (User.Current ().getId ());
	}

	// obtener comentarios del usuario, sistema paginado
	public List <Comment> getCommentsByUser (int from) {
    	return commentDao.findByUser (User.Current ().getId (), from, 10);
	}
}
