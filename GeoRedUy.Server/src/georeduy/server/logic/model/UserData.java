package georeduy.server.logic.model;

import java.util.Date;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.PrePersist;
import com.google.code.morphia.annotations.Property;

@Embedded
public class UserData {

    @Property
    private String email;
    @Property
    private Date creationDate;
    @Property
    private Date lastUpdateDate;
    @Property
    private String name;
    @Property
    private String lastName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @PrePersist
    public void prePersist() {
        this.creationDate = (creationDate == null) ? new Date() : creationDate;
        this.lastUpdateDate = (lastUpdateDate == null) ? creationDate : new Date();
    }
}
