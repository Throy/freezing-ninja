package georeduy.server.logic.controllers;

import java.util.ArrayList;
import java.util.List;

import georeduy.server.dao.ISiteDao;
import georeduy.server.dao.SiteDaoImpl;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.Site;

public class SitesController {
	private static SitesController s_instance = null;

	private ISiteDao siteDao =  new SiteDaoImpl();
	
	public SitesController() {
	}

	public static SitesController getInstance() {
		if (s_instance == null) {
			s_instance = new SitesController();
		}

		return s_instance;
	}
	
	public void NewSite(Site site) throws Exception {
        if (siteDao.findByName(site.getName()) == null) {
            siteDao.saveSite(site);
        } else {
        	throw new Exception(GeoRedConstants.SITE_NAME_EXISTS);
        }
	}
	
	public List<Site> Get(int from, int count) {
		return siteDao.getSites(from, count);
	}
	
	public List<Site> GetByPosition(int latitude, int longitud) {
		List<Site> lista = new ArrayList<Site>();
		Site sitio1 = new Site();
		sitio1.coordinates[0] = latitude+0.0;
		sitio1.coordinates[1] = longitud+0.0;
		sitio1.setName("prueba1");
		Site sitio2 = new Site();
		sitio2.coordinates[0] = latitude+0.0;
		sitio2.coordinates[1] = longitud+0.0;
		sitio2.setName("prueba2");
		lista.add(sitio1);
		lista.add(sitio2);
		return lista;		
	}
}
