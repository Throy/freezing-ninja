package georeduy.server.dao;

import georeduy.server.logic.model.Tag;
import georeduy.server.logic.model.UserNotificationTag;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;

public class UserNotificationsTagDaoImpl
		extends BasicDAO <UserNotificationTag, ObjectId>
		implements IUserNotificationsTagDao {

	// daos
	
	private static ITagDao tagDao = new TagDaoImpl();
	
	// constructor
	
    public UserNotificationsTagDaoImpl() {
        super (UserNotificationTag.class, MongoConnectionManager.instance().getDb());
    }
    
    // funciones del dao

    // agrega una etiqueta de notificaciones a la configuración del usuario.
	@Override
    public void saveUserNotificationsTag (UserNotificationTag userNotitag) {
	    this.save (userNotitag);
    }

    // asigna la configuración de etiquetas de notificaciones del usuario.
    public void saveUserNotificationsTags (String userId, List <UserNotificationTag> userNotitags) {
    	
    	// borrar las etiquetas viejas
    	ds.delete (ds.createQuery (UserNotificationTag.class).field ("userId").equal (userId));
    	
    	// asignar las etiquetas nuevas
    	for (UserNotificationTag userNotitag : userNotitags) {
    	    this.save (userNotitag);
    	}
    }

    // devuelve la configuración de etiquetas de notificaciones del usuario.
    @Override
    public List <Tag> findByUser (String userId)
	{
    	// obtener todas las etiquetas del sistema.
    	List <Tag> tags = tagDao.getTags (0, 99);
    	
    	// asignar el valor de marcado
    	for (Tag tag : tags) {
    		tag.setChecked (createQuery().field("userId").equal(userId).field("tagId").equal(tag.getId ()).get () != null);
    	}
    	
    	return tags;
	}

    // obtiene la etiqueta de notificaciones de la configuración del usuario, si existe.
    public UserNotificationTag findByUserAndTag (String userId, String tagId) {
    	return createQuery().field("userId").equal(userId).field("tagId").equal(tagId).get ();
    }
}
