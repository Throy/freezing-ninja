package georeduy.server.dao;

import georeduy.server.logic.model.RetailStore;

import java.util.List;

import org.bson.types.ObjectId;

public interface IRetailStoreDao {

    public void saveStore(RetailStore store);

    public RetailStore find(ObjectId siteId);

    public RetailStore findByName(String name);

    public List<RetailStore> getNearStores(double latitude, double longitude, double radius);
    
    public List<RetailStore> getStores();
    
    public List<RetailStore> getStores(int from, int count);
    
    public List<RetailStore> getStores(int from, int count, String retailerId);
}
