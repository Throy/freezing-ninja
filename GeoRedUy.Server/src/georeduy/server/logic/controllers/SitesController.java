package georeduy.server.logic.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import georeduy.server.dao.ISiteDao;
import georeduy.server.dao.ITagDao;
import georeduy.server.dao.IUserDao;
import georeduy.server.dao.IVisitDao;
import georeduy.server.dao.SiteDaoImpl;
import georeduy.server.dao.TagDaoImpl;
import georeduy.server.dao.UserDaoImpl;
import georeduy.server.dao.VisitDaoImpl;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.Tag;
import georeduy.server.logic.model.User;
import georeduy.server.logic.model.Visit;

public class SitesController {
	private static SitesController s_instance = null;

	
	// objetos dao
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
	
	public List<Site> Get(int from, int count) {
		return siteDao.getSites(from, count);
	}
	
	public List<Site> GetByPosition(int latitude, int longitud) {
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
	
	// obtener datos de una visita.
	public Site getById (String siteId) {
		return siteDao.find (new ObjectId (siteId));		
	}
	
	// administrar visitas
	
	// crear visita
	public void newVisit (Visit visit) throws Exception {
		// comprobar existencia del usuario
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

	// obtener visitas del usuario
	public List <Visit> getVisitsByUser () {
    	return visitDao.findByUser (User.Current ().getId ());
	}

	// obtener visitas del usuario, sistema paginado
	public List <Visit> getVisitsByUser (int from) {
    	return visitDao.findByUser (User.Current ().getId (), from, 10);
	}
}
