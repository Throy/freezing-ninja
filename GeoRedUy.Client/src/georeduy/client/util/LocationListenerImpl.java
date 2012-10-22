package georeduy.client.util;

import android.location.LocationListener;
import android.location.Location;
import android.os.Bundle;

public class LocationListenerImpl implements LocationListener
{
		public Location location;
		
        public LocationListenerImpl()
        {
        }

        @Override
        public void onLocationChanged(Location location) 
        {       
                //Set this location
        		this.location = location;
        }
        
        @Override
        public void onProviderDisabled(String provider) 
        {       
        }

        @Override
        public void onProviderEnabled(String provider) 
        {       
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) 
        {       
        }
}