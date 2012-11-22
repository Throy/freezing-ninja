package georeduy.server.logic.model;


import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Transient;
import com.google.code.morphia.utils.IndexDirection;

@Entity(value = "users", noClassnameStored = true)
public class User {

    @Id
    private String id;
    
    @Indexed(unique = true, sparse = true, value = IndexDirection.ASC)
    private String userName;
    
    @Indexed
    private String password;
    
    @Embedded
    private UserData userData;
    
    @Embedded
    private UserNotificationsTypes notificationsTypes;
    
    @Property
    private List<String> roles;
    
    @Property
    private String retailId;
    
    @Property
    private String externalId;
    
    @Transient
    public Double[] coordinates = new Double[2];
    
    @Transient
    public MapRect mapRect = new MapRect();
    
    @Transient
    private String token;
    
    private static User s_currentUser;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    /**
	 * @return the notificationsTypes
	 */
	public UserNotificationsTypes getNotificationsTypes ()
	{
		return notificationsTypes;
	}

	/**
	 * @param notificationsTypes the notificationsTypes to set
	 */
	public void setNotificationsTypes (UserNotificationsTypes notificationsTypes)
	{
		this.notificationsTypes = notificationsTypes;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    public void addRole(String role) {
        this.roles.add(role);
    }

    public String getRetailId() {
		return retailId;
	}

	public void setRetailId(String retailId) {
		this.retailId = retailId;
	}
	

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Double[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Double[] coordinates) {
		this.coordinates = coordinates;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public MapRect getMapRect() {
		return mapRect;
	}

	public void setMapRect(MapRect mapRect) {
		this.mapRect = mapRect;
	}

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 29 * hash + (this.userName != null ? this.userName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if ((this.userName == null) ? (other.userName != null) : !this.userName.equals(other.userName)) {
            return false;
        }
        return true;
    }
    
	public static void setCurrent(User user)
	{
		s_currentUser = user;
	}
	
	public static User Current() {
    	return s_currentUser;
	}
}
