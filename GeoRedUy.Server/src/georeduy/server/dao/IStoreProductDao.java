package georeduy.server.dao;

import georeduy.server.logic.model.Product;
import georeduy.server.logic.model.StoreProduct;

import java.util.List;

import org.bson.types.ObjectId;

public interface IStoreProductDao {

    public void saveStoreProduct(StoreProduct storeProduct);

    public StoreProduct find(ObjectId siteId);

    public List<Product> getStoreProducts(int from, int count, String storeId);
}
