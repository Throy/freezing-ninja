package georeduy.server.logic.controllers;

import java.util.ArrayList;
import java.util.List;

import georeduy.server.dao.ISiteDao;
import georeduy.server.dao.ITagDao;
import georeduy.server.dao.SiteDaoImpl;
import georeduy.server.dao.TagDaoImpl;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.Tag;

public class SitesController {
	private static SitesController s_instance = null;

	private ISiteDao siteDao =  new SiteDaoImpl();
	
	private ITagDao tagDao =  new TagDaoImpl();
	
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
}
