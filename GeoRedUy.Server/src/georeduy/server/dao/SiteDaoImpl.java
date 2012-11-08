package georeduy.server.dao;

import georeduy.server.logic.model.Site;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;

public class SiteDaoImpl extends BasicDAO<Site, ObjectId> implements ISiteDao {

	private ITagDao tagDao =  new TagDaoImpl();
	
    public SiteDaoImpl() {
        super(Site.class, MongoConnectionManager.instance().getDb());
    }
    
    private Site ResolveReferences(Site site) {
		for (String tagId : site.getTagsIds()) {
			site.addTag(tagDao.find(new ObjectId(tagId)));
		}
    	
    	return site;
    }
    
    private List<Site> ResolveReferences(List<Site> sites) {
    	for (Site site : sites) {
    		ResolveReferences(site);
    	}
    	
    	return sites;
    }

	@Override
    public void saveSite(Site site) {
	    this.save(site);
    }
	
    @Override
    public Site find(ObjectId siteId) {
        return ResolveReferences(get(siteId));
    }

	@Override
    public Site findByName(String name) {
    	List<Site> sites = createQuery().field("name").equal(name).asList();
    	sites = ResolveReferences(sites);
    	if (sites.size() == 1) {
    		return ResolveReferences(sites.get(0));
    	}
    	else {
    		return null;
    	}
    }

	@Override
    public List<Site> getNearSites(double latitude, double longitude, double radius) {
		double distanceDegrees = 0.0026997840172; //3.1 / 69;		// radio de 3 millas, son 69 millas por grado
		List<Site> sites = createQuery().field("coordinates").within(longitude, latitude, distanceDegrees).asList();
		sites = ResolveReferences(sites);
	    if (sites.size() > 0)
	    	return sites;
	    else
	    	return null;
    }

	// obtener sitios
	@Override
    public List<Site> getSites() {
		return ResolveReferences (createQuery().asList());
    }

	// obtener sitios, por página
	@Override
    public List<Site> getSites(int from, int count) {
		return ResolveReferences (createQuery().offset(from).limit(count).asList());
    }
}
