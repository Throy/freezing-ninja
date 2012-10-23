package georeduy.client.activities;

import java.util.List;

import georeduy.client.activities.R;
import georeduy.client.util.LocationListenerImpl;

import georeduy.client.maps.SiteMapOverlay;
import georeduy.client.maps.MapOverlayItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class MapaActivity extends MapActivity {
	// mapa
	private MapView mapView;

	// cordenadas de Atenas
	private static final int latitudeE6 = 37985339;
	private static final int longitudeE6 = 23716735;
	private static final int latitudeE5 = 37985839;
	private static final int longitudeE5 = 23716035;
	private LocationManager locationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView (georeduy.client.activities.R.layout.activity_main);

		// obtener mapa
		mapView = (MapView) findViewById (georeduy.client.activities.R.id.map_view);
		mapView.setBuiltInZoomControls (true);

		// inicializar overlay de sitios, con íconos carrito
		List <Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable (R.drawable.cart);
		SiteMapOverlay siteMapOverlay = new SiteMapOverlay (drawable, this);

		// agregar ítems al overlay
		GeoPoint point = new GeoPoint (latitudeE6 + 2 * 100100, longitudeE6 + 2 * 100100);
		for (int idx = 0; idx < 10; idx += 1) {
			point = new GeoPoint (latitudeE6 + idx * 100100, longitudeE6 + idx * 100100);
			MapOverlayItem siteItem = new MapOverlayItem (point, "Sitio " + idx, "Calle " + (idx * 12), idx);
			siteMapOverlay.addOverlay (siteItem);
		}

		// agregar overlay al mapa
		mapOverlays.add (siteMapOverlay);

		// encuadrar mapa en Atenas
		MapController mapController = mapView.getController();

		mapController.animateTo (point);
		mapController.setZoom (9);

		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//This component receives callback with the results
		LocationListener locationListener = new LocationListenerImpl();

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
		criteria.setCostAllowed(false);     

		String bestProvider = locationManager.getBestProvider(criteria, true);
		Location porAcaEstaBien = new Location(bestProvider);
		locationListener.onLocationChanged(porAcaEstaBien);
		if (locationManager.isProviderEnabled(bestProvider))
		{
			;
		}
		try
		{
			//Register the listener with the Location Manager to receive location updates
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		}
		catch(Exception e)
		{}
		Location acaEstoy = null;
		try
		{
			if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
			{
				//Register the listener with the Location Manager to receive location updates
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
				acaEstoy = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}
		}
		catch (Exception e)
		{}

	}
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
