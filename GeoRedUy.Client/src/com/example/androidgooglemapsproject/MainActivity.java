package com.example.androidgooglemapsproject;

import java.util.List;

import android.os.Bundle;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.example.androidgooglemapsproject.CustomItemizedOverlay;
 
public class MainActivity extends MapActivity {
	     
    private MapView mapView;
    private static final int latitudeE6 = 37985339;
	private static final int longitudeE6 = 23716735;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         
        mapView = (MapView) findViewById(R.id.map_view);      
        mapView.setBuiltInZoomControls(true);
        super.onCreate(savedInstanceState);
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.cart);        
        CustomItemizedOverlay itemizedOverlay = new CustomItemizedOverlay(drawable, this);
         
        GeoPoint point = new GeoPoint(latitudeE6, longitudeE6);
        OverlayItem overlayitem =
             new OverlayItem(point, "Hola", "Estoy en atenas!");
         
        itemizedOverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedOverlay);
    
        MapController mapController = mapView.getController();
     
        mapController.animateTo(point);
        mapController.setZoom(6);
        
	}
 
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
	     
}
