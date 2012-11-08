// IPurchaseDao

// Implementación de acceso a la colección de compras de la base de datos.

package georeduy.server.dao;

import georeduy.server.logic.model.Comment;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Purchase;
import georeduy.server.logic.model.PurchaseItem;
import georeduy.server.logic.model.Product;
import georeduy.server.logic.model.RetailStore;
import georeduy.server.logic.model.Retailer;
import georeduy.server.logic.model.Review;
import georeduy.server.logic.model.Visit;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class PurchaseDaoImpl extends BasicDAO <Purchase, ObjectId> implements IPurchaseDao {
	
	// nombres de los campos de Visit
	
	private static String userIdString = "userId";
	private static String idString = "id";
	private static String reviewsString = "reviews";
	
	// daos externos

	private static IProductDao productDao = new ProductDaoImpl();
	private static IRetailerDao retailerDao = new RetailerDaoImpl ();
	private static IRetailStoreDao storeDao = new RetailStoreDaoImpl();
	
	// constructor
	
    public PurchaseDaoImpl() {
        super (Purchase.class, MongoConnectionManager.instance().getDb());
    }
    
    // resolver referencias de claves foráneas
    
    private void resolveReferences (List <Purchase> purchases) {
    	for (Purchase purchase : purchases) {
    		resolveReferences (purchase);
    	}
    }

    private void resolveReferences (Purchase purchase) {
    	purchase.setRealRetailer (retailerDao.find (new ObjectId (purchase.getRetailerId ())));
    	purchase.setRealStore (storeDao.find (new ObjectId (purchase.getStoreId ())));
    	
    	for (PurchaseItem item : purchase.getItems ()) {
    		item.setRealProduct (productDao.find (new ObjectId (item.getProductId ())));
    	}
    }
    
    // funciones del dao

	// guardar compra en la base de datos
	public void savePurchase (Purchase purchase)  {
	    this.save (purchase);
	}

	// guardar evaluación en la base de datos
	@Override
	public void addPurchaseReview (ObjectId purchaseId, Review review) {
		Query <Purchase> query = ds.createQuery (Purchase.class).field (idString).equal (purchaseId);
		UpdateOperations <Purchase> ops = ds.createUpdateOperations (Purchase.class).add (reviewsString, review);
		ds.update (query, ops);
	}

	// obtener compra a partir del id
	public Purchase find (ObjectId purchaseId) {
		Purchase purchase = get (purchaseId);
        resolveReferences (purchase);
        return purchase;
	}

	// obtener compras del usuario
	public List <Purchase> findByUser (String userId) {
    	List <Purchase> purchases = createQuery().field (userIdString).equal(userId).asList();
    	resolveReferences (purchases);
    	return purchases;
	}

	// obtener visitas del usuario, sistema paginado
	public List <Purchase> findByUser (String userId, int from, int count) {
    	List <Purchase> purchases = createQuery().field (userIdString).equal(userId).offset(from).limit(count).asList();
    	resolveReferences (purchases);
    	return purchases;
	}
}
