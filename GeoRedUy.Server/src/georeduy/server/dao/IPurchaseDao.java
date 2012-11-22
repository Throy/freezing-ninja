// IPurchaseDao

// Interfaz de acceso a la colección de compras de la base de datos.

package georeduy.server.dao;

import georeduy.server.logic.model.Purchase;
import georeduy.server.logic.model.Review;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

public interface IPurchaseDao {

	// guardar compra en la base de datos
	public void savePurchase (Purchase purchase) throws Exception;

	// guardar evaluación en la base de datos
	public void addPurchaseReview (ObjectId purchaseId, Review review);

	// obtener compra a partir del id
	public Purchase find (ObjectId purchaseId);

	// obtener compras del usuario
	public List <Purchase> findByUser (String userId);

	// obtener visitas del usuario, sistema paginado
	public List <Purchase> findByUser (String userId, int from, int count);
	
	public List <Purchase> findByPeriod (Date start, Date end);
}
