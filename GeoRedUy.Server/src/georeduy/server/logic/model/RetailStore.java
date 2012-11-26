package georeduy.server.logic.model;


import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.utils.IndexDirection;

@Entity(value = "retail_stores", noClassnameStored = true)
public class RetailStore {

    @Id
    private String id;
    @Indexed(unique = true, value = IndexDirection.ASC)
    private String name;
    @Property
    private String address;
    @Property
    private String paypalMail;
    @Property
    private String phoneNumber;
    @Indexed(IndexDirection.GEO2D)
    public Double[] coordinates = new Double[2];
    @Property
    private String imageUrl;
    @Property
    private String retailerId;
    @Property
    private List<String> productsIds;

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
	

	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPayPalMail() {
		return paypalMail;
	}


	public void setPayPalMail(String mail) {
		this.paypalMail = mail;
	}
	

	public Double[] getCoordinates() {
		return coordinates;
	}


	public void setCoordinates(Double[] coordinates) {
		this.coordinates = coordinates;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	public String getRetailerId() {
		return retailerId;
	}


	public void setRetailerId(String retailerId) {
		this.retailerId = retailerId;
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
	    RetailStore other = (RetailStore) obj;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else if (!id.equals(other.id))
		    return false;
	    return true;
    }
}
