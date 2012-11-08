// Visit

// clase de visitas de la capa lógica del appserver.

package georeduy.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Purchase {

    private String id;
    
    private String userId;
    
    private String retailerId;
    
    private String storeId;
    
    public Date date;
    
    private String pricetotal;

    private List <PurchaseItem> items = new ArrayList <PurchaseItem>();

    private List <Review> reviews = new ArrayList <Review>();
    
    // No pude hacer andar @Reference asi que lo hago asi
    private Retailer realRetailer;
    
    // No pude hacer andar @Reference asi que lo hago asi
    private RetailStore realStore;    
    
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
	 * @return the userId
	 */
	public String getUserId ()
	{
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId (String userId)
	{
		this.userId = userId;
	}

	/**
	 * @return the retailerId
	 */
	public String getRetailerId ()
	{
		return retailerId;
	}

	/**
	 * @param retailerId the retailerId to set
	 */
	public void setRetailerId (String retailerId)
	{
		this.retailerId = retailerId;
	}

	/**
	 * @return the storeId
	 */
	public String getStoreId ()
	{
		return storeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId (String storeId)
	{
		this.storeId = storeId;
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
	 * @return the pricetotal
	 */
	public String getPricetotal ()
	{
		return pricetotal;
	}

	/**
	 * @param pricetotal the pricetotal to set
	 */
	public void setPricetotal (String pricetotal)
	{
		this.pricetotal = pricetotal;
	}

	/**
	 * @return the items
	 */
	public List <PurchaseItem> getItems ()
	{
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems (List <PurchaseItem> items)
	{
		this.items = items;
	}

	/**
	 * @return the reviews
	 */
	public List <Review> getReviews ()
	{
		return reviews;
	}

	/**
	 * @param reviews the reviews to set
	 */
	public void setReviews (List <Review> reviews)
	{
		this.reviews = reviews;
	}

	/**
	 * @return the realRetailer
	 */
	public Retailer getRealRetailer ()
	{
		return realRetailer;
	}

	/**
	 * @param realRetailer the realRetailer to set
	 */
	public void setRealRetailer (Retailer realRetailer)
	{
		this.realRetailer = realRetailer;
	}

	/**
	 * @return the realStore
	 */
	public RetailStore getRealStore ()
	{
		return realStore;
	}

	/**
	 * @param realStore the realStore to set
	 */
	public void setRealStore (RetailStore realStore)
	{
		this.realStore = realStore;
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
		Purchase other = (Purchase) obj;
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
