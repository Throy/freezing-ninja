package georeduy.server.dao;

import georeduy.server.logic.model.Site;

import java.awt.Point;
import java.util.List;

import org.bson.types.ObjectId;

public interface ISiteDao {

    public void saveSite(Site site);

    public Site find(ObjectId siteId);

    public Site findByName(String name);

    public List<Site> getNearSites(double bottomLeftLatitude, double bottomLeftLongitude, double topRightLatitude, double topRightLongitude);
    
    public List<Site> getSites();
    
    public List<Site> getSites(int from, int count);
    
    public byte[] getSiteImage(String id);
}
