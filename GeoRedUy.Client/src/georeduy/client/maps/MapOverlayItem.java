// MapOverlayItem

// item para desplegar en el mapa.

package georeduy.client.maps;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class MapOverlayItem extends OverlayItem
{
	// id del item.
	private String _id;
	private String _address;	
	
	// constructor con id del item.

	public MapOverlayItem (GeoPoint location, String title, String snippet, String address, String id)
	{
		super (location, title, snippet);
		
		_id = id;
		_address = address;		
	}
	
	// getter del id del item.

	public String getId() {
		return _id;
	}
	
	public String getAddress() {
		return _address;
	}
}
