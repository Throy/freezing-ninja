package georeduy.client.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import georeduy.client.model.Site;
import georeduy.client.util.GeoRedClient;
import georeduy.client.util.OnCompletedCallback;
import georeduy.client.activities.R;
import georeduy.client.controllers.SitesController;
import georeduy.client.util.LocationListenerImpl;

import georeduy.client.maps.CustomItemizedOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;


import android.R.integer;
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
	private static final int latitudeE5 = -34892830;
	private static final int longitudeE5 = -56130030;
	private LocationManager locationManager;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView (georeduy.client.activities.R.layout.activity_main);
		
		// obtener mapa
		mapView = (MapView) findViewById (georeduy.client.activities.R.id.map_view);
		mapView.setBuiltInZoomControls (true);

		// inicializar overlay, con íconos carrito
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawableCarrito = this.getResources().getDrawable (R.drawable.cart);
		final CustomItemizedOverlay itemizedOverlayCarrito = new CustomItemizedOverlay (drawableCarrito, this);
		
		Drawable drawableAndroid = this.getResources().getDrawable (R.drawable.android);
		final CustomItemizedOverlay itemizedOverlayAndroid = new CustomItemizedOverlay (drawableAndroid, this);

		// agregar ítems al overlay
		GeoPoint pointCasaAndres = new GeoPoint (latitudeE5, longitudeE5);
		OverlayItem overlayitem = new OverlayItem(pointCasaAndres, "Casa Andres", "Estoy en la casa de Andres!");
		itemizedOverlayAndroid.addOverlay (overlayitem);
		
		// agregar overlay al mapa
		mapOverlays.add (itemizedOverlayAndroid);
		mapOverlays.add (itemizedOverlayCarrito);

		// encuadrar mapa en Atenas
		MapController mapController = mapView.getController();

		mapController.animateTo(pointCasaAndres);
		mapController.setZoom(17);
		
		
		
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
        
        SitesController.getInstance().getSitesByPosition(latitudeE5, longitudeE5, 
		        new OnCompletedCallback() {

			        @Override
			        public void onCompleted(String response, String error) {
			        	if (error == null)  {
				        	Gson gson = new Gson();
				        	Type listType = new TypeToken<ArrayList<Site>>() {}.getType();				    		
				    		List<Site> sites = gson.fromJson(response, listType);
				    		int i = 500;
				    		for(Site sitio:sites)
				    		{
				    			double lat =  sitio.getCoordinates()[0]*1e6 +i;
				    			double longitud = sitio.getCoordinates()[1]*1e6 +i;
				    			GeoPoint point2 = new GeoPoint ((int)Math.round(lat), (int)Math.round(longitud));
				    			OverlayItem overlayitem = new OverlayItem(point2, sitio.getName(), sitio.getName());
				    			itemizedOverlayCarrito.addOverlay (overlayitem);
				    			i += 500;
				    		}
			    		}
			        }
		        });
	}
	
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
