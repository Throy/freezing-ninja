// Comment

// clase de comentarios de la capa lógica del appserver.

package georeduy.server.logic.model;

import java.util.Date;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Transient;

@Entity(value = "purchaseItems", noClassnameStored = true)
public class PurchaseItem {

	// id del item
    @Id
    private String id;
    
    // id de la compra
    @Property
    private String purchaseId;
    
    // id del producto
    @Property
    private String productId;
    
    // cantidad de unidades
    @Property
    public String units;
    
    // No pude hacer andar @Reference asi que lo hago asi
    @Transient
    private Product realProduct;

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
	 * @return the productId
	 */
	public String getProductId ()
	{
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId (String productId)
	{
		this.productId = productId;
	}

	/**
	 * @return the units
	 */
	public String getUnits ()
	{
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits (String units)
	{
		this.units = units;
	}

	/**
	 * @return the realProduct
	 */
	public Product getRealProduct ()
	{
		return realProduct;
	}

	/**
	 * @param realProduct the realProduct to set
	 */
	public void setRealProduct (Product realProduct)
	{
		this.realProduct = realProduct;
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
		PurchaseItem other = (PurchaseItem) obj;
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
