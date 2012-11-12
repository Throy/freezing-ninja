package georeduy.server.logic.model;


import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Transient;
import com.google.code.morphia.utils.IndexDirection;

@Entity(value = "sites", noClassnameStored = true)
public class Site {

    @Id
    private String id;
    
    @Indexed(unique = true, value = IndexDirection.ASC)
    private String name;
    
    @Property
    private String description;
    
    @Property
    private String address;
    
    @Indexed(IndexDirection.GEO2D)
    public Double[] coordinates = new Double[2];
    
    // radio para visitas, en metros
    @Property
    private int radius;
    
    @Property
    private byte[] image;
    
    @Property
    private List<String> tags = new ArrayList<String>();
    
    // No pude hacer andar @Reference asi que lo hago asi
    @Transient
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

	/**
	 * @return the radius
	 */
	public int getRadius ()
	{
		return radius;
	}
	
	/**
	 * @param radius the radius to set
	 */
	public void setRadius (int radius)
	{
		this.radius = radius;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public List<String> getTagsIds() {
		return tags;
	}

	public List<Tag> getTags() {
		return realTags;
	}


	public void setTags(List<Tag> tags) {
		this.tags = new ArrayList<String>();
		for (Tag tag : tags) {
			this.tags.add(tag.getId());
		}
		
		this.realTags = tags;
	}
	
	
	public void addTag(Tag tag) {
		if (!tags.contains(tag.getId()))
			tags.add(tag.getId());
		
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
	    Site other = (Site) obj;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else if (!id.equals(other.id))
		    return false;
	    return true;
    }
}
