package georeduy.server.logic.controllers;

import georeduy.server.dao.ContactDaoImpl;
import georeduy.server.dao.IContactDao;
import georeduy.server.dao.IUserDao;
import georeduy.server.dao.UserDaoImpl;
import georeduy.server.logic.model.Contact;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Tag;
import georeduy.server.logic.model.User;

import java.util.List;

import org.bson.types.ObjectId;

public class ClientsController {
	private static ClientsController s_instance = null;
	
	private IUserDao userDao =  new UserDaoImpl();
	
	private IContactDao contactDao =  new ContactDaoImpl();
	
	public ClientsController() {
	}

	public static ClientsController getInstance() {
		if (s_instance == null) {
			s_instance = new ClientsController();
		}

		return s_instance;
	}
	
	public void AddContact(Contact contact) throws Exception {
        if (userDao.find(new ObjectId(contact.getContactUserId())) != null) {
        	contact.setUserId(User.Current().getId());
        	contactDao.saveContact(contact);
        } else {
        	throw new Exception(GeoRedConstants.USER_DOES_NOT_EXIST);
        }
	}
	
	public List<Contact> Get(int from, int count) {
		return contactDao.findByUser(User.Current().getId(), from, count);
	}
	
	public List<User> SearchUsersByName(String name, int from, int count) {
		return userDao.searchByUsersName(name, from, count);
	}
}
