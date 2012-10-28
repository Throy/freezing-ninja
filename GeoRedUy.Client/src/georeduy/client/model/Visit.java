// Visit

// clase de visitas que para comunicarse con el appserver.

package georeduy.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Visit {

    private String id;
    
    private String userId;
    
    private String siteId;
    
    public Date date;
    
    // No pude hacer andar @Reference asi que lo hago asi
    private User realUser;
    
    // No pude hacer andar @Reference asi que lo hago asi
    private Site realSite;

	public String getId ()
	{
		return id;
	}

	public void setId (String id)
	{
		this.id = id;
	}

	public String getUserId ()
	{
		return userId;
	}

	public void setUserId (String userId)
	{
		this.userId = userId;
	}

	public String getSiteId ()
	{
		return siteId;
	}

	public void setSiteId (String siteId)
	{
		this.siteId = siteId;
	}

	public Date getDate ()
	{
		return date;
	}

	public void setDate (Date date)
	{
		this.date = date;
	}

	public User getRealUser ()
	{
		return realUser;
	}

	public void setRealUser (User realUser)
	{
		this.realUser = realUser;
	}

	public Site getRealSite ()
	{
		return realSite;
	}

	public void setRealSite (Site realSite)
	{
		this.realSite = realSite;
	}

	@Override
	public int hashCode ()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode ());
		return result;
	}

	@Override
	public boolean equals (Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass () != obj.getClass ())
			return false;
		Visit other = (Visit) obj;
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
