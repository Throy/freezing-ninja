package georeduy.server.dao;

import georeduy.server.logic.model.Site;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.mongodb.WriteResult;

public class SiteDaoImpl extends BasicDAO<Site, ObjectId> implements ISiteDao {

	private ITagDao tagDao =  new TagDaoImpl();
	
    public SiteDaoImpl() {
        super(Site.class, MongoConnectionManager.instance().getDb());
    }
    
    private Site ResolveReferences(Site site) {
		for (String tagId : site.getTagsIds()) {
			site.addTag(tagDao.find(new ObjectId(tagId)));
		}
		
		// Esto es un fix espantoso porque no me quiere devolver los sitios en el android, 
		// asi que para obtener la imagen hay que llamar a get
    	site.setImage(null);
    	
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
    public List<Site> getNearSites(double bottomLeftLatitude, double bottomLeftLongitude, double topRightLatitude, double topRightLongitude){
		double miles = 0.0034997840172;
		List<Site> sites = createQuery().field("coordinates").within(bottomLeftLongitude,bottomLeftLatitude,topRightLongitude,topRightLatitude).asList();
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

	// obtener sitios, por p�gina
	@Override
    public List<Site> getSites(int from, int count) {
		return ResolveReferences (createQuery().offset(from).limit(count).asList());
    }

	// Llamar este metodo cuando se quiere la imagen de un sitio!
	@Override
    public byte[] getSiteImage(String id) {
	    Site site = get(new ObjectId(id));
	    return site.getImage();
    }
}
