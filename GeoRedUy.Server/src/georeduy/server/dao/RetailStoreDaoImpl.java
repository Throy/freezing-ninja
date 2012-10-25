package georeduy.server.dao;

import georeduy.server.logic.model.RetailStore;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;

public class RetailStoreDaoImpl extends BasicDAO<RetailStore, ObjectId> implements IRetailStoreDao {
	
    public RetailStoreDaoImpl() {
        super(RetailStore.class, MongoConnectionManager.instance().getDb());
    }

	@Override
    public void saveStore(RetailStore store) {
	    this.save(store);
    }
	
    @Override
    public RetailStore find(ObjectId storeId) {
        return get(storeId);
    }

	@Override
    public RetailStore findByName(String name) {
    	List<RetailStore> stores = createQuery().field("name").equal(name).asList();
    	if (stores.size() == 1)
    		return stores.get(0);
    	else
    		return null;
    }

	@Override
    public List<RetailStore> getNearStores(double latitude, double longitude, double radius) {
		List<RetailStore> stores = createQuery().field("coordinates").near(latitude, longitude).asList();
	    if (stores.size() > 0)
	    	return stores;
	    else
	    	return null;
    }

	@Override
    public List<RetailStore> getStores(int from, int count) {
		return createQuery().offset(from).limit(count).asList();
    }
	
	@Override
    public List<RetailStore> getStores(int from, int count, String retailerId) {
		return createQuery().field("retailerId").equal(retailerId).offset(from).limit(count).asList();
    }
}
