package georeduy.server.dao;

import georeduy.server.logic.model.Site;
import georeduy.server.persistence.MongoConnectionManager;

import java.awt.Point;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;

public class SiteDaoImpl extends BasicDAO<Site, ObjectId> implements ISiteDao {

	private ITagDao tagDao =  new TagDaoImpl();
	
    public SiteDaoImpl() {
        super(Site.class, MongoConnectionManager.instance().getDb());
    }
    
    private List<Site> ResolveReferences(List<Site> sites) {
    	for (Site site : sites) {
    		for (String tagId : site.getTagsIds()) {
    			site.addTag(tagDao.find(new ObjectId(tagId)));
    		}
    	}
    	
    	return sites;
    }

	@Override
    public void saveSite(Site site) {
	    this.save(site);
    }
	
    @Override
    public Site find(ObjectId siteId) {
        return get(siteId);
    }

	@Override
    public Site findByName(String name) {
    	List<Site> sites = createQuery().field("name").equal(name).asList();
    	sites = ResolveReferences(sites);
    	if (sites.size() == 1)
    		return sites.get(0);
    	else
    		return null;
    }

	@Override
    public List<Site> getNearSites(double latitude, double longitude, double radius) {
		List<Site> sites = createQuery().field("coordinates").near(latitude, longitude).asList();
	    if (sites.size() > 0)
	    	return sites;
	    else
	    	return null;
    }

	@Override
    public List<Site> getSites(int from, int count) {
		return ResolveReferences(createQuery().offset(from).limit(count).asList());
    }
}
