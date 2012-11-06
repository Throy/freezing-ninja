package georeduy.server.dao;

import georeduy.server.logic.model.User;
import georeduy.server.logic.model.UserData;
import georeduy.server.logic.model.UserNotificationsTypes;
import georeduy.server.logic.model.Visit;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class UserDaoImpl extends BasicDAO<User, ObjectId> implements IUserDao {
	
	// nombres de los campos de Visit
	
	private static String idString = "id";
	
	// constructor

    public UserDaoImpl() {
        super(User.class, MongoConnectionManager.instance().getDb());
    }
    
    // funciones del dao

    @Override
    public void saveUser(User user) {
        this.save(user);
    }
    
    @Override
    public UpdateOperations<User> getUpdateOperations() {
    	return this.createUpdateOperations();
    }
    
    @Override
    public void update(User user, UpdateOperations<User> uo) {
    	Query<User> query = ds.createQuery(User.class).field("userName").equal(user.getUserName());
    	this.update(query, uo);
    }

    @Override
    public User find(ObjectId userId) {
        return get(userId);
    }

    @Override
    public List<User> findUsersByUserData(UserData userData) {
        return createQuery().field("userData").equal(userData).asList();
    }

    @Override
    public User findByUserName(String userName) {
    	List<User> users = createQuery().field("userName").equal(userName).asList();
    	if (users.size() == 1)
    		return users.get(0);
    	else
    		return null;
    }
    
    @Override
    public  List<User> searchByUsersName(String userName, int from, int count) {
    	return createQuery().field("id").notEqual(new ObjectId(User.Current().getId())).field("userName").containsIgnoreCase(userName).offset(from).limit(count).asList();
    }
    
    @Override
    public User findByExternalId(String id) {
    	List<User> users = createQuery().field("externalId").equal(id).asList();
    	if (users.size() == 1)
    		return users.get(0);
    	else
    		return null;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User ret = null;

        List<User> users = createQuery().field("userName").equal(username)
                .field("password").equal(password).asList();
        if (users.size() == 1) {
            ret = users.get(0);
        } else if (users.size() > 1) {
            throw new RuntimeException(
                    "username is duplicated in database. run guys!");
        }

        return ret;
    }

    // devuelve la configuración de tipos de notificaciones del usuario.
    @Override
    public UserNotificationsTypes getNotificationsTypes (String userId) {
    	return get (new ObjectId (userId)).getNotificationsTypes ();
    }

    // modifica la configuración de tipos de notificaciones del usuario.
    @Override
    public void setNotificationsTypes (String userId, UserNotificationsTypes notitypes) {
    	
		Query <User> query = ds.createQuery(User.class).field (idString).equal (new ObjectId (userId));
		UpdateOperations <User> ops = ds.createUpdateOperations(User.class).set ("notificationsTypes", notitypes);
		ds.update (query, ops);
    }
}
