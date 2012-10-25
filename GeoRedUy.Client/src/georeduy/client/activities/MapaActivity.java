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
	//MagicPositionOverlay androidOverlay;	 
	//private GPS gps;
	//OverlayItem itemRobotito;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//gps = new GPS(this);
		
		setContentView (georeduy.client.activities.R.layout.activity_main);

		// obtener mapa
		mapView = (MapView) findViewById (georeduy.client.activities.R.id.map_view);
		mapView.setBuiltInZoomControls (true);		
		
		List <Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawableCarrito = this.getResources().getDrawable (R.drawable.cart);
		final SiteMapOverlay siteMapOverlay = new SiteMapOverlay(drawableCarrito, this);
		
		
		// Create an overlay to show current location
        //MyLocationOverlay androidOverlay = new MyLocationOverlay(this, mapView);
        /*androidOverlay.runOnFirstFix(new Runnable() { public void run() {
            mapView.getController().animateTo(androidOverlay.getMyLocation());
            }});*/
        //androidOverlay.enableMyLocation();
        
				
		// agregar overlay al mapa
		//mapOverlays.add (androidOverlay);
		//mapOverlays.add(androidOverlay);
		mapOverlays.add (siteMapOverlay);

		// encuadrar mapa en Atenas
		MapController mapController = mapView.getController();

		GeoPoint point = new GeoPoint(latitudeE5, longitudeE5);
		mapController.animateTo (point );
		mapController.setZoom (14);
		
		
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
	/*
    @Override
    protected void onResume() { 
      //  if(!gps.isRunning()) gps.resumeGPS();   
        super.onStart();
    }

    @Override
    protected void onStop() {
        //gps.stopGPS();
        super.onStop();
    }


    public void locationChanged(double longitude, double latitude) {
    	
    	mapView.invalidate();
    	/*GeoPoint point = new GeoPoint ((int)(latitude), (int)(longitude));
    	
    	androidOverlay.removeOverlay(itemRobotito);
		itemRobotito = new OverlayItem(point, "Me", "");
		androidOverlay.addOverlay(itemRobotito);
		try {
			wait(1000);
			mapView.refreshDrawableState();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
    /*}
*/

    //@Override
    //public void displayGPSSettingsDialog() {                
    //}


}
