// Comment

// clase de comentarios de la capa lógica del appserver.

package georeduy.server.logic.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Reference;
import com.google.code.morphia.utils.IndexDirection;

@Entity(value = "comments", noClassnameStored = true)
public class Comment {

    @Id
    private String id;
    
    @Property
    private String visitId;
    
    // fecha de publicación
    @Property
    public Date date;
    
    // texto del comentario
    @Property
    private String text;
    
    // *** falta contenido multimedia del comentario ***
    
	public String getId ()
	{
		return id;
	}

	public void setId (String id)
	{
		this.id = id;
	}

	public String getVisitId ()
	{
		return visitId;
	}

	public void setVisitId (String visitId)
	{
		this.visitId = visitId;
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

	public String getText ()
	{
		return text;
	}

	public void setText (String text)
	{
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode ());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals (Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass () != obj.getClass ())
			return false;
		Comment other = (Comment) obj;
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
