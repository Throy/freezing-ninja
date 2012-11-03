package georeduy.server.logic.controllers;

import georeduy.server.dao.IProductDao;
import georeduy.server.dao.IRetailStoreDao;
import georeduy.server.dao.IStoreProductDao;
import georeduy.server.dao.ProductDaoImpl;
import georeduy.server.dao.RetailStoreDaoImpl;
import georeduy.server.dao.StoreProductDaoImpl;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Product;
import georeduy.server.logic.model.RetailStore;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.StoreProduct;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public class ProductsController {
	// instancia del singleton
	private static ProductsController s_instance = null;

	// daos
	private IProductDao productDao = new ProductDaoImpl();
	private IStoreProductDao storeProductsDao = new StoreProductDaoImpl();
	
	// constructores
	
	public ProductsController() {
	}

	public static ProductsController getInstance() {
		if (s_instance == null) {
			s_instance = new ProductsController();
		}

		return s_instance;
	}
	
	// funciones del controlador
	
	public void newProduct(Product product, String retailerId) throws Exception {
		product.setRetailerId(retailerId);
		productDao.saveProduct(product);		
	}
	
	public void addStoreProduct(String storeId, String productName) throws Exception {
		StoreProduct storeProduct = new StoreProduct();
		
		// asignar id del producto
		Product product = productDao.findByName(productName);
		storeProduct.setProductId(product.getId());

		// asignar id del local
		storeProduct.setStoreId(storeId);
		
		// asignar producto al local
		storeProductsDao.saveStoreProduct(storeProduct);
	}
	
	public List<Product> getProducts(int from, int count, String retailerId) {
		return productDao.getProducts(from, count, retailerId);
	}
	
	public List<Product> getStoreProducts(int from, int count, String storeId) {
		return storeProductsDao.getStoreProducts(from, count, storeId);
	}
}
