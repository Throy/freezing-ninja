package georeduy.client.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {

    private String id;
    private String name;
    private String description;
    private String address;
    private Double[] coordinates = new Double[2];
    private String imageUrl;
    private Date date;
    private List<Tag> realTags = new ArrayList<Tag>();

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


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
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


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}
	
	public List<Tag> getTags() {
		return realTags;
	}


	public void setTags(List<Tag> tags) {
		this.realTags = tags;
	}
	
	public void addTag(Tag tag) {
		this.realTags.add(tag);
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
	    Event other = (Event) obj;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else if (!id.equals(other.id))
		    return false;
	    return true;
    }
}
