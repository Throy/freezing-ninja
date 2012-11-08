package georeduy.client.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import georeduy.client.model.RetailStore;
import georeduy.client.model.Site;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.GeoRedClient;
import georeduy.client.util.IGPSActivity;
import georeduy.client.util.MagicPositionOverlay;
import georeduy.client.util.MenuHandler;
import georeduy.client.util.OnCompletedCallback;
import georeduy.client.activities.R;
import georeduy.client.controllers.ClientsController;
import georeduy.client.controllers.NotificationsController;
import georeduy.client.controllers.ProductsController;
import georeduy.client.controllers.SitesController;
import georeduy.client.util.GPS;
import georeduy.client.util.GPS.MyLocationListener;

import georeduy.client.maps.CustomItemizedOverlay;
import georeduy.client.maps.RadiusOverlay;
import georeduy.client.maps.SiteMapOverlay;
import georeduy.client.maps.MapOverlayItem;
import georeduy.client.maps.StoreMapOverlay;

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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockMapActivity;
import com.actionbarsherlock.app.SherlockMapActivity2;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MapaActivity extends SherlockMapActivity /*implements IGPSActivity */{
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
    
    // overlay de sitios
    private SiteMapOverlay siteMapOverlay;
    private List<Site> currentSites = new ArrayList<Site>();
    
    // overlay de locales
    private StoreMapOverlay storeMapOverlay;
    private List<RetailStore> currentStores = new ArrayList<RetailStore>();
    
    private RadiusOverlay radiusOverlay;
    private MenuHandler menuHandler;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//gps = new GPS(this);
		
		setContentView (georeduy.client.activities.R.layout.activity_main);
		mlocManager = (LocationManager)((Activity)this).getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener();
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

		// obtener mapa
		mapView = (MapView) findViewById (georeduy.client.activities.R.id.map_view);
		mapView.setBuiltInZoomControls (true);		
		
		List <Overlay> mapOverlays = mapView.getOverlays();
		
		Drawable drawableCarrito = this.getResources().getDrawable (R.drawable.cart);
		storeMapOverlay = new StoreMapOverlay (drawableCarrito, this);
		Drawable drawableCasita = this.getResources().getDrawable (R.drawable.place);
		siteMapOverlay = new SiteMapOverlay (drawableCasita, this);
		
		
		Drawable drawableAndroid = this.getResources().getDrawable (R.drawable.android);
		androidOverlay = new CustomItemizedOverlay(drawableAndroid, this);
        radiusOverlay = new RadiusOverlay(new GeoPoint(latitudeE5, longitudeE5), 380);
		// Create an overlay to show current location
        /*final MagicPositionOverlay androidOverlay = new MagicPositionOverlay(this, mapView);
        androidOverlay.runOnFirstFix(new Runnable() { public void run() {
            mapView.getController().animateTo(androidOverlay.getMyLocation());
            }});
        androidOverlay.enableMyLocation();
        
				
		// agregar overlay al mapa
		
		*/
        mapOverlays.add (radiusOverlay);
		mapOverlays.add (siteMapOverlay);
		mapOverlays.add (storeMapOverlay);
		mapOverlays.add(androidOverlay);
		

		// encuadrar mapa en Atenas
		MapController mapController = mapView.getController();

		GeoPoint point = new GeoPoint(latitudeE5, longitudeE5);
		mapController.animateTo(point);
		mapController.setZoom (16);
		
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
				    		List <Site> sites = gson.fromJson(response, listType);
				    		updateSites (sites);
			    		}
			        }
		        });
        
        ProductsController.getInstance().getStoresByPosition (latitudeE5, longitudeE5, 
		        new OnCompletedCallback() {

			        @Override
			        public void onCompleted(String response, String error) {
			        	if (error == null)  {
				        	Gson gson = new Gson();
				        	Type listType = new TypeToken<ArrayList<RetailStore>>() {}.getType();				    		
				    		List<RetailStore> stores = gson.fromJson(response, listType);
				    		updateStores (stores);
			    		}
			        }
		        });
        
        menuHandler = new MenuHandler(this);
        
        registerReceiver(m_newSiteReceiver,
                new IntentFilter(CommonUtilities.NEW_SITE_ACTION));
        
        registerReceiver(m_newStoreReceiver,
                new IntentFilter(CommonUtilities.NEW_STORE_ACTION));
	}
		
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
    public class MyLocationListener implements LocationListener {

        private final String TAG = MyLocationListener.class.getSimpleName();

        @Override
        public void onLocationChanged(Location loc) {
        	int latitude = (int)(loc.getLatitude()*1E6);
        	int longitude = (int)(loc.getLongitude()*1E6);
        	androidOverlay.removeOverlay(itemRobotito);
        	radiusOverlay.point = new GeoPoint(latitude, longitude);
        	//storeMapOverlay.clear();
			//siteMapOverlay.clear();
        	
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
    				    		updateSites (sites); 				    		
    			    		}
    			        }
    		        });
            
            ProductsController.getInstance().getStoresByPosition(latitude, longitude, 
    		        new OnCompletedCallback() {

    			        @Override
    			        public void onCompleted(String response, String error) {
    			        	if (error == null)  {
    				        	Gson gson = new Gson();
    				        	Type listType = new TypeToken<ArrayList<RetailStore>>() {}.getType();				    		
    				    		List<RetailStore> stores = gson.fromJson(response, listType);
    				    		updateStores (stores);			    		
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
    
    // actualiza los sitios del mapa
    private void updateSites (List <Site> sites) {
		int i = 500;
		currentSites = sites;
		siteMapOverlay.clear ();
		if (sites != null) {
    		for (Site sitio : sites)
    		{
    			double lat =  sitio.getCoordinates() [1]*1e6;
    			double longitud = sitio.getCoordinates() [0]*1e6;
    			GeoPoint point2 = new GeoPoint ((int) Math.round(lat), (int) Math.round(longitud));
    			MapOverlayItem overlayitem = new MapOverlayItem(point2, sitio.getName(), sitio.getName(), sitio.getAddress(), sitio.getId ());
    			siteMapOverlay.addOverlay (overlayitem);
    			i += 500;				    			
    		}
		}
		mapView.invalidate(); 
    }
    
    // actualiza los locales del mapa
    private void updateStores (List <RetailStore> stores) {
		if (stores != null) {
    		// cachear los locales
    		ProductsController.getInstance().setStoresByPosition (stores);
    		
    		for (RetailStore store : stores)
    		{
    			double lat =  store.getCoordinates() [1]*1e6;
    			double longitud = store.getCoordinates() [0]*1e6;
    			GeoPoint point2 = new GeoPoint ((int) Math.round(lat), (int) Math.round(longitud));
    			MapOverlayItem overlayitem = new MapOverlayItem(point2, store.getName(), store.getName(), store.getAddress(), store.getId ());
    			storeMapOverlay.addOverlay (overlayitem);
    							    			
    		}
		}
		mapView.invalidate();	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        menuHandler.onCreateOptionsMenu(menu);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	menuHandler.onOptionsItemSelected(item);
    	
        switch(item.getItemId()) {
            case R.id.contacts_menu:
            	Intent myIntent = new Intent(this, ContactListActivity.class);
        		startActivity(myIntent);
                return true;

            case R.id.main_menu:
            	Intent intent_main = new Intent(this, MainMenuActivity.class);
        		startActivity(intent_main);
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	@Override
    public void onBackPressed() {
	    if (!menuHandler.hideAll())
	    	super.onBackPressed();
    }
    
	public void button_user_item_rem_onClick (View view) {
		// obtener el id del usuario
    	String userId = ((TextView) ((View) view.getParent ()).findViewById (R.id.user_id)).getText ().toString ();

		menuHandler.onRemoveContact(userId);
    }

	@Override
    protected void onResume() {
		menuHandler.refreshContactList();
		
	    super.onResume();
    }    
	
	private final BroadcastReceiver m_newSiteReceiver =
            new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	List<Site> newSites = NotificationsController.getInstance().getNewSites();
        	currentSites.addAll(newSites);
        	updateSites (currentSites); 
        }
    };
    
    private final BroadcastReceiver m_newStoreReceiver =
            new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	List<RetailStore> newStores = NotificationsController.getInstance().getNewStores();
        	currentStores.addAll(newStores);
        	updateStores (currentStores); 
        }
    };
}
