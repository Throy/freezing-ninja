package georeduy.server.dao;

import georeduy.server.logic.model.Product;
import georeduy.server.logic.model.Tag;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;

public class ProductDaoImpl extends BasicDAO<Product, ObjectId> implements IProductDao {

    public ProductDaoImpl() {
        super(Product.class, MongoConnectionManager.instance().getDb());
    }

	@Override
    public void saveProduct(Product product) {
	    this.save(product);
    }
	
    @Override
    public Product find(ObjectId tagId) {
        return get(tagId);
    }

	@Override
    public Product findByName(String name) {
    	List<Product> products = createQuery().field("name").equal(name).asList();
    	if (products.size() == 1)
    		return products.get(0);
    	else
    		return null;
    }

	@Override
    public List<Product> getProducts(int from, int count, String retailerId) {
		return createQuery().field("retailerId").equal(retailerId).offset(from).limit(count).asList();
    }
}
