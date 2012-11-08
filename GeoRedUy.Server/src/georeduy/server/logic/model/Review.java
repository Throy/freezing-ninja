// Review

// clase de etiqueta de notificaciones de un usuario de la capa lógica del appserver.
// su existencia significa que el usuario quiere recibir notificaciones de dicho tipo.

package georeduy.server.logic.model;

import java.util.Date;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;

@Entity(value = "reviews", noClassnameStored = true)
public class Review {
	
    @Id
    private String id;

    @Property
    private String purchaseId;

    // fecha de publicación
    @Property
    private Date date;

    // texto del comentario
    @Property
    private String text;
    
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
	 * @return the purchaseId
	 */
	public String getPurchaseId ()
	{
		return purchaseId;
	}

	/**
	 * @param purchaseId the purchaseId to set
	 */
	public void setPurchaseId (String purchaseId)
	{
		this.purchaseId = purchaseId;
	}

	/**
	 * @return the date
	 */
	public Date getDate ()
	{
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate (Date date)
	{
		this.date = date;
	}

	/**
	 * @return the text
	 */
	public String getText ()
	{
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText (String text)
	{
		this.text = text;
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
	    Review other = (Review) obj;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else if (!id.equals(other.id))
		    return false;
	    return true;
    }
}
