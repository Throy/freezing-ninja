// IPurchaseDao

// Interfaz de acceso a la colección de compras de la base de datos.

package georeduy.server.dao;

import georeduy.server.logic.model.Purchase;

import java.util.List;

import org.bson.types.ObjectId;

public interface IPurchaseDao {

	// guardar compra en la base de datos
	public void savePurchase (Purchase purchase);

	// obtener compra a partir del id
	public Purchase find (ObjectId purchaseId);

	// obtener compras del usuario
	public List <Purchase> findByUser (String userId);

	// obtener visitas del usuario, sistema paginado
	public List <Purchase> findByUser (String userId, int from, int count);
}
