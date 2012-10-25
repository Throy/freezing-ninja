package georeduy.server.dao;

import georeduy.server.logic.model.Retailer;
import georeduy.server.logic.model.Site;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;

public class RetailerDaoImpl extends BasicDAO<Retailer, ObjectId> implements IRetailerDao {

	private IUserDao userDao = new UserDaoImpl();
	
    public RetailerDaoImpl() {
        super(Retailer.class, MongoConnectionManager.instance().getDb());
    }
    
    private List<Retailer> ResolveReferences(List<Retailer> retailers) {
    	for (Retailer retailer : retailers) {
    		retailer.setUser(userDao.find(new ObjectId(retailer.getUserId())));
    	}    	
    	return retailers;
    }

    @Override
    public void saveRetailer(Retailer retailer) {
        this.save(retailer);
    }

    @Override
    public Retailer find(ObjectId retialerId) {
        return get(retialerId);
    }

    @Override
    public Retailer findByName(String name) {
    	List<Retailer> retailers = createQuery().field("name").equal(name).asList();
    	retailers = ResolveReferences(retailers);
    	if (retailers.size() == 1)
    		return retailers.get(0);
    	else
    		return null;
    }
    
    @Override
    public List<Retailer> getRetailers(int from, int count) {
		return ResolveReferences(createQuery().offset(from).limit(count).asList());
    }
}
