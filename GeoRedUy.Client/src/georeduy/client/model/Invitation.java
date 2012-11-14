package georeduy.client.model;

public class Invitation {
    
    private String email;
    
    private String username;
    
    private String message;

	/**
	 * @return the email
	 */
	public String getEmail ()
	{
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail (String email)
	{
		this.email = email;
	}

	/**
	 * @return the username
	 */
	public String getUsername ()
	{
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername (String username)
	{
		this.username = username;
	}

	/**
	 * @return the message
	 */
	public String getMessage ()
	{
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage (String message)
	{
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode ());
		result = prime * result + ((message == null) ? 0 : message.hashCode ());
		result = prime * result + ((username == null) ? 0 : username.hashCode ());
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
		Invitation other = (Invitation) obj;
		if (email == null)
		{
			if (other.email != null)
				return false;
		}
		else if (!email.equals (other.email))
			return false;
		if (message == null)
		{
			if (other.message != null)
				return false;
		}
		else if (!message.equals (other.message))
			return false;
		if (username == null)
		{
			if (other.username != null)
				return false;
		}
		else if (!username.equals (other.username))
			return false;
		return true;
	}
}
