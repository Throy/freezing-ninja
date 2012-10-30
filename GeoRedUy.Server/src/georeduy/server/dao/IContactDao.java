// IVisitDao

// Interfaz de acceso a la colección de visitas de la base de datos.

package georeduy.server.dao;

import georeduy.server.logic.model.Contact;

import java.util.List;

import org.bson.types.ObjectId;

public interface IContactDao {

	// guardar visita en la base de datos
	public void saveContact (Contact contact);

	// obtener visita a partir del id
	public Contact find (ObjectId contactId);

	// obtener visitas del usuario, sistema paginado
	public List <Contact> findByUser (String userId, int from, int count);
}
