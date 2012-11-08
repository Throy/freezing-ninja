// VisitDaoImpl

// Implementación de acceso a la colección de visitas de la base de datos.

package georeduy.server.dao;

import georeduy.server.logic.model.Contact;
import georeduy.server.logic.model.User;
import georeduy.server.logic.model.Visit;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.mongodb.WriteResult;

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
	
	@Override
	public Contact findByContactUserId (String userId) {
		List<Contact> contacts = createQuery().field("contactUserId").equal(userId).asList();
    	if (contacts.size() == 1)
    		return contacts.get(0);
    	else
    		return null;
	}

	// obtener contactos del usuario, sistema paginado
	@Override
	public List <Contact> findByUser (String userId, int from, int count) {
    	List <Contact> contacts = createQuery().field ("userId").equal(userId).offset(from).limit(count).asList();
    	resolveReferences (contacts);
    	return contacts;	
	}

	@Override
    public void deleteContact(Contact contact) {
	    this.deleteByQuery(createQuery().field("contactUserId").equal(contact.getContactUserId()));
    }
	
	@Override
	public boolean userHasContact (String userId, String contactId) {
		List<Contact> contacts = createQuery().field ("userId").equal(userId).field("contactUserId").equal(contactId).asList();
    	if (contacts.size() == 1)
    		return true;
    	else
    		return false;
	}
}
