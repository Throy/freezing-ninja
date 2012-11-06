package georeduy.server.dao;

import georeduy.server.logic.model.User;
import georeduy.server.logic.model.UserData;
import georeduy.server.logic.model.UserNotificationsTypes;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.query.UpdateOperations;

public interface IUserDao {

    public void saveUser(User user);
    
    public UpdateOperations<User> getUpdateOperations();
    
    public void update(User user, UpdateOperations<User> uo);

    public User find(ObjectId userId);

    public User findByUserName(String user);
    
    public User findByExternalId(String id);

    public List<User> findUsersByUserData(UserData userData);

    public User findByUsernameAndPassword(String username, String password);
    
    public List<User> searchByUsersName(String user, int from, int count);

    // devuelve la configuración de tipos de notificaciones del usuario.
    public UserNotificationsTypes getNotificationsTypes (String userId);

    // modifica la configuración de tipos de notificaciones del usuario.
    public void setNotificationsTypes (String userId, UserNotificationsTypes notitypes);
}
