// MapOverlayItem

// item para desplegar en el mapa.

package georeduy.client.maps;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class MapOverlayItem extends OverlayItem
{
	// id del item.
	private int _id;

	// constructor básico - no usar.
	
	public MapOverlayItem (GeoPoint location, String title, String snippet)
	{
		super (location, title, snippet);
	}
	
	// constructor con id del item.

	public MapOverlayItem (GeoPoint location, String title, String snippet, int id)
	{
		super (location, title, snippet);
		
		_id = id;
	}
	
	// getter del id del item.

	public int getId() {
		return _id;
	}
}
