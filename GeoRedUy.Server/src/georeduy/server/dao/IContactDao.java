// IVisitDao

// Interfaz de acceso a la colección de visitas de la base de datos.

package georeduy.server.dao;

import georeduy.server.logic.model.Contact;

import java.util.List;

import org.bson.types.ObjectId;

public interface IContactDao {

	// guardar contacto en la base de datos
	public void saveContact (Contact contact);

	// obtener contacto a partir del id
	public Contact find (ObjectId contactId);
	
	public Contact findByContactUserId (String userId);

	// obtener contactos del usuario, sistema paginado
	public List <Contact> findByUser (String userId, int from, int count);
	
	public void deleteContact(Contact contact);
}
