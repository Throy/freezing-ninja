package georeduy.server.dao;

import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.mongodb.WriteResult;

import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.User;
import georeduy.server.logic.model.UserData;
import georeduy.server.persistence.MongoConnectionManager;

import java.awt.Point;
import java.util.List;
import org.bson.types.ObjectId;

public class SiteDaoImpl extends BasicDAO<Site, ObjectId> implements ISiteDao {

    public SiteDaoImpl() {
        super(Site.class, MongoConnectionManager.instance().getDb());
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
    	if (sites.size() == 1)
    		return sites.get(0);
    	else
    		return null;
    }

	@Override
    public List<Site> getNearSites(Point location) {
		List<Site> sites = createQuery().field("coordinates").near(location.getX(), location.getY()).asList();
	    if (sites.size() > 0)
	    	return sites;
	    else
	    	return null;
    }

	@Override
    public List<Site> getSites(int from, int count) {
		return createQuery().offset(from).limit(count).asList();
    }
}
