package georeduy.server.dao;

import georeduy.server.logic.model.Retailer;
import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.User;
import georeduy.server.logic.model.UserData;

import java.util.List;
import org.bson.types.ObjectId;

public interface IRetailerDao {

    public void saveRetailer(Retailer retailer);

    public Retailer find(ObjectId retailerId);

    public Retailer findByName(String name);

    public List<Retailer> getRetailers(int from, int count);
}
