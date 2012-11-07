// UserNotificationsTag

// clase de etiqueta de notificaciones de un usuario de la capa lógica del appserver.
// su existencia significa que el usuario quiere recibir notificaciones de dicho tipo.

package georeduy.server.logic.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;

@Entity(value = "user_notifications_tags", noClassnameStored = true)
public class UserNotificationTag {
    @Id
    private String id;

    @Property
    private String userId;

    @Property
    private String tagId;
    
	/**
	 * @return the id
	 */
	public String getId ()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId (String id)
	{
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public String getUserId ()
	{
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId (String userId)
	{
		this.userId = userId;
	}

	/**
	 * @return the tagId
	 */
	public String getTagId ()
	{
		return tagId;
	}

	/**
	 * @param tagId the tagId to set
	 */
	public void setTagId (String tagId)
	{
		this.tagId = tagId;
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((id == null) ? 0 : id.hashCode());
	    return result;
    }
	
	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    UserNotificationTag other = (UserNotificationTag) obj;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else if (!id.equals(other.id))
		    return false;
	    return true;
    }
}
