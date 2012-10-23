package georeduy.server.dao;

import georeduy.server.logic.model.Site;

import java.awt.Point;
import java.util.List;

import org.bson.types.ObjectId;

public interface ISiteDao {

    public void saveSite(Site site);

    public Site find(ObjectId siteId);

    public Site findByName(String name);

    public List<Site> getNearSites(Point location);
    
    public List<Site> getSites(int from, int count);
}
