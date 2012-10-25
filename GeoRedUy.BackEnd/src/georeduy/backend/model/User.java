package georeduy.backend.model;

import java.util.List;


public class User {

    private String id;

    private String userName;
    
    private UserData userData;

    private List<String> roles;
    
    private String retailId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getId() {
        return id;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getRetailId() {
		return retailId;
	}

	public void setRetailId(String retailId) {
		this.retailId = retailId;
	}
    
    public boolean hasRole(String role) {
    	return roles.contains(role);
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
}

