package georeduy.server.logic.controllers;

import georeduy.server.dao.IProductDao;
import georeduy.server.dao.IPurchaseDao;
import georeduy.server.dao.IRetailStoreDao;
import georeduy.server.dao.IRetailerDao;
import georeduy.server.dao.IStoreProductDao;
import georeduy.server.dao.ProductDaoImpl;
import georeduy.server.dao.PurchaseDaoImpl;
import georeduy.server.dao.RetailStoreDaoImpl;
import georeduy.server.dao.RetailerDaoImpl;
import georeduy.server.dao.StoreProductDaoImpl;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Product;
import georeduy.server.logic.model.Purchase;
import georeduy.server.logic.model.PurchaseItem;
import georeduy.server.logic.model.RetailStore;
import georeduy.server.logic.model.Retailer;
import georeduy.server.logic.model.StoreProduct;
import georeduy.server.logic.model.User;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

public class ProductsController {
	// instancia del singleton
	private static ProductsController s_instance = null;

	// daos
	private static IProductDao productDao = new ProductDaoImpl();
	private static IPurchaseDao purchaseDao = new PurchaseDaoImpl();
	private static IStoreProductDao storeProductsDao = new StoreProductDaoImpl();
	private static IRetailerDao retailerDao = new RetailerDaoImpl ();
	private static IRetailStoreDao storeDao = new RetailStoreDaoImpl();
	private static IStoreProductDao storeProductDao = new StoreProductDaoImpl();
	
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
	
	// crear producto
	public void newProduct(Product product, String retailerId) throws Exception {
		product.setRetailerId(retailerId);
		productDao.saveProduct(product);		
	}
	
	// agegar producto a un local
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
	
	// obtener productos de una empresa
	public List<Product> getProducts(int from, int count, String retailerId) {
		return productDao.getProducts(from, count, retailerId);
	}
	
	// obtener productos de un local
	public List<Product> getStoreProducts(int from, int count, String storeId) {
		return storeProductsDao.getStoreProducts(from, count, storeId);
	}
	
	// realizar compra de productos
	public void newPurchase (Purchase purchase) throws Exception {
		
		// comprobar que los productos pertenecen al local y hay al menos 1 unidad, calcular precio total
		Double pricetotal = 0.0;
		for (PurchaseItem item : purchase.getItems ()) {
			if (storeProductDao.find (item.getProductId (), purchase.getStoreId ()) == null) {
				throw new Exception (GeoRedConstants.PURCHASE_SAVE_INCONSISTENT_DATA);
			}
			if (item.getUnits () <= 0) {
				throw new Exception (GeoRedConstants.PURCHASE_SAVE_INCONSISTENT_DATA);
			}
			pricetotal += Double.parseDouble (productDao.find (new ObjectId (item.getProductId ())).getPrice ()) * item.getUnits ();
		}
		
		// comprobar que el local pertenece a la empresa
		Retailer retailer = retailerDao.find (new ObjectId (purchase.getRetailerId ()));
		RetailStore store = storeDao.find (new ObjectId (purchase.getStoreId ()));
		if (! retailer.getId ().equals (store.getRetailerId ())) {
			throw new Exception (GeoRedConstants.PURCHASE_SAVE_INCONSISTENT_DATA);
		}
		
		// asignar datos
		purchase.setDate (new Date ());
		purchase.setUserId (User.Current ().getId ());
		purchase.setPricetotal (pricetotal.toString ());
		
		// guardar compra
		purchaseDao.savePurchase (purchase);
	}
	
	// realizar compra de productos
	public List <Purchase> getPurchasesByUser (String userId) {
		return purchaseDao.findByUser (userId);
	}
}
