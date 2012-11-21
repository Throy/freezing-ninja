package georeduy.server.dao;

import georeduy.server.logic.model.Product;
import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.Tag;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;

public class ProductDaoImpl extends BasicDAO<Product, ObjectId> implements IProductDao {

    public ProductDaoImpl() {
        super(Product.class, MongoConnectionManager.instance().getDb());
    }
    
    private Product removeImage(Product product) {
    	product.setImage(null);
    	return product;
    }
    
    private List<Product> removeImage(List<Product> products) {
    	for (Product product : products) {
    		removeImage(product);
		}
    	return products;
    }

	@Override
    public void saveProduct(Product product) {
	    this.save(product);
    }
	
    @Override
    public Product find(ObjectId tagId) {
        return removeImage(get(tagId));
    }

	@Override
    public Product findByName(String name) {
    	List<Product> products = createQuery().field("name").equal(name).asList();
    	if (products.size() == 1) {
    		return removeImage(products.get(0));    		
    	} else {
    		return null;
    	}
    }

	@Override
    public List<Product> getProducts(int from, int count, String retailerId) {
		List<Product> products = createQuery().field("retailerId").equal(retailerId).offset(from).limit(count).asList();
		return removeImage(products);
	}
	
	// Llamar este metodo cuando se quiere la imagen de un sitio!
	@Override
    public byte[] getProductImage(String id) {
	    Product product = get(new ObjectId(id));
	    return product.getImage();
    }
}
