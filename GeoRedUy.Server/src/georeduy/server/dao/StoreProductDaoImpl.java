package georeduy.server.dao;

import georeduy.server.logic.model.Product;
import georeduy.server.logic.model.RetailStore;
import georeduy.server.logic.model.StoreProduct;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;

public class StoreProductDaoImpl extends BasicDAO<StoreProduct, ObjectId> implements IStoreProductDao {
	
	private IProductDao productDao = new ProductDaoImpl();
	
    public StoreProductDaoImpl() {
        super(StoreProduct.class, MongoConnectionManager.instance().getDb());
    }

	@Override
    public void saveStoreProduct(StoreProduct storeProduct) {
	    this.save(storeProduct);
    }
	
    @Override
    public StoreProduct find(ObjectId id) {
        return get(id);
    }

	@Override
	public List<Product> getStoreProducts(int from, int count, String storeId) {
		List<StoreProduct> storeProducts = createQuery().field("storeId").equal(storeId).offset(from).limit(count).asList();
		List<Product> products = new ArrayList<Product>();
		for (StoreProduct storeProduct : storeProducts) {
			products.add(productDao.find(new ObjectId(storeProduct.getProductId())));
		}
		return products;
    }
}
