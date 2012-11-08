package georeduy.server.dao;

import georeduy.server.logic.model.Tag;
import georeduy.server.logic.model.UserNotificationTag;

import java.util.List;

public interface IUserNotificationsTagDao {

    // agrega una etiqueta de notificaciones a la configuraci�n del usuario.
    public void saveUserNotificationsTag (UserNotificationTag userNotitag);

    // asigna la configuraci�n de etiquetas de notificaciones del usuario.
    public void saveUserNotificationsTags (String userId, List <UserNotificationTag> userNotitags);

    // devuelve la configuraci�n de etiquetas de notificaciones del usuario.
    public List <Tag> findByUser (String userId);

    // obtiene la etiqueta de notificaciones de la configuraci�n del usuario, si existe.
    public UserNotificationTag findByUserAndTag (String userId, String tag);
}
