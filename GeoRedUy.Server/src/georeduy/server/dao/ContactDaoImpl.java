// VisitDaoImpl

// Implementación de acceso a la colección de visitas de la base de datos.

package georeduy.server.dao;

import georeduy.server.logic.model.Contact;
import georeduy.server.logic.model.Visit;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;

public class ContactDaoImpl extends BasicDAO <Contact, ObjectId> implements IContactDao {
	// daos externos
	private IUserDao userDao =  new UserDaoImpl();
	
	// constructor
    public ContactDaoImpl() {
        super (Contact.class, MongoConnectionManager.instance().getDb());
    }
    
    // resolver referencias de claves foráneas
    private void resolveReferences (List <Contact> contacts) {
    	for (Contact contact : contacts) {
    		resolveReferences (contact);
    	}
    }

    private void resolveReferences (Contact contact) {
    	contact.setUser (userDao.find (new ObjectId (contact.getUserId ())));
    	contact.setContactUser(userDao.find (new ObjectId (contact.getContactUserId ())));
    }
    
    // funciones del dao

	// guardar visita en la base de datos
	@Override
	public void saveContact (Contact contact) {
	    this.save (contact);
	}

	// obtener contacto a partir del id
	@Override
	public Contact find (ObjectId contactId) {
        return get (contactId);
	}

	// obtener contactos del usuario, sistema paginado
	@Override
	public List <Contact> findByUser (String userId, int from, int count) {
    	List <Contact> contacts = createQuery().field ("userId").equal(userId).offset(from).limit(count).asList();
    	resolveReferences (contacts);
    	return contacts;	
	}
}
