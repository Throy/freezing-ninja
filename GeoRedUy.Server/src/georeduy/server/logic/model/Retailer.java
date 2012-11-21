package georeduy.server.logic.model;

import java.util.List;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.utils.IndexDirection;

@Entity(value = "retailers", noClassnameStored = true)
public class Retailer {
    @Id
    private String id;
    @Indexed(unique = true, value = IndexDirection.ASC)
    private String name;
    @Property
    private String description;
    @Property
    private byte[] image;
    @Property
    private String userId;
    @Property
    private List<String> storesIds;
    
    private User user;
    
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte image) {
		this.image = image;
	}

	public User getUser() {
		return user;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUser(User user) {
		this.userId = user.getId();
		this.user = user;
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
	    Retailer other = (Retailer) obj;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else if (!id.equals(other.id))
		    return false;
	    return true;
    }
}
