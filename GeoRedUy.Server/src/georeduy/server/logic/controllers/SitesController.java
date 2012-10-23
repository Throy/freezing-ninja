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
}
