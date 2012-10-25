package georeduy.server.dao;

import georeduy.server.logic.model.Product;

import java.util.List;

import org.bson.types.ObjectId;

public interface IProductDao {

    public void saveProduct(Product product);

    public Product find(ObjectId productId);

    public Product findByName(String name);

    public List<Product> getProducts(int from, int count, String retailerId);
}
