package georeduy.client.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import georeduy.client.model.Site;
import georeduy.client.util.GeoRedClient;
import georeduy.client.util.IGPSActivity;
import georeduy.client.util.MagicPositionOverlay;
import georeduy.client.util.OnCompletedCallback;
import georeduy.client.activities.R;
import georeduy.client.controllers.SitesController;
import georeduy.client.util.GPS;
import georeduy.client.util.GPS.MyLocationListener;

import georeduy.client.maps.CustomItemizedOverlay;
import georeduy.client.maps.SiteMapOverlay;
import georeduy.client.maps.MapOverlayItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;


import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class MapaActivity extends MapActivity /*implements IGPSActivity */{
	// mapa
	private MapView mapView;

	// cordenadas de Atenas
	private static final int latitudeE6 = 37985339;
	private static final int longitudeE6 = 23716735;
	private static final int latitudeE5 = -34892830;
	private static final int longitudeE5 = -56130030;
	private LocationListener mlocListener;
    private LocationManager mlocManager;
    private CustomItemizedOverlay androidOverlay;
    OverlayItem itemRobotito;
	//MagicPositionOverlay androidOverlay;	 
	//private GPS gps;
    private SiteMapOverlay siteMapOverlay;
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//gps = new GPS(this);
		
		setContentView (georeduy.client.activities.R.layout.activity_main);
		mlocManager = (LocationManager) ((Activity) this).getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener();
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

		// obtener mapa
		mapView = (MapView) findViewById (georeduy.client.activities.R.id.map_view);
		mapView.setBuiltInZoomControls (true);		
		
		List <Overlay> mapOverlays = mapView.getOverlays();
		
		Drawable drawableCarrito = this.getResources().getDrawable (R.drawable.cart);
		siteMapOverlay = new SiteMapOverlay(drawableCarrito, this);
		
		Drawable drawableAndroid = this.getResources().getDrawable (R.drawable.android);
		androidOverlay = new CustomItemizedOverlay(drawableAndroid, this);
		
		// Create an overlay to show current location
        /*final MagicPositionOverlay androidOverlay = new MagicPositionOverlay(this, mapView);
        androidOverlay.runOnFirstFix(new Runnable() { public void run() {
            mapView.getController().animateTo(androidOverlay.getMyLocation());
            }});
        androidOverlay.enableMyLocation();
        
				
		// agregar overlay al mapa
		
		*/
		mapOverlays.add(androidOverlay);
		mapOverlays.add (siteMapOverlay);

		// encuadrar mapa en Atenas
		MapController mapController = mapView.getController();

		GeoPoint point = new GeoPoint(latitudeE5, longitudeE5);
		mapController.animateTo(point);
		mapController.setZoom (14);
		
		GeoPoint nuevaUbicacion = new GeoPoint(latitudeE5, longitudeE5);
    	itemRobotito = new OverlayItem(nuevaUbicacion, "Me", "This is where you are :)");
    	
		androidOverlay.addOverlay(itemRobotito);
		
		
        SitesController.getInstance().getSitesByPosition(latitudeE5, longitudeE5, 
		        new OnCompletedCallback() {

			        @Override
			        public void onCompleted(String response, String error) {
			        	if (error == null)  {
				        	Gson gson = new Gson();
				        	Type listType = new TypeToken<ArrayList<Site>>() {}.getType();				    		
				    		List<Site> sites = gson.fromJson(response, listType);
				    		int i = 500;
				    		if (sites != null) {
					    		for (Site sitio : sites)
					    		{
					    			double lat =  sitio.getCoordinates() [0]*1e6 +i;
					    			double longitud = sitio.getCoordinates() [1]*1e6 +i;
					    			GeoPoint point2 = new GeoPoint ((int) Math.round(lat), (int) Math.round(longitud));
					    			MapOverlayItem overlayitem = new MapOverlayItem(point2, sitio.getName(), sitio.getName(), sitio.getAddress());
					    			siteMapOverlay.addOverlay (overlayitem);
					    			i += 500;				    			
					    		}
				    		}
				    		mapView.invalidate();
				    		
			    		}
			        }
		        });
        
	}
		
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
    public class MyLocationListener implements LocationListener {

        private final String TAG = MyLocationListener.class.getSimpleName();

        @Override
        public void onLocationChanged(Location loc) {
        	androidOverlay.removeOverlay(itemRobotito);
        	int latitude = (int)(loc.getLatitude()*1E6);
        	int longitude = (int)(loc.getLongitude()*1E6);
        	GeoPoint nuevaUbicacion = new GeoPoint(latitude, longitude);
        	itemRobotito = new OverlayItem(nuevaUbicacion, "Me", "This is where you are :)");
        	
			androidOverlay.addOverlay(itemRobotito);
            mapView.getController().animateTo(new GeoPoint(latitude, longitude));
            
            mapView.invalidate();
            
            SitesController.getInstance().getSitesByPosition(latitude, longitude, 
    		        new OnCompletedCallback() {

    			        @Override
    			        public void onCompleted(String response, String error) {
    			        	if (error == null)  {
    				        	Gson gson = new Gson();
    				        	Type listType = new TypeToken<ArrayList<Site>>() {}.getType();				    		
    				    		List<Site> sites = gson.fromJson(response, listType);
    				    		
    				    		siteMapOverlay.clear();
    				    		
    				    		int i = 500;
    				    		if (sites != null) {
    					    		for (Site sitio : sites)
    					    		{
    					    			double lat =  sitio.getCoordinates() [0]*1e6 +i;
    					    			double longitud = sitio.getCoordinates() [1]*1e6 +i;
    					    			GeoPoint point2 = new GeoPoint ((int) Math.round(lat), (int) Math.round(longitud));
    					    			MapOverlayItem overlayitem = new MapOverlayItem(point2, sitio.getName(), sitio.getName(), sitio.getAddress());
    					    			siteMapOverlay.addOverlay (overlayitem);
    					    			i += 500;				    			
    					    		}
    				    		}
    				    		mapView.invalidate();
    				    		
    			    		}
    			        }
    		        });
        }

        @Override
        public void onProviderDisabled(String provider) {
            
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }

}
