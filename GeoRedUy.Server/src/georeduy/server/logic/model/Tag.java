package georeduy.server.logic.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Transient;
import com.google.code.morphia.utils.IndexDirection;

@Entity(value = "tags", noClassnameStored = true)
public class Tag {
    @Id
    private String id;
    @Indexed(unique = true, sparse = true, value = IndexDirection.ASC)
    private String name;
    @Property
    private String description;
    
    @Transient
    private boolean isChecked;
    
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
	
	/**
	 * @return the isChecked
	 */
	public boolean isChecked ()
	{
		return isChecked;
	}

	/**
	 * @param isChecked the isChecked to set
	 */
	public void setChecked (boolean isChecked)
	{
		this.isChecked = isChecked;
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
	    Tag other = (Tag) obj;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else if (!id.equals(other.id))
		    return false;
	    return true;
    }
}
