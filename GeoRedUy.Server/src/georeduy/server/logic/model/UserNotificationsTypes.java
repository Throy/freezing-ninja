// UserNotificationsTypes

// clase de tipos de notificaciones de un usuario de la capa lógica del appserver.
// true significa que el usuario quiere recibir notificaciones de dicho tipo.

package georeduy.server.logic.model;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Property;

@Embedded
public class UserNotificationsTypes {

    @Property
    private boolean notitype1_contactsVisits = false;
	
    @Property
    private boolean notitype2_contactsComments = false;
	
    @Property
    private boolean notitype3_contactsReviews = false;
	
    @Property
    private boolean notitype4_sites = false;
	
    @Property
    private boolean notitype5_products = false;
	
    @Property
    private boolean notitype6_events = false;

	/**
	 * @return the notitype1_contactsVisits
	 */
	public boolean isNotitype1_contactsVisits ()
	{
		return notitype1_contactsVisits;
	}

	/**
	 * @param notitype1_contactsVisits the notitype1_contactsVisits to set
	 */
	public void setNotitype1_contactsVisits (boolean notitype1_contactsVisits)
	{
		this.notitype1_contactsVisits = notitype1_contactsVisits;
	}

	/**
	 * @return the notitype2_contactsComments
	 */
	public boolean isNotitype2_contactsComments ()
	{
		return notitype2_contactsComments;
	}

	/**
	 * @param notitype2_contactsComments the notitype2_contactsComments to set
	 */
	public void setNotitype2_contactsComments (boolean notitype2_contactsComments)
	{
		this.notitype2_contactsComments = notitype2_contactsComments;
	}

	/**
	 * @return the notitype3_contactsReviews
	 */
	public boolean isNotitype3_contactsReviews ()
	{
		return notitype3_contactsReviews;
	}

	/**
	 * @param notitype3_contactsReviews the notitype3_contactsReviews to set
	 */
	public void setNotitype3_contactsReviews (boolean notitype3_contactsReviews)
	{
		this.notitype3_contactsReviews = notitype3_contactsReviews;
	}

	/**
	 * @return the notitype4_sites
	 */
	public boolean isNotitype4_sites ()
	{
		return notitype4_sites;
	}

	/**
	 * @param notitype4_sites the notitype4_sites to set
	 */
	public void setNotitype4_sites (boolean notitype4_sites)
	{
		this.notitype4_sites = notitype4_sites;
	}

	/**
	 * @return the notitype5_products
	 */
	public boolean isNotitype5_products ()
	{
		return notitype5_products;
	}

	/**
	 * @param notitype5_products the notitype5_products to set
	 */
	public void setNotitype5_products (boolean notitype5_products)
	{
		this.notitype5_products = notitype5_products;
	}

	/**
	 * @return the notitype6_events
	 */
	public boolean isNotitype6_events ()
	{
		return notitype6_events;
	}

	/**
	 * @param notitype6_events the notitype6_events to set
	 */
	public void setNotitype6_events (boolean notitype6_events)
	{
		this.notitype6_events = notitype6_events;
	}
}
