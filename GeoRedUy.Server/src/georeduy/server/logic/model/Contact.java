// Visit

// clase de visitas de la capa lógica del appserver.

package georeduy.server.logic.model;

import java.util.Date;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;

@Entity(value = "contacts", noClassnameStored = true)
public class Contact {

    @Id
    private String id;
    
    @Property
    private String userId;
    
    @Property
    private String contactUserId;
    
    private User user;
    
    private User contactUser;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContactUserId() {
		return contactUserId;
	}

	public void setContactUserId(String contactUserId) {
		this.contactUserId = contactUserId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getContactUser() {
		return contactUser;
	}

	public void setContactUser(User contactUser) {
		this.contactUser = contactUser;
	}

	@Override
	public int hashCode ()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode ());
		return result;
	}

	@Override
	public boolean equals (Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass () != obj.getClass ())
			return false;
		Contact other = (Contact) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals (other.id))
			return false;
		return true;
	}
}
